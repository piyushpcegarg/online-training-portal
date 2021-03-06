package com.gargorg.Masters.valueObject;
// Generated May 9, 2014 11:23:19 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * OrgRoleMst generated by hbm2java
 */
public class OrgRoleMst implements java.io.Serializable {

	private static final long serialVersionUID = 2790864238047004315L;
	private long roleId;
	private OrgUserMst orgUserMstByCreatedUserId;
	private OrgUserMst orgUserMstByUpdatedUserId;
	private long roleCode;
	private boolean activateFlag;
	private Date createdDate;
	private Date updatedDate;

	public OrgRoleMst() {
	}

	public OrgRoleMst(long roleId, OrgUserMst orgUserMstByCreatedUserId,
			long roleCode, boolean activateFlag, Date createdDate) {
		this.roleId = roleId;
		this.orgUserMstByCreatedUserId = orgUserMstByCreatedUserId;
		this.roleCode = roleCode;
		this.activateFlag = activateFlag;
		this.createdDate = createdDate;
	}

	public OrgRoleMst(long roleId, OrgUserMst orgUserMstByCreatedUserId,
			OrgUserMst orgUserMstByUpdatedUserId, long roleCode,
			boolean activateFlag, Date createdDate, Date updatedDate) {
		this.roleId = roleId;
		this.orgUserMstByCreatedUserId = orgUserMstByCreatedUserId;
		this.orgUserMstByUpdatedUserId = orgUserMstByUpdatedUserId;
		this.roleCode = roleCode;
		this.activateFlag = activateFlag;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
	}

	public long getRoleId() {
		return this.roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public OrgUserMst getOrgUserMstByCreatedUserId() {
		return this.orgUserMstByCreatedUserId;
	}

	public void setOrgUserMstByCreatedUserId(
			OrgUserMst orgUserMstByCreatedUserId) {
		this.orgUserMstByCreatedUserId = orgUserMstByCreatedUserId;
	}

	public OrgUserMst getOrgUserMstByUpdatedUserId() {
		return this.orgUserMstByUpdatedUserId;
	}

	public void setOrgUserMstByUpdatedUserId(
			OrgUserMst orgUserMstByUpdatedUserId) {
		this.orgUserMstByUpdatedUserId = orgUserMstByUpdatedUserId;
	}

	public long getRoleCode() {
		return this.roleCode;
	}

	public void setRoleCode(long roleCode) {
		this.roleCode = roleCode;
	}

	public boolean isActivateFlag() {
		return this.activateFlag;
	}

	public void setActivateFlag(boolean activateFlag) {
		this.activateFlag = activateFlag;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return this.updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

}
