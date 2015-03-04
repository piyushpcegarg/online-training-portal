package com.gargorg.Masters.dto;

import java.io.Serializable;

public class UserRoleDto implements Serializable {
	
	private static final long serialVersionUID = 4045149098013438333L;
	
	private long roleCode;
	private String roleName;
	private String roleShortName;
	private String roleDesc;
	private boolean activateFlag;
	
	public long getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(long roleCode) {
		this.roleCode = roleCode;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleShortName() {
		return roleShortName;
	}
	public void setRoleShortName(String roleShortName) {
		this.roleShortName = roleShortName;
	}
	public String getRoleDesc() {
		return roleDesc;
	}
	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}
	public boolean isActivateFlag() {
		return activateFlag;
	}
	public void setActivateFlag(boolean activateFlag) {
		this.activateFlag = activateFlag;
	}
}
