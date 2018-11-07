package com.favccxx.report.util;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.favccxx.report.constants.ReportConstants;

public class ScheduleDateUtil {
	
	public static Date getScheduleDate(String scheduleFrequency, int scheduleDate, int scheduleHour, int scheduleMinute) {
		if(StringUtils.isBlank(scheduleFrequency)) {
			return null;
		}
		
		Calendar nowCa = Calendar.getInstance();	
		nowCa.setTime(new Date());
		
		//任务执行时间
		Calendar ca = Calendar.getInstance();		
		ca.setTime(new Date());
		ca.set(Calendar.HOUR_OF_DAY, scheduleHour);
		ca.set(Calendar.MINUTE, scheduleMinute);
		
		if (ReportConstants.SCHEDULE_DAILY.equals(scheduleFrequency)) {
			//任务比当前时间早，则往后加1天
			if(ca.before(nowCa)) {
				ca.set(Calendar.DAY_OF_MONTH, ca.get(Calendar.DAY_OF_MONTH)+1);
			}
			return ca.getTime();
		}else if(ReportConstants.SCHEDULE_WEEK.equals(scheduleFrequency)) {
			if(scheduleDate==7) {
				scheduleDate = 1;
			}else {
				scheduleDate = scheduleDate + 1;
			}
			
			ca.set(Calendar.DAY_OF_WEEK, scheduleDate);
			//本周已过，则设置为下周执行
			if(ca.before(nowCa)) {
				ca.set(Calendar.DAY_OF_MONTH, ca.get(Calendar.DAY_OF_MONTH)+7);
			}
			return ca.getTime();
		}else if(ReportConstants.SCHEDULE_MONTH.equals(scheduleFrequency)) {
			ca.set(Calendar.DAY_OF_MONTH, scheduleDate);
			//本月已过，设置为下月执行
			if(ca.before(nowCa)) {
				ca.set(Calendar.MONTH, ca.get(Calendar.MONTH)+1);
			}
			return ca.getTime();
		}
		
		
		
		return null;
	}

}
