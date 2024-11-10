package org.crazyit.activiti.oa.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	public static String getTodayString() {
		Date d = new Date();
		return sdf.format(d);
	}
	
	public static Date getDate(String date) {
		try {
			return sdf.parse(date);
		} catch (Exception e) {
			throw new RuntimeException("parse date error");
		}	
	}
	
	public static String getDateString(Date d) {
		return sdf.format(d);
	}
}
