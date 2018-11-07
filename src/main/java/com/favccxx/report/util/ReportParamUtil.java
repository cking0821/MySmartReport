package com.favccxx.report.util;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public class ReportParamUtil {
	
	public static String releaseParameter(String paramter, String userName, String userId, String orgId) {
		if(StringUtils.isBlank(paramter)) {
			return "";
		}
		
		StringBuffer sb = new StringBuffer();
		String[] paramArr = paramter.split("&");
		for(int i=0; i<paramArr.length; i++) {
			if(!StringUtils.isBlank(paramArr[i])) {
				String[] keyValues = paramArr[i].split("=");
				//报表参数转义
				if(keyValues.length==2 && !StringUtils.isBlank(keyValues[1])) {
					String value = keyValues[1];
					sb.append("&").append(keyValues[0]).append("=");
					if("$USERNAME".equals(value)) {
						sb.append(userName);
					}else if("$USERID".equals(value)) {
						sb.append(userId);
					}else if("$ORGID".equals(value)) {
						sb.append(orgId);
					}else if("$DATE".equals(value)) {
						sb.append(DateUtil.date2String(new Date(), "yyyy-MM-dd"));
					}else if("$DATETIME".equals(value)) {
						sb.append(DateUtil.date2String(new Date(), "yyyy-MM-dd HH:mm:ss"));
					}else {
						sb.append(value);
					}
				}
			}
		}
		return sb.toString();
	}

}
