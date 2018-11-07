package com.favccxx.report.service;

import java.util.List;

import com.favccxx.report.model.SysTemplateVersion;
import com.favccxx.report.model.SysTemplateVersionLog;
import com.favccxx.report.result.PageInfo;

public interface SysTemplateVersionService {

	/**
	 * 保存或更新模板编辑版本
	 * 
	 * @author chenxu
	 * @date Jan 4, 2018
	 * @param sysTemplateEdition
	 */
	public void saveTemplateEdition(SysTemplateVersion sysTemplateVersion);

	/**
	 * 创建草稿版本
	 * 如果同一模板已存在草稿版本，会删除已存在的草稿版本
	 * 
	 * @author chenxu
	 * @date Jan 5, 2018
	 * @param sysTemplateEdition
	 */
	public void createDraftEdition(SysTemplateVersion sysTemplateVersion);
	
	/**
	 * 根据Id查找当前版本的模板信息
	 * @author chenxu
	 * @date Jan 8, 2018
	 * @param id
	 * @return
	 */
	public SysTemplateVersion findById(long id);
	
	/**
	 * 删除模板版本
	 * 1. 删除模板版本对应的数据集表及文件
	 * 2. 删除模板当前版本文件
	 * 3. 删除当前版本记录
	 * @author chenxu
	 * @date Jan 9, 2018
	 * @param sysTemplateVersion
	 */
	public void deleteTemplateVersion(SysTemplateVersion sysTemplateVersion);
	
	/**
	 * 根据版本Id查看模板版本信息
	 * @author chenxu
	 * @date Jan 8, 2018
	 * @param templateId
	 * @return
	 */
	List<SysTemplateVersion> listByTemplateId(long templateId);
	
	/**
	 * 根据报表模板Id查询模板版本历史
	 * @param templateId
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	PageInfo<SysTemplateVersion> pageByTemplateId(long templateId, int pageIndex, int pageSize);

	/**
	 * 保存版本编辑记录
	 * @param versionLog
	 */
	void saveVersionLog(SysTemplateVersionLog versionLog);
	
	/**
	 * 根据版本id查询编辑详情
	 * @param templateVersionId
	 * @return
	 */
	List<SysTemplateVersionLog> listByTemplateVersionId(long templateVersionId);

}
