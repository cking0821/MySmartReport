package com.favccxx.report.constants;

public interface SysConstants {

	public static final int SUCCESS_STATUS_CODE = 0;
	
	public static final int ERROR_STATUS_CODE = 1;
	
	
	public static final String FILE_TYPE_DATASOURCE = "DATASOURCE";
	
	public static final String FILE_TYPE_DATASET = "DATASET";
	
	public static final String FILE_TYPE_FOLDER = "FOLDER";
	
	public static final String FILE_TYPE_REPORT = "REPORT";
	
	public static final String USER_SALT_KEY = "report.salt.key";
	
	public static final String USER_SESSION_KEY = "report.session.key";
	
	public static final String SESSION_USER_ID = "$SESSION_USER_ID";
//	public static final String SESSION_USER_NAME = "$SESSION_USER_NAME";
	
	/********************用户状态常量**********************/
	public static final String USER_STATUS_NOMAL = "NORMAL";
	
	public static final String USER_STATUS_DELETE = "DELETE";
	
	public static final String USER_STATUS_LOCK = "LOCK";
	
	/*******************JSON REST请求响应值********************************/
	
	public static final String SUCCESS_MSG = "SUCCESS";
	
	public static final String ERROR_MSG = "ERROR";
	
	public static final String USER_RESOURCE_KEY = "userResources";
	
	public static final String USER_RESOURCE_CACHE_KEY = "userResourceCache_";
	
	
	public static final int STATUS_CODE_LACK_PARAMS = 4001;	//缺少必要的参数
	public static final int STATUS_CODE_INVALID_PARAMS = 4002;	//参数不正确
	public static final String RESPONSE_MESSAGE_LACK_PARAMS = "操作失败，缺少必要的参数";
	public static final String RESPONSE_MESSAGE_INVALID_PARAMS = "操作失败，您填写的参数不正确";
	
	public static final String RESPONSE_MESSAGE_SUCCESS = "操作成功";
	
}
