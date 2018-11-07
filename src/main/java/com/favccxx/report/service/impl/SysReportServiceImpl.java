package com.favccxx.report.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.favccxx.report.dao.SysReportDao;
import com.favccxx.report.model.SysReport;
import com.favccxx.report.service.SysReportService;

@Service
public class SysReportServiceImpl implements SysReportService {
	
	@Autowired
	SysReportDao sysReportDao;

	
	public void addSysReport(SysReport sysReport) {
		sysReportDao.addSysReport(sysReport);
	}

	
	public void updateSysReport(SysReport sysReport) {
		sysReportDao.updateSysReport(sysReport);
	}

	
	public List<SysReport> findByDirectoryId(long directoryId) {
		return sysReportDao.findByDirectoryId(directoryId);
	}

	
	public SysReport findById(long id) {
		return sysReportDao.findById(id);
	}

}
