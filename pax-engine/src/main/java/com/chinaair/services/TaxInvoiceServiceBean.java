package com.chinaair.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.chinaair.entity.InvoiceStock;
import com.chinaair.entity.InvoiceStockDetail;
import com.chinaair.entity.TaxInvoiceCode;
import com.chinaair.entity.TaxInvoiceIssue;

@Stateless
@LocalBean
public class TaxInvoiceServiceBean {
	
	@PersistenceContext(unitName = "chinaair_pax")
	private EntityManager em;
	
	public List<TaxInvoiceCode> getAllTaxInvoiceCode() {
		return em.createQuery("SELECT t FROM TaxInvoiceCode t", TaxInvoiceCode.class).getResultList();
	}
	
	public Long countTaxStockByInvCode(Long invoiceCodeId) {
		Long invStockCount = em
				.createQuery(
						"SELECT COUNT(i) FROM InvoiceStock i WHERE i.invoiceCodeId = :invCodeId",
						Long.class).setParameter("invCodeId", invoiceCodeId)
				.getSingleResult();
		return invStockCount;
	}
	
	public void removeTaxInvoiceCode(Long id) {
		TaxInvoiceCode removeObj = em.find(TaxInvoiceCode.class, id);
		if(removeObj != null) {
			em.remove(removeObj);
		}
	}
	
	public void insertTaxInvoiceCode(TaxInvoiceCode target) {
		if(target.getId() == null) {
			em.persist(target);
		}
	}
	
	public List<InvoiceStock> getAllInvoiceStockInfo(boolean isCargo) {
		StringBuffer sql = new StringBuffer("SELECT i FROM InvoiceStock i ");
		if(isCargo) {
			sql.append("WHERE i.cargo = '1'");
		} else {
			sql.append("WHERE i.cargo IS NULL");
		}
		return em.createQuery(sql.toString(), InvoiceStock.class).getResultList();
	}
	public List<InvoiceStock> getAllInvoiceStockInfo(int first, int pageSize,boolean isCargo) {
		StringBuffer sql = new StringBuffer("SELECT i FROM InvoiceStock i ");
		if(isCargo) {
			sql.append("WHERE i.cargo = '1'");
		} else {
			sql.append("WHERE i.cargo IS NULL");
		}
		Query query = em.createQuery(sql.toString(), InvoiceStock.class);
		query.setFirstResult(first);
		query.setMaxResults(pageSize);
		return query.getResultList();
	}
	public int countAllInvoiceStockInfo(boolean isCargo) {
		StringBuffer sql = new StringBuffer("SELECT i FROM InvoiceStock i ");
		if(isCargo) {
			sql.append("WHERE i.cargo = '1'");
		} else {
			sql.append("WHERE i.cargo IS NULL");
		}
		Query query = em.createQuery(sql.toString(), InvoiceStock.class);
		return query.getResultList().size();
	}
	public void insertTaxInvoiceStock(InvoiceStock invoiceStock) {
		List<InvoiceStockDetail> details = new ArrayList<>();
		int startNo = invoiceStock.getStartNo().intValue();
		int quantity = invoiceStock.getQuantity().intValue();
		for(int i = startNo; i <= (startNo + quantity - 1); i++) {
			InvoiceStockDetail detail = new InvoiceStockDetail();
			detail.setInvoiceStock(invoiceStock);
			detail.setInvoiceStockNo(invoiceStock.getPrefix() + String.format("%04d", i));
			detail.setStatus("0");//unused
			details.add(detail);
		}
		invoiceStock.setInvoiceStockDetails(details);
		em.persist(invoiceStock);
	}
	
	public int countUsedInvoiceStock(Long invoiceStockId) {
		return em
				.createQuery(
						"SELECT COUNT(i) FROM InvoiceStockDetail i WHERE i.invoiceStock.id = :stockId AND i.status in ('1', '2')",
						Long.class).setParameter("stockId", invoiceStockId).getSingleResult().intValue();
	}
	
	public void deleteInvoiceStock(Long invoiceStockId) {
		//delete details
		em.createQuery("DELETE FROM InvoiceStockDetail i WHERE i.invoiceStock.id = :stockId").setParameter("stockId", invoiceStockId).executeUpdate();
		//delete main
		em.createQuery("DELETE FROM InvoiceStock i WHERE i.id = :stockId").setParameter("stockId", invoiceStockId).executeUpdate();
	}
	
	public void updateAllInvoiceDetails(InvoiceStock invoiceStock) {
		//delete details
		em.createQuery("DELETE FROM InvoiceStockDetail i WHERE i.invoiceStock.id = :stockId").setParameter("stockId", invoiceStock.getId()).executeUpdate();
		List<InvoiceStockDetail> details = new ArrayList<>();
		int startNo = invoiceStock.getStartNo().intValue();
		int quantity = invoiceStock.getQuantity().intValue();
		for(int i = startNo; i <= (startNo + quantity - 1); i++) {
			InvoiceStockDetail detail = new InvoiceStockDetail();
			detail.setInvoiceStock(invoiceStock);
			detail.setInvoiceStockNo(invoiceStock.getPrefix() + String.format("%04d", i));
			detail.setStatus("0");//unused
			details.add(detail);
		}
		invoiceStock.setInvoiceStockDetails(details);
		em.merge(invoiceStock);
	}
	
	public void updateInvoiceStockOnly(InvoiceStock invoiceStock) {
		em.createQuery("UPDATE InvoiceStock i SET i.invoiceCodeId = :codeId").setParameter("codeId", invoiceStock.getInvoiceCodeId()).executeUpdate();
	}
	
	public List<InvoiceStockDetail> findUsableInvoiceStockDetail(boolean isCargo) {
		StringBuffer sql = new StringBuffer("SELECT i FROM InvoiceStockDetail i WHERE i.status = '0' ");
		if(isCargo) {
			sql.append("AND i.invoiceStock.cargo = '1' ");
		} else {
			sql.append("AND i.invoiceStock.cargo IS NULL ");
		}
		sql.append("ORDER BY i.invoiceStockNo");
		return em.createQuery(sql.toString(), InvoiceStockDetail.class).getResultList();
	}
	
	public Long taxInvoiceCount(Date startDate, Date endDate, boolean isCargo) {
		StringBuffer sql = new StringBuffer("SELECT COUNT(t) FROM TaxInvoiceIssue t WHERE t.status in ('0','2') AND t.issueDate BETWEEN :fromDate AND :toDate ");//voided also be counted
		if(isCargo) {
			sql.append("AND t.cargo = '1' ");
		} else {
			sql.append("AND t.cargo IS NULL ");
		}
		return em
				.createQuery(
						sql.toString(),
						Long.class)
				.setParameter("fromDate", startDate)
				.setParameter("toDate", endDate).getSingleResult();
	}
	
	public List<TaxInvoiceIssue> gettaxInvoiceList(Date startDate, Date endDate, boolean isCargo) {
		StringBuffer sql = new StringBuffer("SELECT t FROM TaxInvoiceIssue t WHERE t.status in ('0','2') AND t.issueDate BETWEEN :fromDate AND :toDate ");//voided invoice also be counted
		if(isCargo) {
			sql.append("AND t.cargo = '1' ");
		} else {
			sql.append("AND t.cargo IS NULL ");
		}
		sql.append("ORDER BY t.issueDate ASC, t.invoiceStockDetail.invoiceStockNo ASC");
		return em
				.createQuery(
						sql.toString(),
						TaxInvoiceIssue.class)
				.setParameter("fromDate", startDate)
				.setParameter("toDate", endDate).getResultList();
	}
	
}
