package com.favccxx.report.service;

import java.util.List;

import com.favccxx.report.model.SysUserGroup;
import com.favccxx.report.model.SysUserGroupRole;
import com.favccxx.report.result.PageInfo;

public interface SysUserGroupService {
	
	/**
	 * 新建或更新用户组
	 * @param sysUserGroup
	 */
	void saveOrUpdateUserGroup(SysUserGroup sysUserGroup);
	
	/**
	 * 删除用户组
	 * @param id
	 */
	void deleteUserGroup(long id);
	
	/**
	 * 根据用户组Id查询用户组详细信息
	 * @param id
	 * @return
	 */
	SysUserGroup findById(long id);
	
	/**
	 * 查询所有的用户组
	 * @return
	 */
	List<SysUserGroup> findAll();
	
	/**
	 * 分页查询用户组
	 * @param offset
	 * @param pageSize
	 * @return
	 */
	PageInfo<SysUserGroup> pageUserGroups(int offset, int pageSize);
	
	
	/**
	 * 更新用户组用户
	 * @param groupId
	 * @param addUserIds
	 * @param removeUserIds
	 */
	void saveUserGroupUsers(long groupId, List<Long> addUserIds, List<Long> removeUserIds);
	
	/**
	 * 根据用户组Id查询用户组与角色之间的关系列表
	 * @param groupId
	 * @return
	 */
	List<SysUserGroupRole> listByGroupId(long groupId);
	
	/**
	 * 更新用户组与角色之间的关系
	 * @param userId
	 * @param groupId
	 * @param roleIds
	 */
	void updateUserGroupRoles(long userId, long groupId, List<Long> roleIds);
	
	/**
	 * 根据用户Id查询用户的组Id列表
	 * @param userId
	 * @return
	 */
	List<Long> ListGroupIdsByUserId(long userId);
	
	
	boolean isUserInGroup(long userId, long groupId);

}
