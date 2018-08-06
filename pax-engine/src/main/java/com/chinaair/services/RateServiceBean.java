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
	public List<Rate> getRateList(Date startDate, Date endDate) {
		StringBuffer strbuffer = new StringBuffer(); 
		strbuffer.append("SELECT r ")
		         .append("FROM Rate r ");
		if(startDate != null) {
			strbuffer.append(" WHERE r.datetime >= :startDate ");
			if(endDate != null) {
				strbuffer.append(" AND r.datetime <= :endDate ");
			}
		}
		strbuffer.append(" ORDER BY r.datetime DESC, r.id DESC");
		Query query = em.createQuery(strbuffer.toString(), Rate.class);
		if(startDate != null) {
			query.setParameter("startDate", startDate);
			if(endDate != null) {
				query.setParameter("endDate", endDate);
			}
		}
		return query.getResultList();
	}
	@SuppressWarnings("unchecked")
	public List<Rate> getRateList(int first, int pageSize) {
		StringBuffer strbuffer = new StringBuffer(); 
		strbuffer.append("SELECT r ")
		         .append("FROM Rate r ");
		strbuffer.append(" ORDER BY r.datetime DESC, r.id DESC");
		Query query = em.createQuery(strbuffer.toString(), Rate.class);
		query.setFirstResult(first);
		query.setMaxResults(pageSize);
		return query.getResultList();
	}
	public List<Rate> getRateList(Date startDate, Date endDate,int first, int pageSize) {
		StringBuffer strbuffer = new StringBuffer(); 
		strbuffer.append("SELECT r ")
		         .append("FROM Rate r ");
		if(startDate != null) {
			strbuffer.append(" WHERE r.datetime >= :startDate ");
			if(endDate != null) {
				strbuffer.append(" AND r.datetime <= :endDate ");
			}
		}
		strbuffer.append(" ORDER BY r.datetime DESC, r.id DESC");
		Query query = em.createQuery(strbuffer.toString(), Rate.class);
		if(startDate != null) {
			query.setParameter("startDate", startDate);
			if(endDate != null) {
				query.setParameter("endDate", endDate);
			}
		}
		query.setFirstResult(first);
		query.setMaxResults(pageSize);
		return query.getResultList();
	}
	public int countRateList(Date startDate,Date endDate) {
		StringBuffer strbuffer = new StringBuffer(); 
		strbuffer.append("SELECT r ")
		         .append("FROM Rate r ");
		if(startDate != null) {
			strbuffer.append(" WHERE r.datetime >= :startDate ");
			if(endDate != null) {
				strbuffer.append(" AND r.datetime <= :endDate ");
			}
		}
		Query query = em.createQuery(strbuffer.toString(), Rate.class);
		if(startDate != null) {
			query.setParameter("startDate", startDate);
			if(endDate != null) {
				query.setParameter("endDate", endDate);
			}
		}
		return query.getResultList().size();
	}
	public Rate getRateById(Long id) {
		if(id !=null){
			Rate rate = em.find(Rate.class, id);
			return rate;
		}
		return null;
	}
	
	public Rate insert(Rate rate){
		if(rate !=null){
			em.persist(rate);
			em.flush();
			return rate;
		}
		return null;
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
	
	public Rate getTodayRate() {
		List<Rate> foundRate = em.createQuery("SELECT r FROM Rate r WHERE r.datetime = :today ORDER BY r.id DESC", Rate.class)
			.setParameter("today", new Date()).getResultList();
		if(!foundRate.isEmpty()) {
			return foundRate.get(0);
		}
		return null;
	}
	
	public Rate getNearestRate() {
		List<Rate> foundRate = em.createQuery("SELECT r FROM Rate r ORDER BY r.datetime DESC, r.id DESC", Rate.class)
				.setMaxResults(1).getResultList();
		if(!foundRate.isEmpty()) {
			return foundRate.get(0);
		}
		return null;
	}
	
	public Rate getRateByDate(Date rateDate) {
		List<Rate> foundRate = em.createQuery("SELECT r FROM Rate r WHERE r.datetime = :rateDate", Rate.class).setParameter("rateDate", rateDate).getResultList();
		if(!foundRate.isEmpty()) {
			return foundRate.get(0);
		}
		return null;
	}
	
}
