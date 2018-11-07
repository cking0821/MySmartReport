package com.favccxx.report.controller.project;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.favccxx.report.constants.LogConstants;
import com.favccxx.report.constants.SysConstants;
import com.favccxx.report.context.SessionUser;
import com.favccxx.report.model.SysProject;
import com.favccxx.report.model.SysUserGroup;
import com.favccxx.report.result.RestResult;
import com.favccxx.report.service.SysProjectService;
import com.favccxx.report.service.SysUserGroupService;
import com.favccxx.report.util.LogUtil;

@Controller
public class ProjectController {

	@Autowired
	SysProjectService sysProjectService;
	@Autowired
	SysUserGroupService sysUserGroupService;

	// 初始化项目列表页面
	@RequestMapping("/initProject")
	public ModelAndView initProject(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		
		SessionUser sessionUser = (SessionUser)session.getAttribute(SysConstants.USER_SESSION_KEY);	
		if(sessionUser!=null) {
			LogUtil.logActivity(sessionUser.getUserName() + "查询项目列表", LogConstants.OPERATE_QUERY, LogConstants.MODULE_PROJECT);
			long userId = sessionUser.getUserId();
			List<Long> groupIds = sysUserGroupService.ListGroupIdsByUserId(userId);
			List<SysProject> list = sysProjectService.listByUserIdOrGroupId(userId, groupIds, true);

			mav.addObject("projects", list);
			mav.setViewName("/project/project_list");
		}

		return mav;
	}

	// 给项目分配用户
	@RequestMapping("/initProjectUser")
	public ModelAndView initProjectUser(long projectId) {
		ModelAndView mav = new ModelAndView();
		if (projectId != 0) {
			SysProject sysProject = sysProjectService.findbyId(projectId);
			mav.addObject("sysProject", sysProject);

		}

		mav.setViewName("/project/project_user");
		return mav;
	}

	@RequestMapping("/initEditProject")
	public ModelAndView initEditProject(HttpSession session, long projectId) {
		ModelAndView mav = new ModelAndView();
		SessionUser sessionUser = (SessionUser)session.getAttribute(SysConstants.USER_SESSION_KEY);	
		if(sessionUser!=null) {
			if (projectId != 0) {
				LogUtil.logActivity(sessionUser.getUserName() + "编辑项目", LogConstants.OPERATE_QUERY, LogConstants.MODULE_PROJECT);
				
				SysProject sysProject = sysProjectService.findbyId(projectId);
				if (sysProject != null && !StringUtils.isBlank(sysProject.getProjectUser())) {
					StringBuffer sb = new StringBuffer();
					String[] userGroups = sysProject.getProjectUser().split(",");
					for (int i = 0; i < userGroups.length; i++) {
						SysUserGroup userGroup = sysUserGroupService.findById(Long.valueOf(userGroups[i]));
						sb.append(userGroups[i]).append("/").append(userGroup.getGroupName()).append(";");
					}

					if (sb.toString().length() > 0) {
						sysProject.setProjectUser(sb.toString().substring(0, sb.toString().length() - 1));
					}

				}
				mav.addObject("sysProject", sysProject);
			} else {
				LogUtil.logActivity(sessionUser.getUserName() + "添加项目", LogConstants.OPERATE_QUERY, LogConstants.MODULE_PROJECT);

				SysProject sysProject = new SysProject();
				sysProject.setId(Long.valueOf(0));
				mav.addObject("sysProject", sysProject);
			}

			mav.setViewName("/project/project_edit");
		}

		return mav;
	}

	@RequestMapping("/saveProject")
	@ResponseBody
	public String saveProject(HttpSession session, @ModelAttribute("sysProject") @Valid SysProject sysProject) {
		SessionUser sessionUser = (SessionUser)session.getAttribute(SysConstants.USER_SESSION_KEY);
		if(sessionUser!=null) {
			LogUtil.logActivity(sessionUser.getUserName() + "保存项目", LogConstants.OPERATE_UPDATE, LogConstants.MODULE_PROJECT);
			long userId = sessionUser.getUserId();
			
			if (sysProject.getId() == null || sysProject.getId() == 0) {
				sysProject.setId(null);
				sysProject.setCreateUserId(userId);
				sysProject.setCreateTime(new Date());
				sysProject.setUpdateUserId(userId);
				sysProject.setUpdateTime(new Date());
				sysProjectService.saveBizProject(sysProject);
				return SysConstants.SUCCESS_MSG;
			}

			sysProject.setUpdateUserId(userId);
			sysProject.setUpdateTime(new Date());
			sysProjectService.saveBizProject(sysProject);
		}				

		return SysConstants.SUCCESS_MSG;
	}

	@RequestMapping(value = "/delSysProject", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public String delSysProject(HttpSession session, @RequestBody String projectId) {
		SessionUser sessionUser = (SessionUser)session.getAttribute(SysConstants.USER_SESSION_KEY);
		if(sessionUser!=null) {
			LogUtil.logActivity(sessionUser.getUserName() + "删除项目", LogConstants.OPERATE_DELETE, LogConstants.MODULE_PROJECT);
			sysProjectService.deleteSysProject(Long.parseLong(projectId));
		}
		return JSON.toJSONString(RestResult.success(SysConstants.SUCCESS_MSG));
	}
}
