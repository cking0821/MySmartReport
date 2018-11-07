package com.favccxx.report.service;

import java.util.List;

import com.favccxx.report.model.SysReport;

public interface SysReportService {

	void addSysReport(SysReport sysReport);

	void updateSysReport(SysReport sysReport);

	List<SysReport> findByDirectoryId(long directoryId);
	
	SysReport findById(long id);

}
