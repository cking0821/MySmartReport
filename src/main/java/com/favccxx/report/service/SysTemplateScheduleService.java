package com.favccxx.report.service;

import java.util.List;

import com.favccxx.report.model.SysTemplateSchedule;

public interface SysTemplateScheduleService {
	
	/**
	 * 新建或更新模板定时任务
	 * @author chenxu
	 * @date Jan 11, 2018
	 * @param sysTemplateSchedule
	 */
	void saveTemplateSchedule(SysTemplateSchedule sysTemplateSchedule);
	
	/**
	 * 更新定时任务的下次执行时间
	 * @param sysTemplateSchedule
	 */
	void updateScheduleTime(SysTemplateSchedule sysTemplateSchedule);
	
	/**
	 * 删除模板定时任务
	 * @author chenxu
	 * @date Jan 11, 2018
	 * @param sysTemplateSchedule
	 */
	void deleteTemplateSchedule(SysTemplateSchedule sysTemplateSchedule);
	
	/**
	 * 根据任务Id删除模板定时任务
	 * @author chenxu
	 * @date Jan 11, 2018
	 * @param id
	 */
	void deleteTemplateSchedule(long id);
	
	/**
	 * 根据Id查询模板任务详情
	 * @author chenxu
	 * @date Jan 11, 2018
	 * @param id
	 */
	SysTemplateSchedule findById(long id);

	/**
	 * 根据模板Id查询模板定时任务列表
	 * @author chenxu
	 * @date Jan 11, 2018
	 * @param templateId
	 * @return
	 */
	List<SysTemplateSchedule> listByTemplateId(long templateId);
	
	/**
	 * 查询需要执行的定时任务
	 * @author chenxu
	 * @date Jan 17, 2018
	 * @return
	 */
	List<SysTemplateSchedule> listExecuteSchedules();
}
