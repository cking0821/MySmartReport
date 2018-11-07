package com.favccxx.report.service;

import java.util.List;

import com.favccxx.report.model.SysResource;
import com.favccxx.report.model.SysRoleResource;

public interface SysResourceService {
	
	List<SysResource> listResources();
	
	/**
	 * 根据用户角色获取角色权限
	 * @param roleId 角色id
	 * @return
	 */
	List<SysRoleResource> listRoleResources(long roleId);
	
	
	List<SysResource> listResourcesByRoleId(long roleId);
	
	/**
	 * 删除角色权限
	 * @param roleId
	 */
	void deleteRoleResource(long roleId);
	
	/**
	 * 添加角色权限
	 * @param roleId
	 * @param userId
	 * @param resourceIds
	 */
	void addRoleResource(long roleId, long userId, Long[] resourceIds);
}
