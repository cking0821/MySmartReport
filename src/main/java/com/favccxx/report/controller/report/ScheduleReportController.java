package com.favccxx.report.controller.report;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.favccxx.report.constants.SysConstants;
import com.favccxx.report.context.SessionUser;
import com.favccxx.report.model.SysProject;
import com.favccxx.report.model.SysScheduleReport;
import com.favccxx.report.service.SysProjectService;
import com.favccxx.report.service.SysScheduleReportService;
import com.favccxx.report.service.SysUserGroupService;

@Controller
public class ScheduleReportController {
	
	@Autowired
	SysProjectService sysProjectService;
	@Autowired
	SysScheduleReportService scheduleReportService;
	@Autowired
	SysUserGroupService sysUserGroupService;
	
	
	@RequestMapping("/initReport")
	public ModelAndView initReport(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		SessionUser sessionUser = (SessionUser)session.getAttribute(SysConstants.USER_SESSION_KEY);
		
		long userId = sessionUser.getUserId();
		List<Long> groupIds = sysUserGroupService.ListGroupIdsByUserId(userId);
		List<SysProject> list = sysProjectService.listByUserIdOrGroupId(userId, groupIds, false);
		
		mav.addObject("projects", list);
		mav.setViewName("/schedulereport/schedule_report_list");
		return mav;
	}
	
	
	@RequestMapping("/getScheduleReportList")
	public ModelAndView getScheduleReportList(String projectId, String templateId) {
		ModelAndView mav = new ModelAndView();	
		
		List<SysScheduleReport> reportList = scheduleReportService.listReportList(Long.valueOf(projectId), Long.valueOf(templateId));
		
		
		
		mav.addObject("reportList", reportList);
				
		mav.setViewName("/schedulereport/report_list");
		return mav;
	}
	
	
	

}
