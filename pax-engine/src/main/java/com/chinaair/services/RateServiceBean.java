package com.chinaair.services;

import java.sql.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;



import com.chinaair.entity.RateInfo;

@Stateless
@LocalBean
public class RateServiceBean {
	
	@PersistenceContext(unitName = "chinaair_pax")
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	public List<RateInfo> getRateList() {
		StringBuffer strbuffer = new StringBuffer(); 
		strbuffer.append("Select rate ")
		         .append("From RateInfo as rate ")
		         .append("Order by rate.datetime desc");
		Query query = em.createQuery(strbuffer.toString());
		return query.getResultList();
	}
	
	public RateInfo getRateById(Long id) {
		RateInfo rate = em.find(RateInfo.class, id);
		return rate;
	}
	public RateInfo getRateByDatetime(Date datetime) {
		/*StringBuffer strbuffer = new StringBuffer(); 
		strbuffer.append("Select rate ")
		         .append("From RateInfo as rate ")
		         .append("Where rate.datetime = ?1");
		Query query = em.createQuery(strbuffer.toString());
		query.setParameter(1, datetime);
		List<RateInfo> list = query.getResultList(); 
		return list.get(0);*/
		return null;
	}
	public void insert(RateInfo rate){
		em.persist(rate);
		em.flush();
	}
	public void update(RateInfo rate){
		/*RateInfo curRate = em.find(RateInfo.class,rate.getId());
		curRate.setDatetime(rate.getDatetime());
		curRate.setRate(rate.getRate());
		em.merge(rate);
		em.flush();*/
	}
	public void delete(RateInfo rate){
		em.remove(rate);
		em.flush();
	}
}
