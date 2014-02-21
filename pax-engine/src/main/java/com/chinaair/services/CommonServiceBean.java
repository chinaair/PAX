package com.chinaair.services;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.chinaair.entity.Customer;

@Stateless
@LocalBean
public class CommonServiceBean {
	
	@PersistenceContext(unitName = "chinaair_pax")
	private EntityManager em;
	
	public List<Customer> getAllCustomer() {
		return em.createQuery("select cust from Customer cust", Customer.class).getResultList();
	}
	
	public Customer getOneCustomer() {
		return em.find(Customer.class, 1L);
	}

}
