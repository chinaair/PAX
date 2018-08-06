package com.chinaair.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.chinaair.entity.Master;
import com.chinaair.entity.MasterPK;
import com.mysql.jdbc.StringUtils;

@Stateless
@LocalBean
public class MasterServiceBean {
	
	@PersistenceContext(unitName = "chinaair_pax")
	private EntityManager em;
	
	public Master findMaster(String masterNo, String detailNo) {
		if(masterNo == null || detailNo == null) {
			return null;
		}
		MasterPK pk = new MasterPK(masterNo, detailNo);
		return em.find(Master.class, pk);
	}
	
	public List<Master> findByMasterParent(String masterParentNo) {
		if(masterParentNo == null) {
			throw new RuntimeException("Parameter masterParentNo is null.");
		}
		return em
				.createQuery(
						"SELECT m FROM Master m WHERE m.masterParent = :masterParentNo", Master.class)
				.setParameter("masterParentNo", masterParentNo).getResultList();
	}
	
	public Master findByMasterNo(String masterNo) {
		if(masterNo == null) {
			throw new RuntimeException("Parameter masterNo is null.");
		}
		return em.find(Master.class, masterNo);
	}
	public List<Master> findParentMaster(Long level){
		String sql = "SELECT a FROM Master a   ";
		sql += " Where a.level = :level ";
		sql += "   Order by a.level, a.masterNo " ;
		Query query = em.createQuery(sql, Master.class);
			query.setParameter("level", level);
		  return query.getResultList();
	}
	
	public List<Master> getAllMaster(boolean isChild) {
		String sql = " SELECT a.* ,b.NAME as masterParent "; 
		sql += " FROM MASTER a Left join MASTER b ON b.MASTERNO = a.MASTERParent  ";
		if (isChild) {
			sql += "  WHERE a.LEVEL > 1 ";
		}
		sql += " ORDER by a.MASTERNO asc,a.MASTERParent asc ;  ";
		Query query = em.createNativeQuery(sql);
		List<Master> masterlist = new ArrayList<>();
		Vector<Object> list =  (Vector<Object>) query.getResultList();
		Iterator<Object> iterator = list.iterator();
		while (iterator.hasNext()){
			Object[] obj = (Object[]) iterator.next();
			Master master = new Master();
			master.setMasterNo(obj[0].toString());
			if (obj[1] != null) {
				master.setMasterParent(obj[1].toString());
			}
			if (obj[2] != null) {
				master.setName(obj[2].toString());
			}
			if (obj[3] != null) {
				master.setValue(obj[3].toString());
			}
			if (obj[4] != null) {
				master.setLevel(new Long(obj[4].toString()));
			}
			if(obj[5] != null){
				master.setParentMasterName(obj[5].toString());
			}
			masterlist.add(master);
		}
		  return masterlist;
	}

	public List<Master> findMasterByCondition(String name){
		String sql = "SELECT r.* FROM Master r WHERE  r.name like :name ";
		Query query = em.createQuery(sql, Master.class);
			  query.setParameter("name", "'%" + name + "%'");
		  return query.getResultList();
	}
	public List<Master> getMasterByParentMaster(String parentMaster) {
		String sql = " SELECT a.* ,b.NAME as parentMaster ";
			sql   += " FROM Master a Left join Master b ON b.MASTERNO = a.MASTERPARENT " ;
			if(parentMaster!=null){
				sql   += " WHERE a.masterParent = '"+ parentMaster +"' ";	
			} 
			sql   += " ORDER by a.LEVEL asc, a.masterNo asc,a.masterParent asc ;  ";
		Query query = em.createNativeQuery(sql);
		List<Master> masterlist = new ArrayList<>();
		Vector<Object> list =  (Vector<Object>) query.getResultList();
		Iterator<Object> iterator = list.iterator();
		while (iterator.hasNext()){
			Object[] obj = (Object[]) iterator.next();
			Master master = new Master();
			master.setMasterNo(obj[0].toString());
			if (obj[1] != null) {
				master.setMasterParent(obj[1].toString());	
			} else{
				master.setMasterParent("");
			}
			master.setName(obj[2].toString());
			master.setValue(obj[3].toString());
			master.setLevel(new Long(obj[4].toString()));
			if(obj[5] != null){
				master.setParentMasterName(obj[5].toString());		
			}
			masterlist.add(master);
		}
		  return masterlist;
	}
	
