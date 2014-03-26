package com.chinaair.services;

import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.chinaair.entity.Rate;

@Stateless
@LocalBean
public class RateServiceBean {
	
	@PersistenceContext(unitName = "chinaair_pax")
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	public List<Rate> getRateList() {
		StringBuffer strbuffer = new StringBuffer(); 
		strbuffer.append("Select rate ")
		         .append("From Rate as rate ")
		         .append("Order by rate.datetime desc");
		Query query = em.createQuery(strbuffer.toString());
		return query.getResultList();
	}
	
	public Rate getRateById(Long id) {
		Rate rate = em.find(Rate.class, id);
		return rate;
	}
	public Rate getRateByDatetime(Date datetime) {
		StringBuffer strbuffer = new StringBuffer(); 
		strbuffer.append("Select rate ")
		         .append("From Rate as rate ")
		         .append("Where rate.datetime = ?1");
		Query query = em.createQuery(strbuffer.toString());
		query.setParameter(1, datetime);
		List<Rate> list = query.getResultList(); 
		return list.get(0);
	}
	public void insert(Rate rate){
		em.persist(rate);
		em.flush();
	}
	public void update(Rate rate){
		/*RateInfo curRate = em.find(RateInfo.class,rate.getId());
		curRate.setDatetime(rate.getDatetime());
		curRate.setRate(rate.getRate());
		em.merge(rate);
		em.flush();*/
	}
	public void delete(Rate rate){
		em.remove(rate);
		em.flush();
	}
}
