package com.chinaair.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {
	
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
	
	public static String formatDateString(Date date, String format) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }

}
