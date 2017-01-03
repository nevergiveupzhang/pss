package com.psssystem.server.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogUtils {
	private static String FILE_NAME="log";
	public static void setLogProperties(Logger log,String className){
		setLogProperties(log,Level.ALL,className);
	}
	
	public static void setLogProperties(Logger log, Level level,String className) {
		if(System.getProperty("java.util.lang.logging.config.file")==null&&System.getProperty("java.util.lang.logging.config.class")==null){
			try{
				Logger.getLogger(className).setLevel(level);
				final int COUNT=10;
				Handler handler=new FileHandler(getLogName(),0,COUNT);
				Logger.getLogger(className).addHandler(handler);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	private static String getLogName() {
        StringBuffer logPath = new StringBuffer();
        logPath.append(FILE_NAME);
        File file = new File(logPath.toString());
        if (!file.exists())
            file.mkdir();
       
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        logPath.append("//"+sdf.format(new Date())+".log");
       
        return logPath.toString();
    }
	private LogUtils(){}
}
