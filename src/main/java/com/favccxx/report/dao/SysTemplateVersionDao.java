package com.favccxx.report.dao;

import java.util.List;

import com.favccxx.report.model.SysTemplateVersion;

public interface SysTemplateVersionDao {

	/**
	 * 保存或更新模板编辑版本
	 * 
	 * @author chenxu
	 * @date Jan 4, 2018
	 * @param sysTemplateEdition
	 */
	public void saveTemplateEdition(SysTemplateVersion sysTemplateVersion);

	
	/**
	 * 根据模板Id和版本号删除模板编辑版本
	 * @author chenxu
	 * @date Jan 5, 2018
	 * @param templateId 模板Id
	 * @param version 版本号
	 */
	public void deleteEditionByVersion(long templateId, String version);
	
	/**
	 * 删除模板版本记录
	 * @author chenxu
	 * @date Jan 10, 2018
	 * @param sysTemplateVersion
	 */
	public void deleteTemplateVersion(SysTemplateVersion sysTemplateVersion);
	
	
	/**
	 * 根据Id查找模板版本信息
	 * @author chenxu
	 * @date Jan 8, 2018
	 * @param id
	 * @return
	 */
	SysTemplateVersion findById(long id);
	
	/**
	 * 根据模板Id查看版本列表
	 * @author chenxu
	 * @date Jan 8, 2018
	 * @param templateId
	 * @return
	 */
	List<SysTemplateVersion> listByTemplateId(long templateId);
	
	
	/**
	 * 分页查询版本历史
	 * @param templateId
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	List<SysTemplateVersion> pageListByTemplateId(long templateId, int pageIndex, int pageSize);
	
	/**
	 * 分页查询版本历史
	 * @param templateId
	 * @return
	 */
	long pageCountByTemplateId(long templateId);
}
