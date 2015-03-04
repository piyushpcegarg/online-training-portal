package com.gargorg.Masters.dto;

import java.util.Date;

import com.gargorg.Masters.valueObject.CmnLanguageMst;
import com.gargorg.Masters.valueObject.OrgUserMst;

public class OrgRoleMstDto implements java.io.Serializable {
	
	private static final long serialVersionUID = -8574451298205888270L;
	
	private long roleId;
	private long roleDetailId;
	private long roleCode;
	private String roleName;
	private String roleShortName;
	private String roleDesc;
	private boolean activateFlag;
	private CmnLanguageMst cmnLanguageMst;
	private OrgUserMst orgUserMstByCreatedUserId;
	private Date createdDate;

	public long getRoleId() {
		return roleId;
	}
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
	public long getRoleDetailId() {
		return roleDetailId;
	}
	public void setRoleDetailId(long roleDetailId) {
		this.roleDetailId = roleDetailId;
	}
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
	public CmnLanguageMst getCmnLanguageMst() {
		return this.cmnLanguageMst;
	}

	public void setCmnLanguageMst(CmnLanguageMst cmnLanguageMst) {
		this.cmnLanguageMst = cmnLanguageMst;
	}
	public OrgUserMst getOrgUserMstByCreatedUserId() {
		return orgUserMstByCreatedUserId;
	}
	public void setOrgUserMstByCreatedUserId(OrgUserMst orgUserMstByCreatedUserId) {
		this.orgUserMstByCreatedUserId = orgUserMstByCreatedUserId;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	

}
