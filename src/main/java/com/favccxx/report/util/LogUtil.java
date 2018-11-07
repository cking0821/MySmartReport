package com.favccxx.report.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.favccxx.report.constants.LogConstants;

public class LogUtil {
	
	private static Logger logger = LogManager.getLogger();
	
	/**
	 * 记录操作日志
	 * @param module 模块名称
	 * @param message 消息
	 * @param actType 操作类型
	 * @param actContent 操作内容
	 */
	public static void logActivity(String message, String actType, String actContent) {				
		StringBuffer sb = new StringBuffer();
		try {
			sb.append("[")
			.append(InetAddress.getLocalHost().getHostName()).append("] [")
			.append(InetAddress.getLocalHost().getHostAddress()).append("] [\"")
			.append(message).append("\"] [Activity] [")
			.append(actType).append("] [")
			.append(actContent).append("] [] []");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		logger.info(sb.toString());
	}
	
	
	public static void logger(String module, String method, String label, String msg)  {
		StringBuffer sb = new StringBuffer();
		try {
			sb.append("").append(" ")
			.append(LogConstants.APP_NAME).append(" ")
			.append(module).append(" ")
			.append(method).append(" ")
			.append(InetAddress.getLocalHost().getHostAddress()).append(" ")
			.append(label).append(" ")
			.append(msg);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		logger.info(sb.toString());
	}
	
	
	/**
	 * 记录请求操作
	 * @param module	 模块名称
	 * @param operation 操作类型 UPDATE/QUERY/DELETE
	 * @param params	参数
	 * @param msg		消息说明
	 */
	public static void request(String module, String method, String operation, String params, String msg) {
		StringBuffer sb = new StringBuffer();
		try {
			sb.append("").append(" ")
			.append(LogConstants.APP_NAME).append(" ")
			.append(module).append(" ")
			.append(method).append(" ")
			.append(InetAddress.getLocalHost().getHostAddress()).append(" ")
			.append(LogConstants.METHOD_REQUEST).append(" ")
			.append(operation).append(" ")
			.append(params).append(" ")
			.append(msg);
		} catch (UnknownHostException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		
		logger.info(sb.toString());
	}
	
	
	/**
	 * 记录响应操作
	 * @param module	模块名称
	 * @param operation	操作类型UPDATE/QUERY/DELETE
	 * @param reponseStatus	响应状态 SUCCESS/ERROR
	 * @param msg	消息说明
	 */
	public static void response(String module, String method, String operation, String reponseStatus, String msg) {
		StringBuffer sb = new StringBuffer();
		try {
			sb.append("").append(" ")
			.append(LogConstants.APP_NAME).append(" ")
			.append(module).append(" ")
			.append(method).append(" ")
			.append(InetAddress.getLocalHost().getHostAddress()).append(" ")
			.append(LogConstants.METHOD_RESPONSE).append(" ")
			.append(operation).append(" ")
			.append(reponseStatus).append(" ")
			.append(msg);
		} catch (UnknownHostException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		
		logger.info(sb.toString());
	}
	
	
	/**
	 * 记录错误操作
	 * @param module 模块名称
	 * @param method 异常方法 class.method
	 * @param msg 异常消息
	 */
	public static void error(String module, String method, String msg) {
		StringBuffer sb = new StringBuffer();
		try {
			sb.append("").append(" ")
			.append(LogConstants.APP_NAME).append(" ")
			.append(module).append(" ")
			.append(InetAddress.getLocalHost().getHostAddress()).append(" ")
			.append(LogConstants.METHOD_RESPONSE).append(" ")
			.append(method).append(" ")
			.append(msg);
		} catch (UnknownHostException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		
		logger.error(sb.toString());
	}
	
	
	public static void logReportView(String url) {
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
		
		logger.error(sb.toString());
	}
	

}
