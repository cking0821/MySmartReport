package com.favccxx.report.controller.template;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.favccxx.report.constants.ImageConstants;
import com.favccxx.report.constants.LogConstants;
import com.favccxx.report.constants.SysConstants;
import com.favccxx.report.context.SessionUser;
import com.favccxx.report.model.SysDataSource;
import com.favccxx.report.model.SysProject;
import com.favccxx.report.model.SysTemplate;
import com.favccxx.report.model.SysTemplateVersion;
import com.favccxx.report.model.SysTemplateVersionLog;
import com.favccxx.report.result.JsonResult;
import com.favccxx.report.result.RestResult;
import com.favccxx.report.service.SysDataSourceService;
import com.favccxx.report.service.SysProjectService;
import com.favccxx.report.service.SysTemplateService;
import com.favccxx.report.service.SysTemplateVersionService;
import com.favccxx.report.service.SysUserGroupService;
import com.favccxx.report.util.DateUtil;
import com.favccxx.report.util.LogUtil;

@Controller
public class TemplateController {
	
	@Autowired
	SysProjectService sysProjectService;
	@Autowired
	SysUserGroupService sysUserGroupService;
	@Autowired
	SysDataSourceService sysDataSourceService; 
	@Autowired
	SysTemplateService sysTemplateService;
	@Autowired
	SysTemplateVersionService sysTemplateVersionService;
	
	
	@RequestMapping("/initTemplate")
	public ModelAndView initTemplate(HttpSession session) {
		ModelAndView mav = new ModelAndView();	
		SessionUser sessionUser = (SessionUser)session.getAttribute(SysConstants.USER_SESSION_KEY);
		LogUtil.logActivity(sessionUser.getUserName() + "查询模板列表", LogConstants.OPERATE_QUERY, LogConstants.MODULE_TEMPLATE);
		long userId = sessionUser.getUserId();
		
		List<Long> groupIds = sysUserGroupService.ListGroupIdsByUserId(userId);
		List<SysProject> list = sysProjectService.listByUserIdOrGroupId(userId, groupIds, false);
		
		mav.addObject("projects", list);	
		mav.setViewName("/template/template_list");
		return mav;
	}
	
	//查询模板的版本列表
	@RequestMapping("/initTemplateVersion")
	public ModelAndView initTemplateVersion(HttpSession session, long templateId) {
		ModelAndView mav = new ModelAndView();		
		
		SessionUser sessionUser = (SessionUser)session.getAttribute(SysConstants.USER_SESSION_KEY);
		LogUtil.logActivity(sessionUser.getUserName() + "查询模板版本列表", LogConstants.OPERATE_QUERY, LogConstants.MODULE_TEMPLATE);
		
		List<SysTemplateVersion> versionList = sysTemplateVersionService.listByTemplateId(templateId);				
		mav.addObject("versionList", versionList);		
		mav.setViewName("/template/template_version");
		return mav;
	}
	
	
	@RequestMapping(value="/getTemplateVersionLog", produces="application/json; charset=UTF-8")
	@ResponseBody
	public String getTemplateVersionLog(HttpSession session, @RequestBody String templateVersionId) {	
		List<SysTemplateVersionLog> list = sysTemplateVersionService.listByTemplateVersionId(Long.valueOf(templateVersionId));
				
		JsonResult<SysTemplateVersionLog> json = new JsonResult<SysTemplateVersionLog>();
		json.setList(list);
		json.setStatus(SysConstants.SUCCESS_STATUS_CODE);
				
		return JSON.toJSONString(json, SerializerFeature.WriteDateUseDateFormat);
	}
	
	
	@RequestMapping("/initTemplateReport")
	public ModelAndView initTemplateReport(HttpSession session, String id, String projectId) {
		ModelAndView mav = new ModelAndView();	
		
		SessionUser sessionUser = (SessionUser)session.getAttribute(SysConstants.USER_SESSION_KEY);
		LogUtil.logActivity(sessionUser.getUserName() + "查询模板基本信息", LogConstants.OPERATE_QUERY, LogConstants.MODULE_TEMPLATE);
		
		if(id!=null && !"".equals(id)) {
			SysTemplate directory = sysTemplateService.findbyId(Long.parseLong(id));
			mav.addObject("directory", directory);		
		}
		
		//查询某项目的所有可用数据源
		if(projectId!=null && !"".equals(projectId)) {
			List<SysDataSource> dataSourceList = sysDataSourceService.listDataSourcesByProjectId(Long.parseLong(projectId));
			mav.addObject("dataSourceList", dataSourceList);				
		}
				
		mav.setViewName("/template/template_report");
		return mav;
	}
	
