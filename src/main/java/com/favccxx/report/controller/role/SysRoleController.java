package com.favccxx.report.controller.role;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.favccxx.report.constants.LogConstants;
import com.favccxx.report.constants.SysConstants;
import com.favccxx.report.context.SessionUser;
import com.favccxx.report.model.SysRole;
import com.favccxx.report.service.SysResourceService;
import com.favccxx.report.service.SysRoleService;
import com.favccxx.report.util.LogUtil;

@Controller
public class SysRoleController {

	@Autowired
	SysRoleService sysRoleService;
	@Autowired
	SysResourceService sysResourceService;

	@RequestMapping("/initRoleList")
	public ModelAndView initRoleList(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		
		SessionUser sessionUser = (SessionUser)session.getAttribute(SysConstants.USER_SESSION_KEY);
		LogUtil.logActivity(sessionUser.getUserName() + "查询角色信息", LogConstants.OPERATE_QUERY, LogConstants.MODULE_ROLE);
		
		
		List<SysRole> list = sysRoleService.listRoles();
		// List<SysResource> resourceList = sysResourceService.listResources();
		mav.addObject("roles", list);
		// mav.addObject("resources", resourceList);
		mav.setViewName("/role/role_list");

		return mav;
	}

}
