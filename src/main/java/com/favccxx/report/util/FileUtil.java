package com.favccxx.report.util;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class FileUtil {
	
	/**
	 * 报表生成目录
	 * @return
	 */
	public static final String getReportPath() {
		
		 
		
		String filePath = "";
		
		Context c;
		try {
			c = new InitialContext();
			filePath = (String)c.lookup("REPORT_FILE_PATH");  
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
		
		
		return filePath;
	}

}
