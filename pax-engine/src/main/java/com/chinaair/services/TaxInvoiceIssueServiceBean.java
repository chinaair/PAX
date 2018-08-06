package com.chinaair.services;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.chinaair.dto.TaxInvoiceIssueSearchDto;
import com.chinaair.entity.InvoiceStockDetail;
import com.chinaair.entity.TaxInvoiceIssue;
import com.chinaair.entity.TaxInvoiceIssueDetail;
import com.chinaair.entity.TicketIssue;

@Stateless
@LocalBean
public class TaxInvoiceIssueServiceBean {
	
	@PersistenceContext(unitName = "chinaair_pax")
	private EntityManager em;

	
	public List<TaxInvoiceIssue> getTaxInvoiceIssueList(int first, int pageSize,TaxInvoiceIssueSearchDto dto) {
		Query q = selectTaxInvoiceQuery(dto, false);
		q.setFirstResult(first);
		q.setMaxResults(pageSize);
		return q.getResultList();
	}
	
	public Long countTaxInvoiceIssueList(TaxInvoiceIssueSearchDto dto) {
		Query q = selectTaxInvoiceQuery(dto, true);
		return (Long)q.getSingleResult();
	}
	
	private Query selectTaxInvoiceQuery(TaxInvoiceIssueSearchDto dto, boolean isCount) {
		StringBuffer sql = new StringBuffer("SELECT");
		if(isCount) {
			sql.append(" COUNT(t) ");
		} else {
			sql.append(" t ");
		}
		
		sql.append("FROM TaxInvoiceIssue t ");
		if(dto != null && dto.getTicketNo() != null) {
			sql.append("JOIN t.taxInvoiceIssueDetail d ");
		}
		sql.append("WHERE 1 = 1 ");
		if (dto != null) {
			if(dto.isCargo()) {
				sql.append("AND t.cargo = '1' ");
			} else {
				sql.append("AND t.cargo IS NULL ");
			}
			if(dto.getStartDate() != null) {
				sql.append("AND t.issueDate >= :startDate ");
			}
			if(dto.getEndDate() != null) {
				sql.append("AND t.issueDate <= :endDate ");
			}
			if(dto.getReferNumber() != null && dto.getReferNumberAsNumber() != null) {
				sql.append("AND t.ticketIssueId = :referNo ");
			}
			if(dto.getVatCode() != null && dto.getVatCode().length() > 0) {
				sql.append("AND t.vatCode = :vatCode ");
			}
			if(dto.getStatus() != null && !dto.getStatus().equals("ALL")) {
				sql.append("AND t.status = :status ");
			}
			if(dto.getTicketNo() != null && dto.getTicketNo().trim().length() > 0) {
				sql.append(" AND d.ticketNo like :ticketNo ");
			}
			if(dto.getCompanyName() != null && dto.getCompanyName().trim().length() > 0) {
				sql.append("AND t.companyName like :companyName ");
			}
			if(dto.getTaxInvNo() != null && dto.getTaxInvNo().trim().length() > 0) {
				sql.append("AND t.invoiceStockDetail.invoiceStockNo = :taxInvNo ");
			}
		}
		if(!isCount) {
			sql.append("ORDER BY ");
			if(dto.getSortField() != null) {
				sql.append("t.").append(dto.getSortField())
					.append(" ").append(dto.getSortOrder());
			} else {
				sql.append("t.issueDate DESC, t.invoiceStockDetail.invoiceStockNo DESC");
			}
		}
		
		Query q = null;
		if(isCount) {
			q = em.createQuery(sql.toString(), Long.class);
		} else {
			q = em.createQuery(sql.toString(), TaxInvoiceIssue.class);
		}
		if (dto != null) {
			if(dto.getStartDate() != null) {
				q.setParameter("startDate", dto.getStartDate());
			}
			if(dto.getEndDate() != null) {
				q.setParameter("endDate", dto.getEndDate());
			}
			if(dto.getReferNumber() != null && dto.getReferNumberAsNumber() != null) {
				q.setParameter("referNo", dto.getReferNumberAsNumber());
			}
			if(dto.getVatCode() != null && dto.getVatCode().trim().length() > 0) {
				q.setParameter("vatCode", dto.getVatCode().trim());
			}
			if(dto.getStatus() != null && !dto.getStatus().equals("ALL")) {
				q.setParameter("status", dto.getStatus());
			}
			if(dto.getTicketNo() != null && dto.getTicketNo().trim().length() > 0) {
				q.setParameter("ticketNo", "%" + dto.getTicketNo().trim() + "%");
			}
			if(dto.getCompanyName() != null && dto.getCompanyName().trim().length() > 0) {
				q.setParameter("companyName", "%" + dto.getCompanyName().trim() + "%");
			}
			if(dto.getTaxInvNo() != null && dto.getTaxInvNo().trim().length() > 0) {
				q.setParameter("taxInvNo", dto.getTaxInvNo().trim());
			}
		}
		return q;
	}
	
