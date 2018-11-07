package com.favccxx.report.dao;

import java.util.List;

import com.favccxx.report.model.SysReport;

public interface SysReportDao {
	
	void addSysReport(SysReport sysReport);
	
	void updateSysReport(SysReport sysReport);
	
	List<SysReport> findByDirectoryId(long directoryId);
	
	SysReport findById(long id);

}
