package com.favccxx.report.service;

import java.util.List;

import com.favccxx.report.model.SysResource;
import com.favccxx.report.model.SysUser;
import com.favccxx.report.model.VUserRoleResources;
import com.favccxx.report.result.PageInfo;

public interface SysUserService {

	/**
	 * 新增用户
	 * @param user
	 */
	void addUser(SysUser user);
	
	/**
	 * 新增或更新用户
	 * @param user
	 */
	void saveUser(SysUser user);
	
	/**
	 * 逻辑删除用户
	 * @param id
	 */
	void delUser(long id);

	List<SysUser> listUsers();
	
	/**
	 * 根据用户名或邮箱查找用户
	 * @param username
	 * @return
	 */
	SysUser findByUsername(String username);
	
	SysUser findById(long id);
	
	
	List<VUserRoleResources> listUserResources(long userId);
	
	/**
	 * 分页查询用户
	 * @param searchTxt
	 * @param offset
	 * @param pageSize
	 * @return
	 */
	PageInfo<SysUser> pageUsers(String searchTxt, int offset, int pageSize);
	
	
	List<SysResource> listUserResourcesByUserId(long userId);

}
