package com.chinaair.services;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.chinaair.entity.EmployeeAuthor;
import com.chinaair.entity.EmployeeAuthorPK;

@Stateless
@LocalBean
public class AuthorityServiceBean {
	
	@PersistenceContext(unitName = "chinaair_pax")
	private EntityManager em;
	
	public EmployeeAuthor findEmpAuthor(Long empId, String authorCode) {
		return em.find(EmployeeAuthor.class, new EmployeeAuthorPK(empId, authorCode));
	}

}
