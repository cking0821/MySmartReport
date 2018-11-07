package com.favccxx.report.dao;

import java.util.List;

import com.favccxx.report.model.SysProjectLinkUserGroup;

public interface SysProjectLinkUserGroupDao {
	
	/**
	 * 创建
	 * @param userLinkGroup
	 */
	void save(SysProjectLinkUserGroup projectLinkUserGroup);
	
	/**
	 * 删除
	 * @param userLinkGroup
	 */
	void delete(SysProjectLinkUserGroup projectLinkUserGroup);
	
	/**
	 * 根据项目Id查找所有关联的用户组
	 * @param projectId
	 * @return
	 */
	List<SysProjectLinkUserGroup> listByProjectId(long projectId);
	

	void deleteByProjectId(long projectId);

}
