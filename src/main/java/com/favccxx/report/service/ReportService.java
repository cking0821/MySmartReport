package com.favccxx.report.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ReportService {
	
	void generateReport(String reportName, HttpServletRequest request, HttpServletResponse response);
	
	void generateReportThumb(String reportName, HttpServletRequest request, HttpServletResponse response);

}
