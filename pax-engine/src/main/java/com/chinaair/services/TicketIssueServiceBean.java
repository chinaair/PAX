package com.chinaair.services;

import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.chinaair.dto.TicketIssueSearchDto;
import com.chinaair.entity.TicketIssue;
import com.chinaair.entity.TicketIssueDetail;

@Stateless
@LocalBean
public class TicketIssueServiceBean {
	
	@PersistenceContext(unitName = "chinaair_pax")
	private EntityManager em;
	
	public void insertTicketIssue(TicketIssue ticketIssue) {
		List<TicketIssueDetail> details = ticketIssue.getTicketIssueDetail();
		ticketIssue.setTicketIssueDetail(null);
		em.persist(ticketIssue);
		em.flush();
		if(details != null
				&& !details.isEmpty()) {
			long order = 1;
			for(TicketIssueDetail item : details) {
				item.setTicketIssueId(ticketIssue.getId());
				item.setDispOrder(order++);
				em.persist(item);
			}
		}
		em.flush();
	}
	
	public List<TicketIssue> findTicketIssueList(TicketIssueSearchDto searchDto) {
		StringBuffer sqlBuffer = new StringBuffer("SELECT t FROM TicketIssue t WHERE 1 = 1 ");
		if(searchDto.getStartDate() != null) {
			sqlBuffer.append(" AND t.issueDate >= :startDate ");
			if(searchDto.getEndDate() != null) {
				sqlBuffer.append(" AND t.issueDate <= :endDate ");
			}
		}
		if(searchDto.getAgentId() != null) {
			sqlBuffer.append(" AND t.agent.id = :agenId ");
		}
		if(searchDto.getReferNumber() != null) {
			sqlBuffer.append(" AND t.id = :referNum ");
		}
		if(searchDto.getStatus() != null && !searchDto.getStatus().equals("ALL")) {//
			sqlBuffer.append(" AND t.status = :status ");
		}
		sqlBuffer.append("ORDER BY t.issueDate DESC");
		TypedQuery<TicketIssue> q = em.createQuery(sqlBuffer.toString(), TicketIssue.class);
		if(searchDto.getStartDate() != null) {
			q.setParameter("startDate", searchDto.getStartDate());
			if(searchDto.getEndDate() != null) {
				q.setParameter("endDate", searchDto.getEndDate());
			}
		}
		if(searchDto.getAgentId() != null) {
			q.setParameter("agenId", searchDto.getAgentId());
		}
		if(searchDto.getReferNumber() != null) {
			q.setParameter("referNum", searchDto.getReferNumber());
		}
		if(searchDto.getStatus() != null && !searchDto.getStatus().equals("ALL")) {
			q.setParameter("status", searchDto.getStatus());
		}
		return q.getResultList();
	}
	
	public List<TicketIssue> findTicketIssueList(int first, int pageSize, TicketIssueSearchDto searchDto) {
		Query q = selectTaxInvoiceQuery(searchDto, false);
		q.setFirstResult(first);
		q.setMaxResults(pageSize);
		return q.getResultList();
	}
	
	public Long countTicketIssueList(TicketIssueSearchDto searchDto) {
		Query q = selectTaxInvoiceQuery(searchDto, true);
		return (Long)q.getSingleResult();
	}
	
