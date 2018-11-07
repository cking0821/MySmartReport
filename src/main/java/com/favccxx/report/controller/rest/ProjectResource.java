package com.favccxx.report.controller.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.favccxx.report.model.SysProject;
import com.favccxx.report.model.SysUser;
import com.favccxx.report.result.RestResult;
import com.favccxx.report.service.SysProjectService;
import com.favccxx.report.service.SysUserGroupService;
import com.favccxx.report.service.SysUserService;

@RequestMapping("/api/project")
@RestController
public class ProjectResource {
	
	@Autowired
	SysProjectService sysProjectService;
	@Autowired
	SysUserService sysUserService;
	@Autowired
	SysUserGroupService sysUserGroupService;
	
	/**
	 * 查询某用户可以访问的项目列表
	 * @param userName
	 * @return
	 */
	@RequestMapping(value = "/list/{userName}", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public String listByUsername(
			@PathVariable(value = "userName") String userName) {
		
		SysUser sysUser = sysUserService.findByUsername(userName);
		if(sysUser==null) {
			return JSON.toJSONString(RestResult.error(404, "无效的用户"));
		}
		
		List<Long> groupIds = sysUserGroupService.ListGroupIdsByUserId(sysUser.getId());
		List<SysProject> list = sysProjectService.listByUserIdOrGroupId(sysUser.getId(), groupIds, true);
		List<SysProject> dataList = new ArrayList<SysProject>();
		for(SysProject pro : list) {
			pro.setCreateUserId(0);
			pro.setUpdateUserId(0);
			pro.setProjectUser(null);
			dataList.add(pro);
		}
		
		return JSON.toJSONStringWithDateFormat(RestResult.success(dataList), "yyyy-MM-dd HH:mm:ss");				
	}

}
