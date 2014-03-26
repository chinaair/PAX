package com.chinaair.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.chinaair.entity.Customer;

@Stateless
@LocalBean
public class CommonUtils {
	
	public static boolean isThisDateValid(String dateToValidate, String dateFromat){
		 
		if(dateToValidate == null){
			return false;
		}
 
		SimpleDateFormat sdf = new SimpleDateFormat(dateFromat);
		sdf.setLenient(false);
 
		try {
 
			//if not valid, it will throw ParseException
			Date date = sdf.parse(dateToValidate);
 
		} catch (ParseException e) {
			return false;
		}
		return true;
	}

}
