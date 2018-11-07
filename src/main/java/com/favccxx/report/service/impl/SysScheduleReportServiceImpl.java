package com.favccxx.report.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.favccxx.report.dao.SysScheduleReportDao;
import com.favccxx.report.model.SysScheduleReport;
import com.favccxx.report.result.PageInfo;
import com.favccxx.report.service.SysScheduleReportService;

@Service
public class SysScheduleReportServiceImpl implements SysScheduleReportService {
	
	@Autowired
	SysScheduleReportDao sysScheduleReportDao;

	@Transactional
	@Override
	public void generateReport(SysScheduleReport sysScheduleReport) {
		sysScheduleReportDao.generateReport(sysScheduleReport);
	}

	@Transactional
	@Override
	public void deleteReport(SysScheduleReport sysScheduleReport) {
		sysScheduleReportDao.deleteReport(sysScheduleReport);
	}

	@Transactional
	@Override
	public List<SysScheduleReport> listReportByProjectId(long projectId) {
		return sysScheduleReportDao.listReportByProjectId(projectId);
	}

	@Override
	public List<SysScheduleReport> listReportList(long projectId, long templateId) {
		return sysScheduleReportDao.listReportList(projectId, templateId);
	}

	@Override
	public PageInfo<SysScheduleReport> pageByTemplateId(long templateId, int pageIndex, int pageSize) {
		List<SysScheduleReport> list = sysScheduleReportDao.pageListByTemplateId(templateId, pageIndex, pageSize);
		int totalCount = Integer.valueOf(String.valueOf(sysScheduleReportDao.pageCountByTemplateId(templateId)));
		PageInfo<SysScheduleReport> pageInfo = new PageInfo<SysScheduleReport>(pageIndex, pageSize, totalCount, list);
		return pageInfo;
	}

}
