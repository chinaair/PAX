package com.chinaair.services;

import java.math.BigDecimal;
import java.util.Date;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.chinaair.entity.DailyTicketJournal;

@Stateless
@LocalBean
public class JournalServiceBean {
	

	@PersistenceContext(unitName = "chinaair_pax")
	private EntityManager em;
	
	public DailyTicketJournal findTicketJournalByDate(Date pbsrDate) {
		return em.find(DailyTicketJournal.class, pbsrDate);
	}
	
	public void insertCloseJournal(Date journalDate, BigDecimal amtUsd, BigDecimal amtVnd, Long empId) {
		DailyTicketJournal newClose = new DailyTicketJournal();
		newClose.setPbsrDate(journalDate);
		newClose.setTotalAmtUsd(amtUsd);
		newClose.setTotalAmtVnd(amtVnd);
		newClose.setCloseEmpId(empId);
		newClose.setStatus("1");//closed
		em.persist(newClose);
		em.createQuery(
				"UPDATE TicketIssue t SET t.reportedDate = :reportedDate WHERE t.issueDate = :issueDate AND t.status IN ('0', '1')")
				.setParameter("reportedDate", new Date())
				.setParameter("issueDate", journalDate).executeUpdate();
		em.flush();
	}
	
	public void updateCloseJournal(Date journalDate, BigDecimal amtUsd, BigDecimal amtVnd, Long empId) {
		DailyTicketJournal target = findTicketJournalByDate(journalDate);
		target.setTotalAmtUsd(amtUsd);
		target.setTotalAmtVnd(amtVnd);
		target.setCloseEmpId(empId);
		target.setStatus("1");//closed
		em.merge(target);
		em.createQuery(
				"UPDATE TicketIssue t SET t.reportedDate = :reportedDate WHERE t.issueDate = :issueDate AND t.status IN ('0', '1')")
				.setParameter("reportedDate", new Date())
				.setParameter("issueDate", journalDate).executeUpdate();
	}
	
	public void reOpenClosedJournal(Date journalDate) {
		DailyTicketJournal target = em.find(DailyTicketJournal.class, journalDate);
		if(target != null) {
			target.setStatus("0");//opened
			em.merge(target);
			em.createQuery(
					"UPDATE TicketIssue t SET t.reportedDate = null WHERE t.issueDate = :issueDate AND t.status IN ('0', '1')")
					.setParameter("issueDate", journalDate).executeUpdate();
			em.flush();
		}
	}

}
