package com.chinaair.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.chinaair.dto.AgentSummaryDto;
import com.chinaair.dto.FinalReportSearchDto;
import com.chinaair.dto.InvoiceSummary;
import com.chinaair.entity.Agent;

@Stateless
@LocalBean
public class FinalReportServiceBean {
	
	@PersistenceContext(unitName = "chinaair_pax")
	private EntityManager em;
	
	public List<AgentSummaryDto> getAgentSummaryData(FinalReportSearchDto searchDto) {
		StringBuffer sql = new StringBuffer("SELECT t.agent, sum(t.amountUsd) as summaryAmtUsd from TicketIssue t WHERE t.status = '0' AND t.taxInvoiceId is null ");
		if(searchDto.getStartDate() != null) {
			sql.append(" AND t.issueDate >= :startDate ");
			if(searchDto.getEndDate() != null) {
				sql.append(" AND t.issueDate <= :endDate ");
			}
		}
		if(searchDto.getPaymentType() != null) {
			sql.append(" AND t.paymentType = :paymentType ");
		}
		sql.append("GROUP BY t.agent ORDER BY t.agent");
		Query q = em.createQuery(sql.toString(), Object[].class);
		if(searchDto.getStartDate() != null) {
			q.setParameter("startDate", searchDto.getStartDate());
			if(searchDto.getEndDate() != null) {
				q.setParameter("endDate", searchDto.getEndDate());
			}
		}
		if(searchDto.getPaymentType() != null) {
			q.setParameter("paymentType", searchDto.getPaymentType());
		}
		@SuppressWarnings("unchecked")
		List<Object[]> result = q.getResultList();
		List<AgentSummaryDto> sumList = new ArrayList<>();
		for(Object[] item : result) {
			AgentSummaryDto dto = new AgentSummaryDto();
			dto.setAgent((Agent)item[0]);
			dto.setSummaryAmtUsd((BigDecimal)item[1]);
			sumList.add(dto);
		}
		return sumList;
	}
	
	public List<AgentSummaryDto> findInvoiceSummaryGroupByAgent(Date startDate, Date endDate, String paymentType, AgentSummaryDto[] dtos) {
		if(dtos == null) {
			return new ArrayList<>();
		}
		List<Long> agentParams = new ArrayList<>();
		Map<Long, Agent> selectAgentMap = new HashMap<Long, Agent>();
		String agentQueryStr = "";
		for(AgentSummaryDto item : dtos) {
			agentParams.add(item.getAgent().getId());
			selectAgentMap.put(item.getAgent().getId(), item.getAgent());
			agentQueryStr += ",?";
		}
		if(agentQueryStr.length() > 0) {
			agentQueryStr = agentQueryStr.substring(1);
		}
		Query q = em
				.createNativeQuery(
						"SELECT t.AGENT_ID, t.ISSUE_DATE, SUM(d.PRICE * d.QUANTITY), SUM(ROUND(d.PRICE * r.RATE / 1000) * 1000 * d.QUANTITY) FROM TICKET_ISSUE_DETAIL d, TICKET_ISSUE t, RATE r WHERE t.ID = d.TICKET_ISSUE_ID AND t.RATE_ID = r.ID AND t.STATUS = '0' AND t.TAX_INVOICE_ID IS NULL AND t.AGENT_ID IN ("+ agentQueryStr +") AND t.ISSUE_DATE BETWEEN ? AND ? AND t.PAYMENT_TYPE = ? GROUP BY t.AGENT_ID, t.ISSUE_DATE ORDER BY t.AGENT_ID, t.ISSUE_DATE");
		int index = 1;
		for(Long agentid : agentParams) {
			q.setParameter(index++, agentid);
		}
		q.setParameter(index++, startDate);
		q.setParameter(index++, endDate);
		q.setParameter(index++, paymentType);
		List<Object[]> result = q.getResultList();
		List<AgentSummaryDto> sumList = new ArrayList<>();
		Long bufferAgentId = null;
		AgentSummaryDto bufferDto = new AgentSummaryDto();
		BigDecimal agentTotalUsd = new BigDecimal(0);
		BigDecimal agentTotalVnd = new BigDecimal(0);
		for(Object[] item : result) {
			Long itemAgentId = (Long)item[0];
			if(!itemAgentId.equals(bufferAgentId)) {
				if(bufferAgentId != null) {
					bufferDto.setSummaryAmtUsd(agentTotalUsd);
					bufferDto.setSummaryAmtVnd(agentTotalVnd);
					sumList.add(bufferDto);
				}
				bufferDto = new AgentSummaryDto();
				bufferDto.setInvoiceSummary(new ArrayList<InvoiceSummary>());
				bufferDto.setAgent(selectAgentMap.get(itemAgentId));
				bufferAgentId = itemAgentId;
				agentTotalUsd = new BigDecimal(0);
				agentTotalVnd = new BigDecimal(0);
			}
			InvoiceSummary invSum = new InvoiceSummary();
			invSum.setIssueDate((Date)item[1]);
			invSum.setDailyAmtUsd((BigDecimal)item[2]);
			invSum.setDailyAmtVnd((BigDecimal)item[3]);
			agentTotalUsd = agentTotalUsd.add(invSum.getDailyAmtUsd());
			agentTotalVnd = agentTotalVnd.add(invSum.getDailyAmtVnd());
			bufferDto.getInvoiceSummary().add(invSum);
		}
		if(bufferDto.getInvoiceSummary() != null
				&& !bufferDto.getInvoiceSummary().isEmpty()) {
			bufferDto.setSummaryAmtUsd(agentTotalUsd);
			bufferDto.setSummaryAmtVnd(agentTotalVnd);
			sumList.add(bufferDto);
		}
		return sumList;
	}

}
