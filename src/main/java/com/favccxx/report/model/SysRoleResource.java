package com.favccxx.report.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SYS_ROLE_RESOURCE")
public class SysRoleResource  {
	
	@Id
	RoleResourcePKID roleResourceId = new RoleResourcePKID();
	
	
	/**用户Id*/
	@Column(name = "USER_ID")
	private long userId;
	
	/**操作时间*/
	@Column(name = "UPDATE_TIME")
	private Date updateTime;
	
	
	
	public RoleResourcePKID getRoleResourceId() {
		return roleResourceId;
	}

	public void setRoleResourceId(RoleResourcePKID roleResourceId) {
		this.roleResourceId = roleResourceId;
	}

	

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	
	
	

}
