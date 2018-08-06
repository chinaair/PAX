package com.chinaair.services;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.chinaair.entity.Company;
import com.chinaair.entity.Employee;
import com.mysql.jdbc.StringUtils;

@Stateless
@LocalBean
public class EmployeeServiceBean {
	
	@PersistenceContext(unitName = "chinaair_pax")
	private EntityManager em;
	
	public List<Employee> getAllEmployee() {
		return em.createQuery("SELECT a FROM Employee a", Employee.class).getResultList();
	}
	

	public Employee insert(Employee newEmployee){
		if(newEmployee !=null){
			em.persist(newEmployee);
			em.flush();
			return newEmployee;
		}
		return null;
	}
	
	public Employee update(Employee newEmployee){
		if(newEmployee !=null){
			em.merge(newEmployee);
			em.flush();	
		}
		return newEmployee;
	}
	public void delete(Employee employee){
		if(employee !=null){
			Employee oldEmployee = em.find(Employee.class, employee.getId());
			oldEmployee.setUsageFlag(employee.isUsageFlag());
			em.remove(oldEmployee);
			em.flush();	
		}
	}
	public List<Employee> findEmployeeByCondition(String empName,String userId){
		String sql = "SELECT r FROM Employee r WHERE  1=1 ";
		if(!StringUtils.isNullOrEmpty(empName)){
			sql += " and r.empName like :empName " ; 
		}
		
		if(!StringUtils.isNullOrEmpty(userId)){
			sql += "  and r.userId like :userId " ; 
		}
		Query query = em.createQuery(sql, Company.class);
		if(!StringUtils.isNullOrEmpty(empName)){
			  query.setParameter("empName", "%" + empName + "%");
		}
		if(!StringUtils.isNullOrEmpty(userId)){
			query.setParameter("userId", "%" + userId + "%");
		}
		return query.getResultList();
	}

	public Employee findEmployeeByUserId(Employee employee, boolean isUpdate) {
		if(employee == null){
			return null;
		}
		String sql = "SELECT r FROM Employee r WHERE  r.userId = :userId ";
		if (isUpdate) {
			sql += " and r.id != :id";
		}
		Query query = em.createQuery(sql, Employee.class);
		query.setParameter("userId", employee.getUserId());
		if (isUpdate) {
			query.setParameter("id", employee.getId());
		}
		List<Employee> list = query.getResultList();
		if(list!=null && !list.isEmpty()){
			return list.get(0);
		}
		return null;
	}
	public Employee login(String userId,String password) {
		if(StringUtils.isNullOrEmpty(userId) || StringUtils.isNullOrEmpty(password) ){
			return null;
		}
		String sql = "SELECT r FROM Employee r WHERE  r.userId = :userId and r.password = :password and r.usageFlag = 1";
		Query query = em.createQuery(sql, Employee.class);
		query.setParameter("userId", userId);
			query.setParameter("password", password);
		List<Employee> list = query.getResultList();
		if(list!=null && !list.isEmpty()){
			return list.get(0);
		}
		return null;
	}
}
