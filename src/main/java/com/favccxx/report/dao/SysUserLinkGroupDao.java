package com.favccxx.report.dao;

import java.util.List;

public interface SysUserLinkGroupDao {
	
	/**
	 * 更新用户与用户组之间的关联关系
	 * @param groupId 用户组Id
	 * @param linkUserIds 新添加的用户id列表
	 * @param removeUserIds 移除的用户Id列表
	 */
	void updateUserLinkGroups(long groupId, List<Long> linkUserIds, List<Long> removeUserIds);
	
	/**
	 * 删除用户与用户组关系
	 * @param groupId
	 * @param userId
	 */
	void deleteUserLinkGroup(long groupId, long userId);
	
	/**
	 * 删除某用户组下的用户
	 * @param groupId
	 */
	void deleteByGroupId(long groupId);
	
	/**
	 * 建立用户与用户组关系
	 * @param groupId
	 * @param userId
	 */
	void saveUserLinkGroup(long groupId, long userId);

	
	List<Long> ListGroupIdsByUserId(long userId);
	
	/**
	 * 判断用户是否存在某个用户组
	 * @param userId
	 * @param groupId
	 * @return
	 */
	boolean isUserInGroup(long userId, long groupId);
}
