package com.favccxx.report.dao;

import java.util.List;

import com.favccxx.report.model.SysScheduleReport;

public interface SysScheduleReportDao {

	/**
	 * 创建定时任务报表
	 * 
	 * @param sysScheduleReport
	 */
	void generateReport(SysScheduleReport sysScheduleReport);

	/**
	 * 删除报表
	 * 
	 * @param sysScheduleReport
	 */
	void deleteReport(SysScheduleReport sysScheduleReport);

	List<SysScheduleReport> listReportByProjectId(long projectId);

	/**
	 * 根据项目Id和模板Id查询报表列表
	 * 
	 * @param projectId
	 *            项目Id
	 * @param templateId
	 *            模板Id
	 * @return
	 */
	List<SysScheduleReport> listReportList(long projectId, long templateId);

	/**
	 * 分页查询某模板生成的报表
	 * @param templateId
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	List<SysScheduleReport> pageListByTemplateId(long templateId, int pageIndex, int pageSize);

	/**
	 * 查询某模板的报表总数
	 * @param templateId
	 * @return
	 */
	int pageCountByTemplateId(long templateId);

}
