package com.psssystem.server.util;

import java.util.ArrayList;
import java.util.List;

public class ArrayUtils{
	public static String[] removeDuplicated(String[] values){
		List<String> filterResultList=new ArrayList<String>();
		for(String value:values){
			if(!filterResultList.contains(value)){
				filterResultList.add(value);
			}
		}
		String[]noRepeatArr=new String[filterResultList.size()];
		for(int i=0;i<filterResultList.size();i++){
			noRepeatArr[i]=filterResultList.get(i);
		}
		
		return noRepeatArr;
	}
}
