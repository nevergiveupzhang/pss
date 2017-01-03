package com.psssystem.client.ui.filter;

import java.text.ParseException;
import java.util.regex.Pattern;

import javax.swing.text.DefaultFormatter;

public class EmailFilter extends DefaultFormatter{
	public String valueToString(Object value) throws ParseException
	   {
		if(!(value instanceof String)) throw new ParseException("非字符串", 0);
		
		if(!Pattern.compile("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$",Pattern.CASE_INSENSITIVE).matcher((String)value).find()){
			
		}
	      StringBuilder builder = new StringBuilder();
	      builder.append(value);
	      return builder.toString();
	   }

	   public Object stringToValue(String text) throws ParseException
	   {
	      return text;
	   }
}
