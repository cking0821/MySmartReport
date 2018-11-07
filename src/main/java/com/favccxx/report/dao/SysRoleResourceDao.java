package com.favccxx.report.dao;

import java.util.List;

import com.favccxx.report.model.SysRoleResource;

public interface SysRoleResourceDao {
	
	List<SysRoleResource> listRoleResoucesByRoleId(long roleId);
	
	/**
	 * 添加角色权限
	 * @param roleId 角色Id
	 * @param userId 用户id
	 * @param resourceIds 资源Id数组
	 */
	void addRoleResource(long roleId, long userId, Long[] resourceIds);
	
	/**
	 * 删除角色权限
	 * @param roleId 角色id
	 */
	void deleteRoleResource(long roleId);

}
