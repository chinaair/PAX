package com.chinaair.services;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
			for(TicketIssueDetail item : details) {
				item.setTicketIssueId(ticketIssue.getId());
				em.persist(item);
			}
		}
		em.flush();
	}

}