	@RequestMapping("/initTemplateDirectory")
	public ModelAndView initTemplateDirectory(HttpSession session, String id) {
		ModelAndView mav = new ModelAndView();	
		
		SessionUser sessionUser = (SessionUser)session.getAttribute(SysConstants.USER_SESSION_KEY);
		LogUtil.logActivity(sessionUser.getUserName() + "查询模板目录", LogConstants.OPERATE_QUERY, LogConstants.MODULE_TEMPLATE);
		
		if(id!=null && !"".equals(id)) {
			SysTemplate directory = sysTemplateService.findbyId(Long.parseLong(id));
			mav.addObject("directory", directory);		
		}
				
		mav.setViewName("/template/template_directory");
		return mav;
	}
	
	
	@RequestMapping(value="/getTemplateTree", produces="application/json; charset=UTF-8")
	@ResponseBody
	public String getTemplateTree(HttpSession session, @RequestBody String projectId) {
		
		
		SessionUser sessionUser = (SessionUser)session.getAttribute(SysConstants.USER_SESSION_KEY);
		LogUtil.logActivity(sessionUser.getUserName() + "根据项目查询模板目录树", LogConstants.OPERATE_QUERY, LogConstants.MODULE_TEMPLATE);
		
		
		SysProject sysProject = sysProjectService.findbyId(Long.parseLong(projectId));
		if(sysProject==null) {
			return null;
		}
		
		List<SysTemplate> list = sysTemplateService.listByProjectId(Long.parseLong(projectId));
		//当目录为空时，创建一个根目录
		if(list==null || list.size()==0) {
			SysTemplate rootDirectory = new SysTemplate();
			rootDirectory.setId(0);
			rootDirectory.setName(sysProject.getProjectName());
			rootDirectory.setProjectId(sysProject.getId());
			sysTemplateService.addSysTemplate(rootDirectory);
			
			list.add(rootDirectory);
		}
		
		JsonResult<SysTemplate> json = new JsonResult<SysTemplate>();
		json.setList(list);
		json.setStatus(SysConstants.SUCCESS_STATUS_CODE);
				
		return JSON.toJSONString(json);
	}
	
