package com.chinaair.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.chinaair.entity.Menu;

@Stateless
@LocalBean
public class MenuServiceBean {
	
	@PersistenceContext(unitName = "chinaair_pax")
	private EntityManager em;
	
	public List<Menu> getAllMenu() {
		String sql = " SELECT a.* ,b.MENU as parentMenu ";
			sql   += " FROM MENU a Left join MENU b ON b.ID = a.PARENTID " ;
			sql   += " ORDER by a.LEVEL asc, a.ID asc,a.PARENTID asc ;  ";
		Query query = em.createNativeQuery(sql);
		List<Menu> menulist = new ArrayList<>();
		Vector<Object> list =  (Vector<Object>) query.getResultList();
		Iterator<Object> iterator = list.iterator();
		while (iterator.hasNext()){
			Object[] obj = (Object[]) iterator.next();
			Menu menu = new Menu();
			menu.setId(new Long(obj[0].toString()));
			menu.setMenu(obj[1].toString());
			menu.setMenu_vi(obj[2].toString());
			menu.setLevel(Integer.parseInt(obj[3].toString()));
			if(obj[4] != null){
				menu.setParentId(new Long(obj[4].toString()));
				
			}
			if("1".equals(obj[5])){
				menu.setUseFlag(true);	
			} else{
				menu.setUseFlag(false);
			}
			
			if(obj[6] != null){
				menu.setLink((String) obj[6]);				
			}else{
				menu.setLink("");	
			}
			
			if(obj[7] != null){
				menu.setIcon((String) obj[7]);				
			}else{
				menu.setIcon("");	
			}
			
			if(obj[8] != null){
				menu.setParentMenu((String) obj[8]);
			}else{
				menu.setParentMenu("");
			}
			menulist.add(menu);
		}
		  return menulist;
	}
	public List<Menu> getAllMenuChild() {
		String sql = " SELECT a.* ,b.MENU as parentMenu ";
			sql   += " FROM MENU a Left join MENU b ON b.ID = a.PARENTID " ;
			sql   += " WHERE a.LEVEL > 1 AND a.USEFLAG = 1" ;
			sql   += " ORDER by a.LEVEL asc, a.ID asc,a.PARENTID asc ;  ";
		Query query = em.createNativeQuery(sql);
		List<Menu> menulist = new ArrayList<>();
		Vector<Object> list =  (Vector<Object>) query.getResultList();
		Iterator<Object> iterator = list.iterator();
		while (iterator.hasNext()){
			Object[] obj = (Object[]) iterator.next();
			Menu menu = new Menu();
			menu.setId(new Long(obj[0].toString()));
			menu.setMenu(obj[1].toString());
			menu.setMenu_vi(obj[2].toString());
			menu.setLevel(Integer.parseInt(obj[3].toString()));
			if(obj[4] != null){
				menu.setParentId(new Long(obj[4].toString()));
				
			}
			if("1".equals(obj[5])){
				menu.setUseFlag(true);	
			} else{
				menu.setUseFlag(false);
			}
			
			if(obj[6] != null){
				menu.setLink((String) obj[6]);				
			}else{
				menu.setLink("");	
			}
			
			if(obj[7] != null){
				menu.setIcon((String) obj[7]);				
			}else{
				menu.setIcon("");	
			}
			
			if(obj[8] != null){
				menu.setParentMenu((String) obj[8]);
			}else{
				menu.setParentMenu("");
			}
			menulist.add(menu);
		}
		  return menulist;
	}
	
