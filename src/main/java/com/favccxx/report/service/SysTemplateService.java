package com.favccxx.report.service;

import java.util.List;

import com.favccxx.report.model.SysTemplate;
import com.favccxx.report.result.PageInfo;

public interface SysTemplateService {

	/**
	 * 添加模板
	 * @author chenxu
	 * @date Jan 3, 2018
	 * @param sysTemplate
	 */
	void addSysTemplate(SysTemplate sysTemplate);
	
	/**
	 * 添加或更新模板
	 * @author chenxu
	 * @date Jan 3, 2018
	 * @param sysTemplate
	 */
	void saveSysTemplate(SysTemplate sysTemplate);	
	
	void delSysTemplate(SysTemplate sysTemplate);
	
	void delSysTemplate(long id);
	
	List<SysTemplate> listTemplates();
	
	SysTemplate findbyId(long directoryId);
	
	List<SysTemplate> listByProjectId(long projectId);
	
	void findAndUpdate(SysTemplate sysTemplate);
	
	
	
	/**
	 * 根据项目Id分页查询模板列表
	 * @param projectId 项目Id
	 * @param pageIndex 当前页索引
	 * @param pageSize 每页显示条目
	 * @param sortField 排序字段
	 * @param ascending true/false, 升序或降序
	 * @return
	 */
	PageInfo<SysTemplate> pageByProjectId(long projectId, int pageIndex, int pageSize, String sortField,  boolean ascending);
	
	/**
	 * 查询所有有效的报表模板
	 * @param pageIndex
	 * @param pageSize
	 * @param sortField
	 * @param ascending
	 * @return
	 */
	PageInfo<SysTemplate> pageAll(int pageIndex, int pageSize, String sortField,  boolean ascending);
	
	/**
	 * 分页查询项目中的报表模板
	 * @param projectIds
	 * @param pageIndex
	 * @param pageSize
	 * @param sortField
	 * @param ascending
	 * @return
	 */
	PageInfo<SysTemplate> pageByProjects(List<Long> projectIds, int pageIndex, int pageSize, String sortField,  boolean ascending);
}
