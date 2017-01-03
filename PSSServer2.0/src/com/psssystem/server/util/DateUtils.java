package com.psssystem.server.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	public static boolean isToday(String date){
		Calendar c1=Calendar.getInstance();
		Calendar c2=Calendar.getInstance();
		try {
			c1.setTime(new SimpleDateFormat("yyyyMMdd").parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c2.setTime(new Date());
		return c1.get(Calendar.YEAR)==c2.get(Calendar.YEAR)&&c1.get(Calendar.MONTH)==c2.get(Calendar.MONTH)&&c1.get(Calendar.DAY_OF_MONTH)==c2.get(Calendar.DAY_OF_MONTH);
	}
	
	public static void main(String[]args){
		System.out.println(DateUtils.isToday("20141113"));
	}
}
