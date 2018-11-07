package com.favccxx.report.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.favccxx.report.model.SysProject;
import com.favccxx.report.model.SysScheduleReport;
import com.favccxx.report.model.SysTemplate;
import com.favccxx.report.model.SysUser;
import com.favccxx.report.result.PageInfo;
import com.favccxx.report.result.RestResult;
import com.favccxx.report.service.SysProjectService;
import com.favccxx.report.service.SysScheduleReportService;
import com.favccxx.report.service.SysTemplateService;
import com.favccxx.report.service.SysUserGroupService;
import com.favccxx.report.service.SysUserService;

@RequestMapping("/api/report")
@RestController
public class ReportResource {

	@Autowired
	SysProjectService sysProjectService;
	@Autowired
	SysUserService sysUserService;
	@Autowired
	SysUserGroupService sysUserGroupService;
	@Autowired
	SysTemplateService sysTemplateService;
	@Autowired
	SysScheduleReportService scheduleReportService;

	@RequestMapping(value="/list/{templateId}", produces = "application/json; charset=UTF-8")
	public String templateDetail(@PathVariable(value = "templateId") long templateId,
			@RequestParam(value = "userName") String userName,
			@RequestParam(value = "pageIndex", defaultValue = "1") int pageIndex,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
		RestResult<PageInfo<SysScheduleReport>> rs = new RestResult<PageInfo<SysScheduleReport>>();

		if (templateId == 0) {
			rs = RestResult.lackParams();
			return JSON.toJSONString(rs);
		}

		SysTemplate template = sysTemplateService.findbyId(templateId);
		if (template == null) {
			rs = RestResult.invalidParams();
			return JSON.toJSONString(rs);
		}

		// 校验用户名是否合法
		SysUser sysUser = sysUserService.findByUsername(userName);
		if (sysUser == null) {
			rs = RestResult.invalidParams();
			return JSON.toJSONString(rs);
		}

		List<Long> groupIds = sysUserGroupService.ListGroupIdsByUserId(sysUser.getId());
		List<SysProject> list = sysProjectService.listByUserIdOrGroupId(sysUser.getId(), groupIds, true);
		if (list == null || list.size() == 0) {			
			return JSON.toJSONString(RestResult.error(41000, "该用户没有权限访问该报表"));
		}
		boolean flag = false;
		for (SysProject project : list) {
			if (project.getId() == Long.valueOf(template.getProjectId())) {
				flag = true;
				continue;
			}
		}
		if (!flag) {			
			return JSON.toJSONString(RestResult.error(41000, "该用户没有权限访问该报表"));
		}				
		
		PageInfo<SysScheduleReport> pageInfo = scheduleReportService.pageByTemplateId(Long.valueOf(templateId), pageIndex, pageSize);
		rs.setData(pageInfo);
		return JSON.toJSONStringWithDateFormat(rs, "yyyy-MM-dd HH:mm:ss");
	}
	

	

	

	

}
