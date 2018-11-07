package com.favccxx.report.service;

import java.util.List;

import com.favccxx.report.model.SysScheduleReport;
import com.favccxx.report.result.PageInfo;

public interface SysScheduleReportService {
	
	/**
	 * 创建定时任务报表
	 * @param sysScheduleReport
	 */
	void generateReport(SysScheduleReport sysScheduleReport);
	
	/**
	 * 删除报表
	 * @param sysScheduleReport
	 */
	void deleteReport(SysScheduleReport sysScheduleReport);
	
	
	List<SysScheduleReport> listReportByProjectId(long projectId);
	
	/**
	 * 根据项目Id和模板Id查询报表列表
	 * @param projectId 项目Id
	 * @param templateId 模板Id
	 * @return
	 */
	List<SysScheduleReport> listReportList(long projectId, long templateId);
	
	/**
	 * 根据模板Id分页查询定时任务生成的报表
	 * @param templateId
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	PageInfo<SysScheduleReport> pageByTemplateId(long templateId, int pageIndex, int pageSize);


}
