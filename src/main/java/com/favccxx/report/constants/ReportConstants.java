package com.favccxx.report.constants;

public interface ReportConstants {
	
	public final static String JSP_ROOT = "/assets/birt";
	
	//JSON文件存储路径
//	public final static String JSON_FILE_PATH = "D://jsondata";
	
	//模板数据的编辑版本状态：DRAFT/NORMAL
	public static final String TEMPLATE_STATUS_DRAFT = "DRAFT";
	
	public static final String TEMPLATE_STATUS_NORMAL = "NORMAL";
	
	//报表模板设计文件路径
	public static final String TEMPLATE_FILE_PATH_NAME = "templateFilePath";
	
	//报表模板数据源
	public static final String REPORT_DATA_SOURCE_JDBC = "JDBC";
	public static final String REPORT_DATA_SOURCE_JAVASCRIPT = "SCRIPT";
	
	//定时任务触发频率
	public static final String SCHEDULE_DAILY = "DAILY";
	public static final String SCHEDULE_WEEK = "WEEK";
	public static final String SCHEDULE_MONTH = "MONTH";
	public static final String SCHEDULE_YEAR = "YEAR";
	
	/**
	 * 定时任务是否启动
	 */
	public static final String SCHEDULE_STATUS_OPEN = "OPEN";
	public static final String SCHEDULE_STATUS_CLOSED = "CLOSED";
	
	
	public static final String REPORT_EDIT_GROUP_CODE = "REPORT_EDIT_GROUP";
	public static final String REPORT_EDIT_GROUP_FLAG = "REPORT_EDIT_FLAG";
}