	public List<Menu> getMenuByParentId(Long parentId) {
		String sql = " SELECT a.* ,b.MENU as parentMenu ";
			sql   += " FROM MENU a Left join MENU b ON b.ID = a.PARENTID " ;
			sql   += " WHERE a.PARENTID = "+ parentId +" AND a.USEFLAG = 1 ";
			sql   += " ORDER by a.LEVEL asc, a.ID asc,a.PARENTID asc ;  ";
		Query query = em.createNativeQuery(sql);
				//query.setParameter("parentId", parentId);
		List<Menu> menulist = new ArrayList<>();
		Vector<Object> list =  (Vector<Object>) query.getResultList();
		Iterator<Object> iterator = list.iterator();
		while (iterator.hasNext()){
			Object[] obj = (Object[]) iterator.next();
			Menu menu = new Menu();
			menu.setId(new Long(obj[0].toString()));
			menu.setMenu(obj[1].toString());
			menu.setMenu_vi(obj[2].toString());
			menu.setLevel(Integer.parseInt(obj[3].toString()));
			if(obj[4] != null){
				menu.setParentId(new Long(obj[4].toString()));
				
			}
			if("1".equals(obj[5])){
				menu.setUseFlag(true);	
			} else{
				menu.setUseFlag(false);
			}
			
			if(obj[6] != null){
				menu.setLink((String) obj[6]);				
			}else{
				menu.setLink("");	
			}
			
			if(obj[7] != null){
				menu.setIcon((String) obj[7]);				
			}else{
				menu.setIcon("");	
			}
			
			if(obj[8] != null){
				menu.setParentMenu((String) obj[8]);
			}else{
				menu.setParentMenu("");
			}
			menulist.add(menu);
		}
		  return menulist;
	}
	
	public Menu insert(Menu newMenu){
		if(newMenu !=null){
			em.persist(newMenu);
			em.flush();
			return newMenu;
		}
		return null;
	}
	
	public Menu update(Menu newMenu){
		if(newMenu !=null){
			Menu oldMenu = em.find(Menu.class, newMenu.getId());
			em.merge(newMenu);
			em.flush();	
		}
		return newMenu;
	}
	public void delete(Menu menu){
		if(menu !=null){
			Menu oldMenu = em.find(Menu.class, menu.getId());
			em.remove(oldMenu);
			em.flush();	
		}
	}
	public Menu findMenuByMenuId(Long menuId){
		if(menuId !=null){
			Menu menu = em.find(Menu.class, menuId);
			return menu;
		}
		return null;
	}
	public List<Menu> findMenuByCondition(Menu menu,boolean isUpdate){
		String sql = "SELECT r FROM Menu r WHERE  r.menu = :menu ";
		if (isUpdate) {
			sql += " and r.id != :id";
		}
		
		Query query = em.createQuery(sql, Menu.class);
			  query.setParameter("menu", menu.getMenu() );
			  if (isUpdate) {
					query.setParameter("id", menu.getId());
				}
		  return query.getResultList();
	}
	public List<Menu> findMenuByCondition(String menu){
		String sql = "SELECT r FROM Menu r WHERE  r.menu like :menu ";
		return em.createQuery(sql, Menu.class)
				.setParameter("menu", "%" + menu + "%").getResultList();
	}
	
	public List<Menu> findParentMenu(int level){
		String sql = "SELECT a FROM Menu a   ";
		sql += " Where a.level = :level ";
		sql += "   Order by a.level, a.id ,a.parentId asc " ;
		return em.createQuery(sql, Menu.class).setParameter("level", level)
				.getResultList();
	}
	public List<Menu> getParentMenu(int level){
		String sql = "SELECT a FROM Menu a   ";
		sql += " Where a.level = :level AND a.useFlag = 1";
		sql += "   Order by a.level, a.id ,a.parentId asc " ;
		return em.createQuery(sql, Menu.class).setParameter("level", level)
				.getResultList();
	}
	public List<Menu> getParentMenuByChildMenuId(String childMenuList){
		String sql = "SELECT DISTINCT a.*   ";
		sql += "  FROM MENU a INNER JOIN MENU b on a.ID = b.PARENTID ";
		sql += "   WHERE b.ID in ("+ childMenuList +") AND a.USEFLAG = 1  " ;
		sql += "   ORDER BY a.LEVEL, a.ID ,a.PARENTID ASC " ;
		return em.createNativeQuery(sql, Menu.class).getResultList();
	}
}
