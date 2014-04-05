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
		if(id !=null){
			Rate rate = em.find(Rate.class, id);
			return rate;
		}
		return null;
	}
	public List<Rate> getRateByDatetime(Date datetime) {
		if(datetime !=null){
			StringBuffer strbuffer = new StringBuffer(); 
			strbuffer.append("Select rate ")
			         .append("From Rate as rate ")
			         .append("Where rate.datetime = ?1");
			Query query = em.createQuery(strbuffer.toString());
			query.setParameter(1, datetime);
			return  query.getResultList();
		}
		return null;
	}
	public void insert(Rate rate){
		if(rate !=null){
			em.persist(rate);
			em.flush();
		}
		
	}
	public void update(Rate rate){
		if(rate !=null){
			Rate curRate = em.find(Rate.class,rate.getId());
			curRate.setRate(rate.getRate());
			em.merge(rate);
			em.flush();
		}
	}
	public void delete(Rate rate){
		if(rate !=null){
			Rate curRate = em.find(Rate.class,rate.getId());
			em.remove(curRate);
			em.flush();
		}
	}
}
