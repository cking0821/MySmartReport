package com.favccxx.report.dao;

import java.util.List;

import com.favccxx.report.model.SysTemplate;

public interface SysTemplateDao {
	
	/**
	 * 添加模板
	 * @author chenxu
	 * @date Jan 3, 2018
	 * @param sysTemplate
	 */
	void addSysTemplate(SysTemplate sysTemplate);
	
	/**
	 * 根据项目Id查找根节点模板
	 * @param projectId
	 */
	SysTemplate findRootNodeByProjectId(long projectId);
	
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
	
	/**
	 * 根据项目Id进行分页查询
	 * @author chenxu
	 * @date Jan 22, 2018
	 * @param projectId 项目Id
	 * @param offset 从第几条开始查
	 * @param pageSize 查询的条目
	 * @return
	 */
	List<SysTemplate> listByProjectId(long projectId, int offset, int pageSize);
	
	/**
	 * 查询模板总数
	 * @author chenxu
	 * @date Jan 22, 2018
	 * @param projectId
	 * @return
	 */
	long countByProjectId(long projectId);
	
	/**
	 * 分页查询需要显示的模板列表
	 * @param projectId 项目Id
	 * @param templateType 报表模板类型
	 * @param pageIndex
	 * @param pageSize
	 * @param sortField
	 * @param direction
	 * @return
	 */
	List<SysTemplate> pageListByProjectId(long projectId, String templateType, int pageIndex, int pageSize, String sortField, boolean ascending);
	
	/**
	 * 根据项目查询模板总数
	 * @param projectId
	 * @return
	 */
	long pageCountByProjectId(long projectId, String templateType);
	
	
	/**
	 * 查询所有特定类型的报表
	 * @param templateType
	 * @param pageIndex
	 * @param pageSize
	 * @param sortField
	 * @param ascending
	 * @return
	 */
	List<SysTemplate> pageAll(String templateType, int pageIndex, int pageSize, String sortField, boolean ascending);
	
	/**
	 * 查询报表总数
	 * @param templateType
	 * @return
	 */
	int countAll(String templateType);
	
	
	/**
	 * 根据项目分页查询报表模板列表
	 * @param projectIds
	 * @param templateType
	 * @param pageIndex
	 * @param pageSize
	 * @param sortField
	 * @param ascending
	 * @return
	 */
	List<SysTemplate> pageByProjectIds(List<Long> projectIds, String templateType, int pageIndex, int pageSize, String sortField, boolean ascending);
	
	
	/**
	 * 根据项目查询报表模板数量
	 * @param projectIds
	 * @param templateType
	 * @return
	 */
	int countByProjects(List<Long> projectIds, String templateType);
	
	
}
