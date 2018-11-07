package com.favccxx.report.controller.rest;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.favccxx.report.model.SysProject;
import com.favccxx.report.model.SysTemplate;
import com.favccxx.report.model.SysTemplateVersion;
import com.favccxx.report.model.SysUser;
import com.favccxx.report.result.PageInfo;
import com.favccxx.report.result.RestResult;
import com.favccxx.report.service.SysProjectService;
import com.favccxx.report.service.SysTemplateService;
import com.favccxx.report.service.SysTemplateVersionService;
import com.favccxx.report.service.SysUserGroupService;
import com.favccxx.report.service.SysUserService;

@RequestMapping("/api/template")
@RestController
public class TemplateResource {

	@Autowired
	SysProjectService sysProjectService;
	@Autowired
	SysUserService sysUserService;
	@Autowired
	SysUserGroupService sysUserGroupService;
	@Autowired
	SysTemplateService sysTemplateService;
	@Autowired
	SysTemplateVersionService sysTemplateVersionService;
	
	
	@RequestMapping(value="/project/{id}", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public String listByUsername(@PathVariable(value = "id") String id,
			@RequestParam(value = "userName") String userName,
			@RequestParam(value = "sortField", defaultValue = "id") String sortField,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "pageIndex", defaultValue = "1") int pageIndex,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

		RestResult<PageInfo<SysTemplate>> rs = new RestResult<PageInfo<SysTemplate>>();

		if (StringUtils.isBlank(id) || StringUtils.isBlank(userName)) {
			rs = RestResult.lackParams();
			return JSON.toJSONString(rs);
		}

		// 校验用户名是否合法
		SysUser sysUser = sysUserService.findByUsername(userName);
		if (sysUser == null) {
			rs = RestResult.invalidParams();
			return JSON.toJSONString(rs);
		}

		// 查询用户是否有权限访问该项目
		List<Long> groupIds = sysUserGroupService.ListGroupIdsByUserId(sysUser.getId());
		List<SysProject> list = sysProjectService.listByUserIdOrGroupId(sysUser.getId(), groupIds, true);			
		if (list == null || list.size() == 0) {			
			return JSON.toJSONString(RestResult.error(41000, "该用户没有权限访问该项目"));
		}
		boolean flag = false;
		for (SysProject project : list) {
			if (project.getId() == Long.valueOf(id)) {
				flag = true;
				continue;
			}
		}
		if (!flag) {			
			return JSON.toJSONString(RestResult.error(41000, "该用户没有权限访问该项目"));
		}

		boolean ascending = true;
		if ("DESC".equals(direction.toUpperCase())) {
			ascending = false;
		}

		PageInfo<SysTemplate> pageInfo = sysTemplateService.pageByProjectId(Long.valueOf(id), pageIndex, pageSize,
				sortField, ascending);
		rs.setData(pageInfo);
		return JSON.toJSONStringWithDateFormat(rs, "yyyy-MM-dd HH:mm:ss");
	}
	
	
	/**
	 * 查看某报表的版本记录
	 * 
	 * @param id
	 * @param userName
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value="/version/{id}", produces = "application/json; charset=UTF-8")
	public String pageVersionsByTemplateId(@PathVariable(value = "id") long id,
			@RequestParam(value = "userName") String userName,
			@RequestParam(value = "pageIndex", defaultValue = "1") int pageIndex,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

		RestResult<PageInfo<SysTemplateVersion>> pageTv = new RestResult<PageInfo<SysTemplateVersion>>();

		if (id == 0 || StringUtils.isBlank(userName)) {
			pageTv = RestResult.lackParams();
			return JSON.toJSONString(pageTv);
		}

		// 校验用户名是否合法
		SysUser sysUser = sysUserService.findByUsername(userName);
		if (sysUser == null) {
			pageTv = RestResult.invalidParams();
			return JSON.toJSONString(pageTv);
		}

		SysTemplate sysTemplate = sysTemplateService.findbyId(id);
		if (sysTemplate == null) {
			pageTv = RestResult.invalidParams();
			return JSON.toJSONString(pageTv);
		}

		// 用户没有权限查看任何项目，也就无权查看报表
		List<Long> groupIds = sysUserGroupService.ListGroupIdsByUserId(sysUser.getId());
		List<SysProject> list = sysProjectService.listByUserIdOrGroupId(sysUser.getId(), groupIds, true);
		if (list == null || list.size() == 0) {			
			return JSON.toJSONString(RestResult.error(41003, "该用户没有权限访问该报表"));
		}

		boolean flag = false;
		for (SysProject project : list) {
			if (project.getId() == sysTemplate.getProjectId()) {
				flag = true;
				continue;
			}
		}
		if (!flag) {			
			return JSON.toJSONString(RestResult.error(41003, "该用户没有权限访问该报表"));
		}

		PageInfo<SysTemplateVersion> pageInfo = sysTemplateVersionService.pageByTemplateId(id, pageIndex, pageSize);
		pageTv.setData(pageInfo);

		return JSON.toJSONStringWithDateFormat(pageTv, "yyyy-MM-dd HH:mm:ss");
	}
	
	
	/**
	 * 根据用户分页查看可访问的模板列表
	 * @param userName
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value="/template/{userName}", produces = "application/json; charset=UTF-8")
	public String pageTemplatesByUserName(@PathVariable(value = "userName") String userName,
			@RequestParam(value = "pageIndex", defaultValue = "1") int pageIndex,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
		RestResult<PageInfo<SysTemplate>> rs = new RestResult<PageInfo<SysTemplate>>();

		if (StringUtils.isBlank(userName)) {
			rs = RestResult.lackParams();
			return JSON.toJSONString(rs);
		}

		// 校验用户名是否合法
		SysUser sysUser = sysUserService.findByUsername(userName);
		if (sysUser == null) {
			rs = RestResult.invalidParams();
			return JSON.toJSONString(rs);
		}

		// 超级管理员可以查看所有的报表模板
		List<Long> groupIds = sysUserGroupService.ListGroupIdsByUserId(sysUser.getId());
		List<SysProject> list = sysProjectService.listByUserIdOrGroupId(sysUser.getId(), groupIds, true);
		if (list == null || list.size() == 0) {				
			return JSON.toJSONString(RestResult.error(41000, "抱歉，该用户没有报表访问权限。"));
		}

		List<Long> projectIds = new ArrayList<Long>();
		for (SysProject project : list) {
			projectIds.add(project.getId());
		}

		PageInfo<SysTemplate> ps = sysTemplateService.pageByProjects(projectIds, pageIndex, pageSize, null, true);
		rs.setData(ps);
		return JSON.toJSONStringWithDateFormat(rs, "yyyy-MM-dd HH:mm:ss");
		
	}
	
	/**
	 * 查看报表模板详情
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/detail/{templateId}", produces = "application/json; charset=UTF-8")
	public String templateDetail(@PathVariable(value = "templateId") long templateId,
			@RequestParam(value = "userName") String userName) {
		RestResult<SysTemplate> rs = new RestResult<SysTemplate>();

		if (templateId == 0) {
			rs = RestResult.lackParams();
			return JSON.toJSONString(rs);
		}

		SysTemplate template = sysTemplateService.findbyId(templateId);
		if (template == null) {
			rs = RestResult.invalidParams();
			return JSON.toJSONString(template);
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
			if (project.getId() == Long.valueOf(templateId)) {
				flag = true;
				continue;
			}
		}
		if (!flag) {			
			return JSON.toJSONString(RestResult.error(41000, "该用户没有权限访问该报表"));
		}

		rs.setData(template);
		return JSON.toJSONStringWithDateFormat(rs, "yyyy-MM-dd HH:mm:ss");
	}
}
