package com.gargorg.Masters.valueObject;
// Generated May 9, 2014 11:23:19 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * CmnLanguageMst generated by hbm2java
 */
public class CmnLanguageMst implements java.io.Serializable {

	private static final long serialVersionUID = -258078490137366585L;
	private long langId;
	private OrgUserMst orgUserMstByCreatedUserId;
	private OrgUserMst orgUserMstByUpdatedUserId;
	private String langName;
	private String langShortName;
	private Date createdDate;
	private Date updatedDate;
	private boolean activateFlag;
	private Set orgElementDetailsRlts = new HashSet(0);
	private Set cmnLookupDetailsRlts = new HashSet(0);
	private Set orgEmpMsts = new HashSet(0);
	private Set orgRoleDetailsRlts = new HashSet(0);

	public CmnLanguageMst() {
	}

	public CmnLanguageMst(long langId, OrgUserMst orgUserMstByCreatedUserId,
			String langName, String langShortName, Date createdDate,
			boolean activateFlag) {
		this.langId = langId;
		this.orgUserMstByCreatedUserId = orgUserMstByCreatedUserId;
		this.langName = langName;
		this.langShortName = langShortName;
		this.createdDate = createdDate;
		this.activateFlag = activateFlag;
	}

	public CmnLanguageMst(long langId, OrgUserMst orgUserMstByCreatedUserId,
			OrgUserMst orgUserMstByUpdatedUserId, String langName,
			String langShortName, Date createdDate, Date updatedDate,
			boolean activateFlag, Set orgElementDetailsRlts,
			Set cmnLookupDetailsRlts, Set orgEmpMsts, Set orgRoleDetailsRlts) {
		this.langId = langId;
		this.orgUserMstByCreatedUserId = orgUserMstByCreatedUserId;
		this.orgUserMstByUpdatedUserId = orgUserMstByUpdatedUserId;
		this.langName = langName;
		this.langShortName = langShortName;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
		this.activateFlag = activateFlag;
		this.orgElementDetailsRlts = orgElementDetailsRlts;
		this.cmnLookupDetailsRlts = cmnLookupDetailsRlts;
		this.orgEmpMsts = orgEmpMsts;
		this.orgRoleDetailsRlts = orgRoleDetailsRlts;
	}

	public long getLangId() {
		return this.langId;
	}

	public void setLangId(long langId) {
		this.langId = langId;
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

	public String getLangName() {
		return this.langName;
	}

	public void setLangName(String langName) {
		this.langName = langName;
	}

	public String getLangShortName() {
		return this.langShortName;
	}

	public void setLangShortName(String langShortName) {
		this.langShortName = langShortName;
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

	public boolean isActivateFlag() {
		return this.activateFlag;
	}

	public void setActivateFlag(boolean activateFlag) {
		this.activateFlag = activateFlag;
	}

	public Set getOrgElementDetailsRlts() {
		return this.orgElementDetailsRlts;
	}

	public void setOrgElementDetailsRlts(Set orgElementDetailsRlts) {
		this.orgElementDetailsRlts = orgElementDetailsRlts;
	}

	public Set getCmnLookupDetailsRlts() {
		return this.cmnLookupDetailsRlts;
	}

	public void setCmnLookupDetailsRlts(Set cmnLookupDetailsRlts) {
		this.cmnLookupDetailsRlts = cmnLookupDetailsRlts;
	}

	public Set getOrgEmpMsts() {
		return this.orgEmpMsts;
	}

	public void setOrgEmpMsts(Set orgEmpMsts) {
		this.orgEmpMsts = orgEmpMsts;
	}

	public Set getOrgRoleDetailsRlts() {
		return this.orgRoleDetailsRlts;
	}

	public void setOrgRoleDetailsRlts(Set orgRoleDetailsRlts) {
		this.orgRoleDetailsRlts = orgRoleDetailsRlts;
	}

}