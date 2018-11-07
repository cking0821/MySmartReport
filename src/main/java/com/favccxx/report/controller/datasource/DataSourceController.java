package com.favccxx.report.controller.datasource;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.favccxx.report.constants.ImageConstants;
import com.favccxx.report.constants.LogConstants;
import com.favccxx.report.constants.SysConstants;
import com.favccxx.report.context.SessionUser;
import com.favccxx.report.model.SysDataSource;
import com.favccxx.report.model.SysProject;
import com.favccxx.report.result.RestResult;
import com.favccxx.report.service.SysDataSourceService;
import com.favccxx.report.service.SysProjectService;
import com.favccxx.report.service.SysUserGroupService;
import com.favccxx.report.util.LogUtil;

@Controller
public class DataSourceController {
	
	@Autowired
	SysProjectService sysProjectService;
	@Autowired
	SysDataSourceService sysDataSourceService;
	@Autowired
	SysUserGroupService sysUserGroupService;
	
	@RequestMapping("/initDataSource")
	public ModelAndView initDataSource(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		
		SessionUser sessionUser = (SessionUser)session.getAttribute(SysConstants.USER_SESSION_KEY);
		
		LogUtil.logActivity(sessionUser.getUserName() + "查询数据源列表", LogConstants.OPERATE_QUERY, LogConstants.MODULE_DATASOURCE);		
		
		long userId = sessionUser.getUserId();
		List<Long> groupIds = sysUserGroupService.ListGroupIdsByUserId(userId);
		List<SysProject> list = sysProjectService.listByUserIdOrGroupId(userId, groupIds, false);
		
		mav.addObject("projects", list);
		mav.setViewName("/datasource/datasource_list");
		return mav;
	}
	
	@RequestMapping("/initDataSourceFolder")
	public ModelAndView initDataSourceFolder(String id) {
		ModelAndView mav = new ModelAndView();
		if(id!=null && !"".equals(id)) {
			SysDataSource dataSource = sysDataSourceService.findbyId(Long.parseLong(id));
			mav.addObject("dataSource", dataSource);
		}
		mav.setViewName("/datasource/datasource_folder");
		return mav;
	}
	
	@RequestMapping("/initNewDs")
	public ModelAndView initNewDs(String id) {
		ModelAndView mav = new ModelAndView();
		if(id!=null && !"".equals(id)) {
			SysDataSource dataSource = sysDataSourceService.findbyId(Long.parseLong(id));
			mav.addObject("dataSource", dataSource);
		}
		mav.setViewName("/datasource/datasource_edit");
		return mav;
	}
	
	//根据项目Id查询所有数据源
	@RequestMapping(value="/getDatasources", produces="application/json; charset=UTF-8")
	@ResponseBody
	public String getTreeNodes(HttpSession session, @RequestBody String projectId) {
		SessionUser sessionUser = (SessionUser)session.getAttribute(SysConstants.USER_SESSION_KEY);
		LogUtil.logActivity(sessionUser.getUserName() + "根据项目查询数据源信息", LogConstants.OPERATE_QUERY, LogConstants.MODULE_DATASOURCE);
		SysProject sysProject = sysProjectService.findbyId(Long.parseLong(projectId));
		if(sysProject==null) {
			return null;
		}
		List<SysDataSource> list = sysDataSourceService.listByProjectId(Long.parseLong(projectId));
		
		return JSON.toJSONString(RestResult.success(list));
	}
	
	
	//保存数据源目录
	@RequestMapping(value="/saveDsFolder", produces="application/json; charset=UTF-8")
	@ResponseBody
	public String saveDsFolder(HttpSession session, @RequestBody String jsonBody) {		
		SysDataSource sysDataSource = JSON.parseObject(jsonBody, SysDataSource.class);
		
		SessionUser sessionUser = (SessionUser)session.getAttribute(SysConstants.USER_SESSION_KEY);
		LogUtil.logActivity(sessionUser.getUserName() + "保存数据源目录", LogConstants.OPERATE_UPDATE, LogConstants.MODULE_DATASOURCE);
		long userId = sessionUser.getUserId();
		if(sysDataSource.getId()==0) {
			sysDataSource.setCreateUserId(userId);
			sysDataSource.setCreateTime(new Date());
			sysDataSource.setIconSkin(ImageConstants.ICON_FOLDER);
			sysDataSource.setType(SysConstants.FILE_TYPE_FOLDER);
			sysDataSource.setUpdateUserId(userId);
			sysDataSource.setUpdateTime(new Date());
			sysDataSourceService.saveSysDataSource(sysDataSource);
			return JSON.toJSONString(RestResult.success(sysDataSource));
		}else {
			SysDataSource existDataSource = sysDataSourceService.findbyId(sysDataSource.getId());
			existDataSource.setName(sysDataSource.getName());
			existDataSource.setDescription(sysDataSource.getDescription());
			sysDataSource.setUpdateUserId(userId);
			existDataSource.setUpdateTime(new Date());
			sysDataSourceService.saveSysDataSource(existDataSource);
			return JSON.toJSONString(RestResult.success(existDataSource));
		}
	}
	
