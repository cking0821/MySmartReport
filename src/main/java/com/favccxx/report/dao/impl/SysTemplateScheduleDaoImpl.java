package com.favccxx.report.dao.impl;


import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.favccxx.report.dao.SysTemplateScheduleDao;
import com.favccxx.report.model.SysTemplateSchedule;

@Repository
public class SysTemplateScheduleDaoImpl implements SysTemplateScheduleDao {
	
	@Autowired
	protected HibernateTemplate hibernateTemplate;

	@Override
	public void saveTemplateSchedule(SysTemplateSchedule sysTemplateSchedule) {
		hibernateTemplate.saveOrUpdate(sysTemplateSchedule);
	}

	@Override
	public void deleteTemplateSchedule(SysTemplateSchedule sysTemplateSchedule) {
		hibernateTemplate.delete(sysTemplateSchedule);
	}

	@Override
	public void deleteTemplateSchedule(long id) {
		SysTemplateSchedule sysTemplateSchedule = findById(id);
		if(sysTemplateSchedule!=null) {
			hibernateTemplate.delete(sysTemplateSchedule);
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysTemplateSchedule> listByTemplateId(long templateId) {
		String hql = "from SysTemplateSchedule where templateId = ? order by updateTime desc";
		return (List<SysTemplateSchedule>) hibernateTemplate.find(hql, templateId);
	}

	@Override
	public SysTemplateSchedule findById(long id) {
		return hibernateTemplate.get(SysTemplateSchedule.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysTemplateSchedule> listExecuteSchedules() {
		try {		
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new java.util.Date());
			
			java.util.Date today = new java.util.Date();
			Date dd = new Date(today.getTime());
			
			String hql = "from SysTemplateSchedule where scheduleStatus = 'OPEN' and (nextScheduleTime is null or nextScheduleTime<?)";
			List<SysTemplateSchedule> list = (List<SysTemplateSchedule>) hibernateTemplate.find(hql,dd);
			
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
