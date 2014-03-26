package com.chinaair.services;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.chinaair.entity.Agent;

@Stateless
@LocalBean
public class AgentServiceBean {
	
	@PersistenceContext(unitName = "chinaair_pax")
	private EntityManager em;
	
	public List<Agent> getAllAgent() {
		return em.createQuery("SELECT a FROM Agent a", Agent.class).getResultList();
	}

}
