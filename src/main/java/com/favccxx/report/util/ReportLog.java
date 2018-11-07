package com.favccxx.report.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.jboss.logging.Logger;

import com.favccxx.report.constants.LogConstants;

public class ReportLog {
	
	private static Logger logger = Logger.getLogger(ReportLog.class);
	
	public static void request(String url) {
		StringBuffer sb = new StringBuffer();
		try {
			sb.append("").append(" ")
			.append(LogConstants.APP_NAME).append(" ")
			.append(LogConstants.MODULE_REPORT).append(" ")
			.append(InetAddress.getLocalHost().getHostAddress()).append(" ")
			.append(LogConstants.METHOD_REQUEST).append(" ")
			.append(LogConstants.OPERATE_QUERY).append(" ")
			.append(url);
		} catch (UnknownHostException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		
		logger.info(sb.toString());
	}
	
	
	public static void response(String restult) {
		StringBuffer sb = new StringBuffer();
		try {
			sb.append("").append(" ")
			.append(LogConstants.APP_NAME).append(" ")
			.append(LogConstants.MODULE_REPORT).append(" ")
			.append(InetAddress.getLocalHost().getHostAddress()).append(" ")
			.append(LogConstants.METHOD_RESPONSE).append(" ")
			.append(LogConstants.OPERATE_QUERY).append(" ")
			.append(restult);
		} catch (UnknownHostException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		
		logger.info(sb.toString());
	}
	
	
	

}