	public Master insert(Master newMaster){
		if(newMaster !=null){
			em.persist(newMaster);
			em.flush();
			return newMaster;
		}
		return null;
	}
	
	public Master update(Master newMaster){
		if(newMaster !=null){
			Master oldMaster = em.find(Master.class, newMaster.getMasterNo());
			em.merge(newMaster);
			em.flush();	
		}
		return newMaster;
	}
	public void delete(Master master){
		if(master !=null){
			Master oldMaster = em.find(Master.class, master.getMasterNo());
			em.remove(oldMaster);
			em.flush();	
		}
	}
	public Master findMasterByMasterId(Long masterNo){
		if(masterNo !=null){
			Master master = em.find(Master.class, masterNo);
			return master;
		}
		return null;
	}
	public List<Master> findMasterByCondition(Master master,boolean isUpdate){
		String sql = "SELECT r FROM Master r WHERE 1=1 ";
		if (isUpdate) {
			sql += " and r.name = :name";
			sql += " and r.masterNo != :masterNo";
		} else {
			sql += " and (r.name = :name or r.masterNo = :masterNo)";
		}
		
		Query query = em.createQuery(sql, Master.class);
			query.setParameter("name", master.getName());
			query.setParameter("masterNo", master.getMasterNo());
		  return query.getResultList();
	}

	public List<Master> searchMaster(Master searchMaster) {
		String sql = " SELECT a.* ,b.NAME as parentMaster ";
		sql += " FROM Master a Left join Master b ON b.MASTERNO = a.MASTERPARENT ";
		sql += " WHERE 1=1 ";
		Map<Integer,String> mapParam = new HashMap<Integer, String>();
		int index = 0;
		if (searchMaster != null) {
			if (!StringUtils.isNullOrEmpty(searchMaster.getName())) {
				index ++;
				sql += " and a.name = ?" + index;
				mapParam.put(index, searchMaster.getName());
			}
			if (!StringUtils.isNullOrEmpty(searchMaster.getMasterNo())) {
				index ++;
				sql += " and a.masterNo = ?" + index;
				mapParam.put(index, searchMaster.getMasterNo());
			}
			if (!StringUtils.isNullOrEmpty(searchMaster.getMasterParent())) {
				index ++;
				sql += " and a.MASTERPARENT = ?" + index;
				mapParam.put(index, searchMaster.getMasterParent());
			}
		}
		sql += " ORDER by a.masterNo asc,a.masterParent asc ;  ";
		Query query = em.createNativeQuery(sql);
		if (!mapParam.isEmpty()) {
			int i = 0;
			while(i < index){
				i++;
				query.setParameter(i,mapParam.get(i));
				
			}
			
		}
		
		List<Master> masterlist = new ArrayList<>();
		Vector<Object> list = (Vector<Object>) query.getResultList();
		Iterator<Object> iterator = list.iterator();
		while (iterator.hasNext()) {
			Object[] obj = (Object[]) iterator.next();
			Master master = new Master();
			master.setMasterNo(obj[0].toString());
			if (obj[1] != null) {
				master.setMasterParent(obj[1].toString());	
			} else{
				master.setMasterParent("");
			}
			master.setName(obj[2].toString());
			master.setValue(obj[3].toString());
			master.setLevel(new Long(obj[4].toString()));
			if (obj[5] != null) {
				master.setParentMasterName(obj[5].toString());
			}
			masterlist.add(master);
		}
		return masterlist;
	}
	
	public List<Master> findParentMaster(int level){
		String sql = "SELECT a FROM Master a   ";
		sql += " Where a.level = :level ";
		sql += "   Order by a.level, a.masterNo ,a.masterParent asc " ;
		Query query = em.createQuery(sql, Master.class);
			query.setParameter("level", level);
		  return query.getResultList();
	}
	public List<Master> getParentMasterByChildMasterId(String childMasterList){
		String sql = "SELECT DISTINCT a.*   ";
		sql += "  FROM Master a INNER JOIN Master b on a.masterNo = b.masterParent ";
		sql += "   WHERE b.masterNo in ("+ childMasterList +")   " ;
		sql += "   ORDER BY a.LEVEL, a.masterNo ,a.masterParent ASC " ;
		Query query = em.createNativeQuery(sql, Master.class);
		  return query.getResultList();
	}
}