	//保存或更新目录
	@RequestMapping(value="/saveDirectoryFile", produces="application/json; charset=UTF-8")
	@ResponseBody
	public String saveDirectoryFile(HttpSession session, @RequestBody String jsonBody) {		
		SysTemplate directory = JSON.parseObject(jsonBody, SysTemplate.class);
		
		SessionUser sessionUser = (SessionUser)session.getAttribute(SysConstants.USER_SESSION_KEY);
		LogUtil.logActivity(sessionUser.getUserName() + "保存模板目录", LogConstants.OPERATE_UPDATE, LogConstants.MODULE_TEMPLATE);
		
		
		
		long userId = sessionUser.getUserId();
		
		if(directory.getId()==0) {
			directory.setCreateTime(new Date());
			directory.setIconSkin(ImageConstants.ICON_FOLDER);
			directory.setType(SysConstants.FILE_TYPE_FOLDER);
			directory.setCreateTime(new Date());
			directory.setCreateUserId(userId);
			directory.setUpdateTime(new Date());
			directory.setUpdateUserId(userId);
			sysTemplateService.saveSysTemplate(directory);		
			return JSON.toJSONString(RestResult.success(directory));
		}else {
			//更新目录
			SysTemplate existDirectory = sysTemplateService.findbyId(directory.getId());
			existDirectory.setName(directory.getName());
			existDirectory.setDescription(directory.getDescription());
			existDirectory.setUpdateUserId(userId);
			existDirectory.setUpdateTime(new Date());
			sysTemplateService.saveSysTemplate(existDirectory);
			return JSON.toJSONString(RestResult.success(existDirectory));
		}
		
	}
	
	
	//保存或更新目录
	@RequestMapping(value="/saveDirectoryReport", produces="application/json; charset=UTF-8")
	@ResponseBody
	public String saveDirectoryReport(HttpSession session, @RequestBody String jsonBody) {		
		SysTemplate directory = JSON.parseObject(jsonBody, SysTemplate.class);
		SessionUser sessionUser = (SessionUser)session.getAttribute(SysConstants.USER_SESSION_KEY);
		LogUtil.logActivity(sessionUser.getUserName() + "保存模板", LogConstants.OPERATE_UPDATE, LogConstants.MODULE_TEMPLATE);
		
		long userId = sessionUser.getUserId();
		if(directory.getId()==0) {
			directory.setCreateTime(new Date());
			directory.setIconSkin(ImageConstants.ICON_REPORT);
			directory.setType(SysConstants.FILE_TYPE_REPORT);
			directory.setCreateTime(new Date());
			directory.setCreateUserId(userId);
			directory.setUpdateTime(new Date());
			directory.setUpdateUserId(userId);
			
			//设置模板的数据库类型，上传模板时更换数据源
			if("JDBC".equals(directory.getDataSourceType()) && StringUtils.isNoneBlank(directory.getDataSource())) {
				SysDataSource dataSource = sysDataSourceService.findbyId(Long.valueOf(directory.getDataSource()));
				if("DATASOURCE".equals(dataSource.getType())) {
					directory.setDbType(dataSource.getDbType());
				}
			}
			
			if (directory.getParameter()!=null && !"".equals(directory.getParameter().trim()) && !directory.getParameter().startsWith("&")) {
				directory.setParameter("&" + directory.getParameter());
			}
			if (directory.getParameter()!=null && !"".equals(directory.getParameter().trim()) && directory.getParameter().startsWith("&")) {
				directory.setParameter(directory.getParameter());
			}
			
			sysTemplateService.saveSysTemplate(directory);		
			return JSON.toJSONString(RestResult.success(directory));
		}else {
			//更新目录
			SysTemplate existDirectory = sysTemplateService.findbyId(directory.getId());
			existDirectory.setName(directory.getName());
			if (directory.getParameter()!=null && !"".equals(directory.getParameter().trim()) && !directory.getParameter().startsWith("&")) {
				existDirectory.setParameter("&" + directory.getParameter());
			}
			if (directory.getParameter()!=null && !"".equals(directory.getParameter().trim()) && directory.getParameter().startsWith("&")) {
				existDirectory.setParameter(directory.getParameter());
			}
			existDirectory.setDescription(directory.getDescription());
			existDirectory.setDirectoryLabel(directory.getDirectoryLabel());
			existDirectory.setDataSource(directory.getDataSource());
			existDirectory.setUpdateUserId(userId);
			existDirectory.setUpdateTime(new Date());
			sysTemplateService.saveSysTemplate(existDirectory);
			return JSON.toJSONString(RestResult.success(existDirectory));
		}
		
	}
	
	
	
	
	
	
	
	/**
	 * 根据ID查询目录详情
	 * @param jsonBody
	 * @return
	 */
	@RequestMapping(value="/previewReport", produces="application/json; charset=UTF-8")
	@ResponseBody
	public String previewReport(HttpSession session, @RequestBody String jsonBody) {		
		SysTemplate template = JSON.parseObject(jsonBody, SysTemplate.class);	
		SessionUser sessionUser = (SessionUser)session.getAttribute(SysConstants.USER_SESSION_KEY);
		LogUtil.logActivity(sessionUser.getUserName() + "预览报表信息", LogConstants.OPERATE_QUERY, LogConstants.MODULE_REPORT);
		
		
		template = sysTemplateService.findbyId(template.getId());
		if(!StringUtils.isBlank(template.getParameter())) {
			String originalParams = template.getParameter();
			StringBuffer sb = new StringBuffer();
			String[] paramArr = originalParams.split("&");
			for(int i=0; i<paramArr.length; i++) {
				if(!StringUtils.isBlank(paramArr[i])) {
					String[] keyValues = paramArr[i].split("=");
					//报表参数转义
					if(keyValues.length==2 && !StringUtils.isBlank(keyValues[1])) {
						String value = keyValues[1];
						sb.append("&").append(keyValues[0]).append("=");
						if("$USERNAME".equals(value)) {
							sb.append(sessionUser.getUserName());
						}else if("$USERID".equals(value)) {
							sb.append(sessionUser.getUserId());
						}else if("$ORGID".equals(value)) {
							sb.append(sessionUser.getOrgId());
						}else if("$DATE".equals(value)) {
							sb.append(DateUtil.date2String(new Date(), "yyyy-MM-dd"));
						}else if("$DATETIME".equals(value)) {
							sb.append(DateUtil.date2String(new Date(), "yyyy-MM-dd HH:mm:ss"));
						}else {
							sb.append(value);
						}
					}
				}
			}
			
			template.setParameter(sb.toString());		
		}
		
		return JSON.toJSONString(RestResult.success(template), SerializerFeature.WriteDateUseDateFormat);
	}
	
	
	