	//保存数据源
	@RequestMapping(value="/saveDataSource", produces="application/json; charset=UTF-8")
	@ResponseBody
	public String saveDataSource(HttpSession session, @RequestBody String jsonBody) {		
		SysDataSource sysDataSource = JSON.parseObject(jsonBody, SysDataSource.class);
		SessionUser sessionUser = (SessionUser)session.getAttribute(SysConstants.USER_SESSION_KEY);
		LogUtil.logActivity(sessionUser.getUserName() + "保存数据源信息", LogConstants.OPERATE_UPDATE, LogConstants.MODULE_DATASOURCE);
		long userId = sessionUser.getUserId();
		if(sysDataSource.getId()==0) {
			sysDataSource.setCreateUserId(userId);
			sysDataSource.setCreateTime(new Date());
			sysDataSource.setIconSkin(ImageConstants.ICON_DATASOURCE);
			sysDataSource.setType(SysConstants.FILE_TYPE_DATASOURCE);
			sysDataSource.setUpdateUserId(userId);
			sysDataSource.setUpdateTime(new Date());
			sysDataSourceService.saveSysDataSource(sysDataSource);
			return JSON.toJSONString(RestResult.success(sysDataSource));
		}
		
		SysDataSource existDataSource = sysDataSourceService.findbyId(sysDataSource.getId());
		existDataSource.setName(sysDataSource.getName());
		existDataSource.setDescription(sysDataSource.getDescription());
		existDataSource.setDbType(sysDataSource.getDbType());
		existDataSource.setUsername(sysDataSource.getUsername());
		existDataSource.setPassword(sysDataSource.getPassword());
		existDataSource.setUrlAddress(sysDataSource.getUrlAddress());
		existDataSource.setUpdateUserId(userId);
		existDataSource.setUpdateTime(new Date());
		sysDataSourceService.saveSysDataSource(existDataSource);
		return JSON.toJSONString(RestResult.success(existDataSource));
	}
	
	
	@RequestMapping(value="/delDataSource", produces="application/json; charset=UTF-8")
	@ResponseBody
	public String delDataSource(HttpSession session, @RequestBody String jsonBody) {
		SessionUser sessionUser = (SessionUser)session.getAttribute(SysConstants.USER_SESSION_KEY);
		LogUtil.logActivity(sessionUser.getUserName() + "删除数据源", LogConstants.OPERATE_DELETE, LogConstants.MODULE_DATASOURCE);
		SysDataSource dataSource = JSON.parseObject(jsonBody, SysDataSource.class);
		sysDataSourceService.delSysDataSource(dataSource);
		return JSON.toJSONString(RestResult.success(SysConstants.SUCCESS_MSG));
		
	}

}
