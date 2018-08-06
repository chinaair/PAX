package com.chinaair.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {
	
	public final static String YEAR = "YEAR";
    public final static String MONTH = "MONTH";
    public final static String DAY = "DAY";
    public final static String HOUR = "HOUR";
    public final static String HOUR_OF_DAY = "HOUR_OF_DAY";
    public final static String MINUTE = "MINUTE";
    public final static String SECOND = "SECOND";
	
	public static Date getFirstDateOfMonth(Date date) {
		if (date == null) {
			return null;
		}
		String yearMonth = formatDateString(date, "yyyyMM");

		return getFirstDateOfMonth(yearMonth);
	}
	 
	public static Date getLastDateOfMonth(Date date) {
		if (date == null) {
			return null;
		}
		String yearMonth = formatDateString(date, "yyyyMM");
		return getLastDateOfMonth(yearMonth);
	}
	
	public static Date getFirstDateOfMonth(String yearMonth) {
        GregorianCalendar calendar = null;
        if (yearMonth == null || yearMonth.isEmpty()) {
            return null;
        }
        yearMonth = yearMonth.replaceAll("/", "");
        int year = Integer.valueOf(yearMonth.substring(0, 4)).intValue();
        int month = Integer.valueOf(yearMonth.substring(4, 6)).intValue() - 1;
        calendar = new GregorianCalendar();
        calendar.clear();
        calendar.setGregorianChange(new Date(Long.MIN_VALUE));
        calendar.set(year, month, 1, 0, 0, 0);

        return calendar.getTime();
    }
	
	public static Date getLastDateOfMonth(String yearMonth) {
        if (yearMonth == null || yearMonth.isEmpty()) {
            return null;
        }
        yearMonth = yearMonth.replaceAll("/", "");
        int year = Integer.valueOf(yearMonth.substring(0, 4)).intValue();
        int month = Integer.valueOf(yearMonth.substring(4, 6)).intValue() - 1;
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.clear();
        calendar.setGregorianChange(new Date(Long.MIN_VALUE));
        calendar.set(year, month, 1, 23, 59, 59);
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);

        return calendar.getTime();
    }
	
	public static Date addDate(Date date, String type, int index) {
        if (date == null || type == null || type.length() == 0 || index == 0) {
            return date;
        }
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setGregorianChange(new Date(Long.MIN_VALUE));
        calendar.setTime(date);
        if (YEAR.equals(type.toUpperCase())) {
            calendar.add(Calendar.YEAR, index);
        } else if (MONTH.equals(type.toUpperCase())) {
            calendar.add(Calendar.MONTH, index);
        } else if (DAY.equals(type.toUpperCase())) {
            calendar.add(Calendar.DATE, index);
        } else if (HOUR.equals(type.toUpperCase())) {
            calendar.add(Calendar.HOUR, index);
        } else if (HOUR_OF_DAY.equals(type.toUpperCase())) {
            calendar.add(Calendar.HOUR_OF_DAY, index);
        } else if (MINUTE.equals(type.toUpperCase())) {
            calendar.add(Calendar.MINUTE, index);
        } else if (SECOND.equals(type.toUpperCase())) {
            calendar.add(Calendar.SECOND, index);
        }
        return calendar.getTime();
    }
	
	public static String formatDateString(Date date, String format) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }
	
	public static String getYear(Date date) {
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(
                "yyyy");

        return formatter.format(date);

    }
	
	public static String getMonth(Date date){
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(
                "MM");
        return formatter.format(date);

    }
	
	public static String getDay(Date date){
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(
                "dd");
        return formatter.format(date);

    }
	
}