	public TaxInvoiceIssue findTaxInvoiceIssueById(Long id) {
		return em.find(TaxInvoiceIssue.class, id);
	}
	
	public void insertTaxInvoiceIssue(TaxInvoiceIssue issue) {
		List<TaxInvoiceIssueDetail> details = issue.getTaxInvoiceIssueDetail();
		issue.setTaxInvoiceIssueDetail(null);
		em.persist(issue);
		//update stock detail
		em.createQuery(
				"UPDATE InvoiceStockDetail i SET i.status = '1' WHERE i.id = :stockDetailId")
				.setParameter("stockDetailId", issue.getInvoiceStockDetailId())
				.executeUpdate();//update to used
		em.flush();
		if(issue.getTicketIssueId() != null) {
			em.createQuery(
					"UPDATE TicketIssue t SET t.taxInvoiceId = :taxInvoiceId, t.status = '1' WHERE t.id = :ticketIssueId")
					.setParameter("taxInvoiceId", issue.getId())
					.setParameter("ticketIssueId", issue.getTicketIssueId())
					.executeUpdate();//update ticket issue
		}
		if(details != null
				&& !details.isEmpty()) {
			long order = 1;
			for(TaxInvoiceIssueDetail item : details) {
				item.setTaxInvoiceIssueId(issue.getId());
				item.setDispOrder(order++);
				em.persist(item);
			}
		}
		em.flush();
	}
	
	public void updateTaxInvoiceIssue(TaxInvoiceIssue issue) {
		TaxInvoiceIssue oldIssue = em.find(TaxInvoiceIssue.class, issue.getId());
		InvoiceStockDetail oldStockDetail = oldIssue.getInvoiceStockDetail();
		oldStockDetail.setStatus("0");//unused
		em.merge(oldStockDetail);
		List<TaxInvoiceIssueDetail> details = issue.getTaxInvoiceIssueDetail();
		issue.setTaxInvoiceIssueDetail(null);
		em.createQuery(
				"DELETE FROM TaxInvoiceIssueDetail t WHERE t.taxInvoiceIssueId = :issueId")
				.setParameter("issueId", issue.getId()).executeUpdate();
		em.merge(issue);
		if(details != null
				&& !details.isEmpty()) {
			long order = 1;
			for(TaxInvoiceIssueDetail item : details) {
				item.setId(null);
				item.setTaxInvoiceIssueId(issue.getId());
				item.setDispOrder(order++);
				em.persist(item);
			}
		}
		em.createQuery(
				"UPDATE InvoiceStockDetail i SET i.status = '1' WHERE i.id = :stockDetailId")
				.setParameter("stockDetailId", issue.getInvoiceStockDetailId())
				.executeUpdate();//update to used
		em.flush();
	}
	
	public void voidTaxInvoiceIssue(Long id) {
		TaxInvoiceIssue issue = em.find(TaxInvoiceIssue.class, id);
		if(issue == null) {
			return;
		}
		TicketIssue ticketIssue = issue.getTicketIssue();
		if(ticketIssue != null) {
			ticketIssue.setTaxInvoiceId(null);
			ticketIssue.setStatus("0");
			em.merge(ticketIssue);
		}
		issue.setStatus("2");//voided
		issue.setTicketIssueId(null);
		em.merge(issue);
		InvoiceStockDetail invoiceNo = issue.getInvoiceStockDetail();
		if(invoiceNo != null) {
			invoiceNo.setStatus("2");//voided
			em.merge(invoiceNo);
		}
		em.flush();
	}
	
	public Long getTaxInvoiceDetailByTicketNoCount(String ticketNo) {
		return em
				.createQuery(
						"SELECT COUNT(t) FROM TaxInvoiceIssueDetail t WHERE t.ticketNo = :ticketNo",
						Long.class).setParameter("ticketNo", ticketNo)
				.getSingleResult();
	}
}
