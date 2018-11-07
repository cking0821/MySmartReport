package com.favccxx.report.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RoleResourcePKID implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5508463008462295743L;

	@Column(name = "ROLE_ID")
	private long roleId;
	
	@Column(name = "RESOURCE_ID")
	private long resourceId;

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public long getResourceId() {
		return resourceId;
	}

	public void setResourceId(long resourceId) {
		this.resourceId = resourceId;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof RoleResourcePKID) {
			RoleResourcePKID key = (RoleResourcePKID)obj;
			if(this.roleId == key.getRoleId() && this.resourceId == key.getResourceId()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
	
	
	
	

}
