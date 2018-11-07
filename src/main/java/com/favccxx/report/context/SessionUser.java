package com.favccxx.report.context;

import java.util.List;

import com.favccxx.report.model.SysResource;

public class SessionUser {
	
	/**
	 * 用户Id
	 */
	private long userId;

	/**
	 * 组织Id
	 */
	private String orgId;


	/**
	 * 用户名
	 */
	private String userName;

	
	/**
	 * 昵称
	 */
	private String nickName;
	
	/**
	 * 用户状态
	 */
	private String userStatus;

	/**
	 * 用户邮箱
	 */
	private String userMail;

	/**
	 * 用户电话
	 */
	private String userTel;
	
	/**
	 * 用户可访问资源列表
	 */
	private List<SysResource> userResourceList;
	
	/**
	 * 用户组Id列表
	 */
	private List<Long> groupIdList;
	

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public String getUserMail() {
		return userMail;
	}

	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}

	public String getUserTel() {
		return userTel;
	}

	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}

	public List<SysResource> getUserResourceList() {
		return userResourceList;
	}

	public void setUserResourceList(List<SysResource> userResourceList) {
		this.userResourceList = userResourceList;
	}

	public List<Long> getGroupIdList() {
		return groupIdList;
	}

	public void setGroupIdList(List<Long> groupIdList) {
		this.groupIdList = groupIdList;
	}
	
	
	

}
