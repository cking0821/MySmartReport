package com.favccxx.report.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.favccxx.report.model.SysProject;
import com.favccxx.report.model.SysTemplate;
import com.favccxx.report.model.SysUser;
import com.favccxx.report.service.SysProjectService;
import com.favccxx.report.service.SysTemplateService;
import com.favccxx.report.service.SysUserGroupService;
import com.favccxx.report.service.SysUserService;
import com.favccxx.report.util.ReportParamUtil;

@Controller
@RequestMapping("/iframe")
public class IframeViewController {

	@Autowired
	SysProjectService sysProjectService;
	@Autowired
	SysUserService sysUserService;
	@Autowired
	SysUserGroupService sysUserGroupService;
	@Autowired
	SysTemplateService sysTemplateService;

	@RequestMapping("/view")
	public ModelAndView initApiView(String templateId, String userName) {
		ModelAndView mav = new ModelAndView();
		
		String ERROR_VIEW = "";

		SysTemplate sysTemplate = sysTemplateService.findbyId(Long.valueOf(templateId));
		if (sysTemplate == null) {
			mav.setViewName(ERROR_VIEW);
			mav.addObject("error", "模板Id不正确!");
			return mav;
		}

		SysUser sysUser = sysUserService.findByUsername(userName);
		if (sysUser == null) {
			mav.setViewName(ERROR_VIEW);
			mav.addObject("error", "用户没有权限!");
			return mav;
		}

		// 用户没有权限查看任何项目，也就无权查看报表
		List<Long> groupIds = sysUserGroupService.ListGroupIdsByUserId(sysUser.getId());
		List<SysProject> list = sysProjectService.listByUserIdOrGroupId(sysUser.getId(), groupIds, true);
		if (list == null || list.size() == 0) {
			mav.setViewName(ERROR_VIEW);
			mav.addObject("error", "用户没有权限!");
			return mav;
		}

		boolean flag = false;
		for (SysProject project : list) {
			if (project.getId() == sysTemplate.getProjectId()) {
				flag = true;
				continue;
			}
		}
		if (!flag) {
			mav.setViewName(ERROR_VIEW);
			mav.addObject("error", "用户没有权限!");
			return mav;
		}

		String templateName = sysTemplate.getReportName();
		String params = ReportParamUtil.releaseParameter(sysTemplate.getParameter(), userName, String.valueOf(sysUser.getId()), sysUser.getOrgId());
		mav.setViewName("forward:/frameset?__report=" + templateName + params);

		return mav;
	}

}
