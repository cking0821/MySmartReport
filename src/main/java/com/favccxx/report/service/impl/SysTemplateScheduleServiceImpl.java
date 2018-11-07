package com.favccxx.report.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.favccxx.report.constants.ReportConstants;
import com.favccxx.report.dao.SysTemplateScheduleDao;
import com.favccxx.report.model.SysTemplateSchedule;
import com.favccxx.report.service.SysTemplateScheduleService;
import com.favccxx.report.util.DateUtil;
import com.favccxx.report.util.ScheduleDateUtil;

@Service
public class SysTemplateScheduleServiceImpl implements SysTemplateScheduleService {

	@Autowired
	SysTemplateScheduleDao sysTemplateScheduleDao;

	DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Transactional
	@Override
	public void saveTemplateSchedule(SysTemplateSchedule sysTemplateSchedule) {		
		if (ReportConstants.SCHEDULE_STATUS_OPEN.equals(sysTemplateSchedule.getScheduleStatus())) {
			
			int scheduleHour = 8;
			int scheduleMinute = 0;
			String scheduleDay = "0";
			String scheduleTime = sysTemplateSchedule.getSchduleTime();
			
			if(scheduleTime!=null) {
				String hourStr = scheduleTime.substring(0, 2);
				String minuteStr = scheduleTime.substring(3);
				
				scheduleHour = Integer.parseInt(hourStr);
				scheduleMinute = Integer.parseInt(minuteStr);
			}
			if(sysTemplateSchedule.getScheduleFrequencyDetail()!=null) {
				scheduleDay = sysTemplateSchedule.getScheduleFrequencyDetail();
			}
			
			
			
			// 计算定时任务的下次执行时间
			Date scheduleDate = ScheduleDateUtil.getScheduleDate(sysTemplateSchedule.getScheduleFrequency(), Integer.parseInt(scheduleDay), scheduleHour, scheduleMinute);

			try {
				if(!StringUtils.isBlank(sysTemplateSchedule.getEndTime()) && scheduleDate.after(DateUtil.toDate(sysTemplateSchedule.getEndTime()))) {
					sysTemplateSchedule.setNextScheduleTime(null);
				}else {
					sysTemplateSchedule.setNextScheduleTime(scheduleDate);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else {
			sysTemplateSchedule.setNextScheduleTime(null);
		}
		sysTemplateScheduleDao.saveTemplateSchedule(sysTemplateSchedule);
	}
	
	/**
	 * 定时任务下次执行时间
	 */
	@Transactional
	@Override
	public void updateScheduleTime(SysTemplateSchedule sysTemplateSchedule) {
		int scheduleHour = 8;
		int scheduleMinute = 0;
		String scheduleDay = "0";
		String scheduleTime = sysTemplateSchedule.getSchduleTime();
		
		if(scheduleTime!=null) {
			String hourStr = scheduleTime.substring(0, 2);
			String minuteStr = scheduleTime.substring(3);
			
			scheduleHour = Integer.parseInt(hourStr);
			scheduleMinute = Integer.parseInt(minuteStr);
		}
		if(sysTemplateSchedule.getScheduleFrequencyDetail()!=null) {
			scheduleDay = sysTemplateSchedule.getScheduleFrequencyDetail();
		}
		
		Date scheduleDate = ScheduleDateUtil.getScheduleDate(sysTemplateSchedule.getScheduleFrequency(), Integer.parseInt(scheduleDay), scheduleHour, scheduleMinute);
		sysTemplateSchedule.setNextScheduleTime(scheduleDate);
		sysTemplateScheduleDao.saveTemplateSchedule(sysTemplateSchedule);
	}

	@Transactional
	@Override
	public void deleteTemplateSchedule(SysTemplateSchedule sysTemplateSchedule) {
		sysTemplateScheduleDao.deleteTemplateSchedule(sysTemplateSchedule);
	}

	@Transactional
	@Override
	public void deleteTemplateSchedule(long id) {
		sysTemplateScheduleDao.deleteTemplateSchedule(id);
	}

	@Override
	public SysTemplateSchedule findById(long id) {
		return sysTemplateScheduleDao.findById(id);
	}

	@Override
	public List<SysTemplateSchedule> listByTemplateId(long templateId) {
		return sysTemplateScheduleDao.listByTemplateId(templateId);
	}

	@Override
	public List<SysTemplateSchedule> listExecuteSchedules() {
		return sysTemplateScheduleDao.listExecuteSchedules();
	}

	

}
