package com.chinaair.services;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.chinaair.dto.AgentSearchDto;
import com.chinaair.entity.Agent;
import com.mysql.jdbc.StringUtils;

@Stateless
@LocalBean
public class AgentServiceBean {
	
	@PersistenceContext(unitName = "chinaair_pax")
	private EntityManager em;
	
	public List<Agent> getAllAgent() {
		return em.createQuery("SELECT a FROM Agent a", Agent.class).getResultList();
	}
	

	public Agent insert(Agent newAgent){
		if(newAgent !=null){
			em.persist(newAgent);
			em.flush();
			return newAgent;
		}
		return null;
	}
	
	public Agent update(Agent newAgent){
		if(newAgent !=null){
			em.merge(newAgent);
			em.flush();	
		}
		return newAgent;
	}
	public void delete(Agent agent){
		if(agent !=null){
			Agent oldAgent = em.find(Agent.class, agent.getId());
			em.remove(oldAgent);
			em.flush();	
		}
	}
	public Agent findAgentByCode(String agentCode, String location, Long id){
		String sql = "SELECT r FROM Agent r WHERE r.code = :code";
		if(!StringUtils.isNullOrEmpty(location)) {
			sql += " AND r.location = :location ";
		}
		if(id != null){
			sql += " AND r.id != :id ";
		}
		Query query = em.createQuery(sql, Agent.class);
		query.setParameter("code", agentCode);
		if(!StringUtils.isNullOrEmpty(location)) {
			query.setParameter("location", location);
		}
		  if(id != null){
			  query.setParameter("id", id);
		  }
		 List<Agent> foundAgent	= query.getResultList();
		 if(!foundAgent.isEmpty()) {
				return foundAgent.get(0);
			}
			return null;
	}
	@SuppressWarnings("unchecked")
	public List<Agent> findAgentByCodition(AgentSearchDto searchDto){
		String sql = "SELECT agent FROM Agent agent WHERE 1 = 1 ";
		if(searchDto != null) {
			if(!StringUtils.isNullOrEmpty(searchDto.getAgentCode())){
				sql += " and agent.code like :code ";
			}
			if(!StringUtils.isNullOrEmpty(searchDto.getAgentName())){
				sql += " and agent.name like :name ";
			}
			if(!StringUtils.isNullOrEmpty(searchDto.getVatCode())){
				sql += " and agent.vat_code like :vat ";
			}
			if(!StringUtils.isNullOrEmpty(searchDto.getType())){
				sql += " and agent.type = :type ";
			}
			if(!StringUtils.isNullOrEmpty(searchDto.getLocation())){
				sql += " and agent.location = :location ";
			}
		}
		Query query = em.createQuery(sql, Agent.class);
		if(searchDto != null) {
			if(!StringUtils.isNullOrEmpty(searchDto.getAgentCode())){
				query.setParameter("code", "%" + searchDto.getAgentCode().trim() + "%");
			}
			if(!StringUtils.isNullOrEmpty(searchDto.getAgentName())){
				query.setParameter("name", "%" + searchDto.getAgentName().trim() + "%");
			}
			if(!StringUtils.isNullOrEmpty(searchDto.getVatCode())){
				query.setParameter("vat", "%" + searchDto.getVatCode().trim() + "%");
			}
			if(!StringUtils.isNullOrEmpty(searchDto.getType())){
				query.setParameter("type", searchDto.getType());
			}
			if(!StringUtils.isNullOrEmpty(searchDto.getLocation())){
				query.setParameter("location", searchDto.getLocation());
			}
		}
		return query.getResultList();
	}
	
	public List<Agent> findAgentByVatCode(String vatCode){
		String sql = "SELECT a FROM Agent a WHERE a.vat_code = :vatCode";
		return em.createQuery(sql, Agent.class)
				.setParameter("vatCode", vatCode).getResultList();
	}
}
