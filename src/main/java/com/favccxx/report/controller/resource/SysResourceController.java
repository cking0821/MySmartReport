package com.favccxx.report.controller.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.favccxx.report.constants.LogConstants;
import com.favccxx.report.constants.SysConstants;
import com.favccxx.report.context.SessionUser;
import com.favccxx.report.model.SysResource;
import com.favccxx.report.model.SysRoleResource;
import com.favccxx.report.result.RestResult;
import com.favccxx.report.service.SysResourceService;
import com.favccxx.report.util.LogUtil;

@Controller
public class SysResourceController {

	@Autowired
	SysResourceService sysResourceService;

	@RequestMapping("/getRoleResources")
	public ModelAndView getRoleResources(HttpSession session, long roleId) {
		ModelAndView mav = new ModelAndView();
		
		SessionUser sessionUser = (SessionUser)session.getAttribute(SysConstants.USER_SESSION_KEY);
		LogUtil.logActivity(sessionUser.getUserName() + "根据角色查询权限信息", LogConstants.OPERATE_QUERY, LogConstants.MODULE_ROLE);
		
		
		List<SysResource> resourceList = sysResourceService.listResources();
		List<SysRoleResource> roleResources = sysResourceService.listRoleResources(roleId);

		mav.addObject("resources", resourceList);
		mav.addObject("roleResources", roleResources);
		mav.addObject("roleId", roleId);

		mav.setViewName("/role/role_resource_list");
		return mav;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateRoleResource", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public String updateRoleResource(HttpSession session, @RequestBody String params) {
		SessionUser sessionUser = (SessionUser)session.getAttribute(SysConstants.USER_SESSION_KEY);
		LogUtil.logActivity(sessionUser.getUserName() + "保存角色权限信息", LogConstants.OPERATE_UPDATE, LogConstants.MODULE_ROLE);
		
		
		
		Map<String, Object> dataMap = (Map<String, Object>) JSON.parse(params);
		List<Long> resourceIdList = new ArrayList<Long>();
		
		String roleId = (String) dataMap.get("roleId");
		String menuData = (String) dataMap.get("menuData");
		String operationData = (String) dataMap.get("operationData");

		
		
		if(!StringUtils.isBlank(operationData)) {
			String[] operationIds = operationData.split(",");
			
			for (int i = 0; i < operationIds.length; i++) {
				if(!StringUtils.isBlank(operationIds[i])) {
					resourceIdList.add(Long.valueOf(operationIds[i]));
				}
			}
		}
		if(!StringUtils.isBlank(menuData)) {
			String[] menuIds = menuData.split(",");
			for (int i = 0; i < menuIds.length; i++) {
				if(!StringUtils.isBlank(menuIds[i])) {
					resourceIdList.add(Long.valueOf(menuIds[i]));
				}
			}
		}
		

		Long[] resourceIds = (Long[]) resourceIdList.toArray(new Long[resourceIdList.size()]);

		sysResourceService.deleteRoleResource(Long.parseLong(roleId));
		sysResourceService.addRoleResource(Long.parseLong(roleId), 0, resourceIds);


		return JSON.toJSONString(RestResult.success(SysConstants.SUCCESS_MSG));
	}

}