	private Query selectTaxInvoiceQuery(TicketIssueSearchDto searchDto, boolean isCount) {
		StringBuffer sqlBuffer = new StringBuffer("SELECT");//aaaa
		if(isCount) {
			sqlBuffer.append(" COUNT(t) ");
		} else {
			sqlBuffer.append(" t ");
		}
		sqlBuffer.append("FROM TicketIssue t ");
		if(searchDto != null
				&& ((searchDto.getTicketNo() != null && searchDto.getTicketNo().trim().length() > 0)
						|| (searchDto.getRoute() != null && searchDto.getRoute().trim().length() > 0))) {
			sqlBuffer.append(" JOIN t.ticketIssueDetail d ");
		}
		sqlBuffer.append(" WHERE 1 = 1 ");
		if (searchDto != null) {
			if(searchDto.getStartDate() != null) {
				sqlBuffer.append(" AND t.issueDate >= :startDate ");
				if(searchDto.getEndDate() != null) {
					sqlBuffer.append(" AND t.issueDate <= :endDate ");
				}
			}
			if(searchDto.getAgentId() != null) {
				sqlBuffer.append(" AND t.agent.id = :agenId ");
			}
			if(searchDto.getReferNumber() != null) {
				sqlBuffer.append(" AND t.id = :referNum ");
			}
			if(searchDto.getStatus() != null && !searchDto.getStatus().equals("ALL")) {//
				sqlBuffer.append(" AND t.status = :status ");
			}
			if(searchDto.getTicketNo() != null && searchDto.getTicketNo().trim().length() > 0) {
				sqlBuffer.append(" AND d.ticketNo like :ticketNo ");
			}
			if(searchDto.getRoute() != null && searchDto.getRoute().trim().length() > 0) {
				sqlBuffer.append(" AND d.route like :route ");
			}
			if(searchDto.getInputSD() != null && searchDto.getInputSD().trim().length() > 0) {
				sqlBuffer.append(" AND t.inputEmp.userId = :inputSD ");
			}
		}
		
		sqlBuffer.append("ORDER BY t.issueDate DESC");
		
		Query q = null;
		if(isCount) {
			q = em.createQuery(sqlBuffer.toString(), Long.class);
		} else {
			q = em.createQuery(sqlBuffer.toString(), TicketIssue.class);
		}
		
		if (searchDto != null) {
			if(searchDto.getStartDate() != null) {
				q.setParameter("startDate", searchDto.getStartDate());
				if(searchDto.getEndDate() != null) {
					q.setParameter("endDate", searchDto.getEndDate());
				}
			}
			if(searchDto.getAgentId() != null) {
				q.setParameter("agenId", searchDto.getAgentId());
			}
			if(searchDto.getReferNumber() != null) {
				q.setParameter("referNum", searchDto.getReferNumber());
			}
			if(searchDto.getStatus() != null && !searchDto.getStatus().equals("ALL")) {
				q.setParameter("status", searchDto.getStatus());
			}
			if(searchDto.getTicketNo() != null && searchDto.getTicketNo().trim().length() > 0) {
				q.setParameter("ticketNo", "%" + searchDto.getTicketNo().trim() + "%");
			}
			if(searchDto.getRoute() != null && searchDto.getRoute().trim().length() > 0) {
				q.setParameter("route", "%" + searchDto.getRoute().trim() + "%");
			}
			if(searchDto.getInputSD() != null && searchDto.getInputSD().trim().length() > 0) {
				q.setParameter("inputSD", searchDto.getInputSD().trim());
			}
		}
		return q;
	}
	
	public void updateTicketIssue(TicketIssue ticketIssue) {
		em.createQuery("DELETE FROM TicketIssueDetail t WHERE t.ticketIssueId = :ticketIssueId ")
			.setParameter("ticketIssueId", ticketIssue.getId()).executeUpdate();
		List<TicketIssueDetail> details = ticketIssue.getTicketIssueDetail();
		ticketIssue.setTicketIssueDetail(null);
		em.merge(ticketIssue);
		em.flush();
		if(details != null
				&& !details.isEmpty()) {
			long order = 1;
			for(TicketIssueDetail item : details) {
				item.setTicketIssueId(ticketIssue.getId());
				item.setDispOrder(order++);
				em.persist(item);
			}
		}
		em.flush();
	}
	
	public TicketIssue updateTicketIssueStatus(TicketIssue ticketIssue) {
		return em.merge(ticketIssue);
	}
	
	public TicketIssue findTicketIssueById(Long id) {
		return em.find(TicketIssue.class, id);
	}
	
	public List<TicketIssue> findValidTicketIssueByDate(Date issueDate) {
		return em
				.createQuery(
						"SELECT t FROM TicketIssue t WHERE t.issueDate = :issueDate AND t.status IN ('0', '1') ORDER BY t.inputEmpId, t.id",
						TicketIssue.class).setParameter("issueDate", issueDate)
				.getResultList();
	}
	
	public List<TicketIssue> findValidTicketIssueByDateOrderByAgent(Date issueDate, String paymentType) {
		return em
				.createQuery(
						"SELECT t FROM TicketIssue t WHERE t.issueDate = :issueDate AND t.paymentType = :paymentType AND t.status IN ('0', '1') ORDER BY t.agent.id, t.id",
						TicketIssue.class).setParameter("issueDate", issueDate)
				.setParameter("paymentType", paymentType).getResultList();
	}

}
