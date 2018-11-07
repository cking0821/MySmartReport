package com.favccxx.report.dao;

import java.util.List;

import com.favccxx.report.model.SysUserGroup;

public interface SysUserGroupDao {
	
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
	 * 分页查询用户组列表
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	List<SysUserGroup> pageList(int pageIndex, int pageSize);
	
	/**
	 * 查询用户总数
	 * @return
	 */
	int pageCount();

}
