package com.favccxx.report.dao;

import java.util.List;

import com.favccxx.report.model.SysUser;

public interface SysUserDao {
	
	void addUser(SysUser user);
	
	/**
	 * 新增或更新用户
	 * @param user
	 */
	void saveUser(SysUser user);
	
	
	
	List<SysUser> listUsers();
	
	/**
	 * 根据用户名/邮箱/电话查找用户
	 * @param username
	 * @return
	 */
	SysUser findByUsername(String username);
	
	SysUser findById(long id);
	
	
	List<SysUser> pageList(String searchTxt, int offset, int pageSize);
	
	
	int pageCount(String searchTxt);

}
