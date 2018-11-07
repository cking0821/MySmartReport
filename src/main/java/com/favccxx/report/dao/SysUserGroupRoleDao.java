package com.favccxx.report.dao;

import java.util.List;

import com.favccxx.report.model.SysUserGroupRole;

public interface SysUserGroupRoleDao {
	
	/**
	 * 更新用户组与角色之间的关系
	 * @param userId 操作的用户Id
	 * @param groupId 用户组Id
	 * @param linkRoleIds 连接的角色Id
	 * @param removeRoleIds 移除的角色Id
	 */
	void updateUserGroupRole(long userId, long groupId, List<Long> linkRoleIds, List<Long> removeRoleIds);

	/**
	 * 添加用户组与角色之间的关系
	 * @param userId
	 * @param groupId
	 * @param roleId
	 */
	void saveUserGroupRole(long userId, long groupId, long roleId);
	
	/**
	 * 移除用户组与角色直接的关系
	 * @param groupId
	 */
	void deleteByGroupId(long groupId);
	
	/**
	 * 查询用户组的关联角色
	 * @param groupId
	 * @return
	 */
	List<SysUserGroupRole> listByGroupId(long groupId);
}
