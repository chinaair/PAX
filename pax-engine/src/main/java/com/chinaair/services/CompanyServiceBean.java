package com.chinaair.services;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.chinaair.entity.Company;
import com.mysql.jdbc.StringUtils;

@Stateless
@LocalBean
public class CompanyServiceBean {
	
	@PersistenceContext(unitName = "chinaair_pax")
	private EntityManager em;
	
	public List<Company> getAllCompany() {
		return em.createQuery("SELECT a FROM Company a", Company.class).getResultList();
	}
	

	public Company insert(Company newCompany){
		if(newCompany !=null){
			em.persist(newCompany);
			em.flush();
			return newCompany;
		}
		return null;
	}
	
	public Company update(Company newCompany){
		if(newCompany !=null){
			em.merge(newCompany);
			em.flush();	
		}
		return newCompany;
	}
	public void delete(Company company){
		if(company !=null){
			Company oldCompany = em.find(Company.class, company.getId());
			em.remove(oldCompany);
			em.flush();	
		}
	}
	public List<Company> searchCompanyByCondition(String companyName, String companyVat){
		String sql = "SELECT c FROM Company c WHERE 1 = 1 ";
		if(!StringUtils.isNullOrEmpty(companyName)) {
			sql += " AND c.company LIKE :name ";
		}
		if(!StringUtils.isNullOrEmpty(companyVat)) {
			sql += " AND c.vat LIKE :vat ";
		}
		Query query = em.createQuery(sql, Company.class);
		if(!StringUtils.isNullOrEmpty(companyName)) {
			query.setParameter("name", "%" + companyName.trim() + "%");
		}
		if(!StringUtils.isNullOrEmpty(companyVat)) {
			query.setParameter("vat", "%" + companyVat.trim() + "%");
		}
		return query.getResultList();
	}
	
	public List<Company> findCompanyByVatCode(String vatCode){
		String sql = "SELECT r FROM Company r WHERE r.vat = :vatCode";
		return em.createQuery(sql, Company.class)
				.setParameter("vatCode", vatCode).getResultList();
	}
}