	@RequestMapping("/securityViewReport")
	public ModelAndView securityViewReport(HttpSession session, long id) {
		ModelAndView mav = new ModelAndView();	
		SessionUser sessionUser = (SessionUser)session.getAttribute(SysConstants.USER_SESSION_KEY);
		
		SysTemplate template = sysTemplateService.findbyId(id);
		if(!StringUtils.isBlank(template.getParameter())) {
			String originalParams = template.getParameter();
			StringBuffer sb = new StringBuffer();
			String[] paramArr = originalParams.split("&");
			for(int i=0; i<paramArr.length; i++) {
				if(!StringUtils.isBlank(paramArr[i])) {
					String[] keyValues = paramArr[i].split("=");
					//报表参数转义
					if(keyValues.length==2 && !StringUtils.isBlank(keyValues[1])) {
						String value = keyValues[1];
						sb.append("&").append(keyValues[0]).append("=");
						if("$USERNAME".equals(value)) {
							sb.append(sessionUser.getUserName());
						}else if("$USERID".equals(value)) {
							sb.append(sessionUser.getUserId());
						}else if("$ORGID".equals(value)) {
							sb.append(sessionUser.getOrgId());
						}else if("$DATE".equals(value)) {
							sb.append(DateUtil.date2String(new Date(), "yyyy-MM-dd"));
						}else if("$DATETIME".equals(value)) {
							sb.append(DateUtil.date2String(new Date(), "yyyy-MM-dd HH:mm:ss"));
						}else {
							sb.append(value);
						}
					}
				}
			}
			
			template.setParameter(sb.toString());		
		}
		
		
		String viewAddr = "__report=" + template.getReportName() + "&templateId=" + id;
		if(!StringUtils.isBlank(template.getParameter())) {
			viewAddr = viewAddr + template.getParameter(); 
		}
		
		mav.addObject("viewAddr", viewAddr);
				
		mav.setViewName("/template/view_template");
		return mav;
	}
	
	
	
	
	@RequestMapping(value="/addTreeNode", produces="application/json; charset=UTF-8")
	@ResponseBody
	public String addTreeNode(@RequestBody String jsonBody) {		
		SysTemplate paramData = JSON.parseObject(jsonBody, SysTemplate.class);
		
		SysTemplate treeNode = new SysTemplate();
		treeNode.setParentId(paramData.getParentId());
		treeNode.setProjectId(paramData.getProjectId());
		treeNode.setName("目录" + paramData.getId());
		
		sysTemplateService.saveSysTemplate(treeNode);
		
		return JSON.toJSONString(RestResult.success(treeNode));
	}
	
	@RequestMapping(value="/updateTreeNode", produces="application/json; charset=UTF-8")
	@ResponseBody
	public String updateTreeNode(@RequestBody String jsonBody) {
		SysTemplate paramData = JSON.parseObject(jsonBody, SysTemplate.class);
		
		sysTemplateService.findAndUpdate(paramData);
		
		return JSON.toJSONString(RestResult.success(paramData));
	}
	
	@RequestMapping(value="/delTreeNode", produces="application/json; charset=UTF-8")
	@ResponseBody
	public String delTreeNode(HttpSession session, @RequestBody String jsonBody) {
		SessionUser sessionUser = (SessionUser)session.getAttribute(SysConstants.USER_SESSION_KEY);
		LogUtil.logActivity(sessionUser.getUserName() + "删除模板", LogConstants.OPERATE_DELETE, LogConstants.MODULE_TEMPLATE);
		
		SysTemplate sysDirectory = JSON.parseObject(jsonBody, SysTemplate.class);
		sysTemplateService.delSysTemplate(sysDirectory.getId());
		return JSON.toJSONString(RestResult.success("success"));
		
	}
	
	
	@RequestMapping(value="/deleteTemplateVersion", produces="application/json; charset=UTF-8")
	@ResponseBody
	public String deleteTemplateVersion(HttpSession session, long versionId) {
		SessionUser sessionUser = (SessionUser)session.getAttribute(SysConstants.USER_SESSION_KEY);
		LogUtil.logActivity(sessionUser.getUserName() + "删除模板模板", LogConstants.OPERATE_DELETE, LogConstants.MODULE_TEMPLATE);
		
		SysTemplateVersion sysTemplateVersion = sysTemplateVersionService.findById(versionId);
		if(sysTemplateVersion!=null) {
			sysTemplateVersionService.deleteTemplateVersion(sysTemplateVersion);
		}
		return JSON.toJSONString(RestResult.success("success"));
	}

}
