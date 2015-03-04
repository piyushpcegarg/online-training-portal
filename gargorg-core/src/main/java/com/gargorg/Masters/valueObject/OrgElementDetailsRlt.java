package com.gargorg.Masters.valueObject;
// Generated May 9, 2014 11:23:19 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * OrgElementDetailsRlt generated by hbm2java
 */
public class OrgElementDetailsRlt implements java.io.Serializable {

	private static final long serialVersionUID = -141306892389550884L;
	private long elementDetailId;
	private CmnLanguageMst cmnLanguageMst;
	private OrgUserMst orgUserMstByCreatedUserId;
	private OrgUserMst orgUserMstByUpdatedUserId;
	private long elementCode;
	private String elementName;
	private String elementDesc;
	private String elementTooltip;
	private boolean activateFlag;
	private Date createdDate;
	private Date updatedDate;

	public OrgElementDetailsRlt() {
	}

	public OrgElementDetailsRlt(long elementDetailId,
			CmnLanguageMst cmnLanguageMst,
			OrgUserMst orgUserMstByCreatedUserId, long elementCode,
			String elementName, String elementDesc, String elementTooltip,
			boolean activateFlag, Date createdDate) {
		this.elementDetailId = elementDetailId;
		this.cmnLanguageMst = cmnLanguageMst;
		this.orgUserMstByCreatedUserId = orgUserMstByCreatedUserId;
		this.elementCode = elementCode;
		this.elementName = elementName;
		this.elementDesc = elementDesc;
		this.elementTooltip = elementTooltip;
		this.activateFlag = activateFlag;
		this.createdDate = createdDate;
	}

	public OrgElementDetailsRlt(long elementDetailId,
			CmnLanguageMst cmnLanguageMst,
			OrgUserMst orgUserMstByCreatedUserId,
			OrgUserMst orgUserMstByUpdatedUserId, long elementCode,
			String elementName, String elementDesc, String elementTooltip,
			boolean activateFlag, Date createdDate, Date updatedDate) {
		this.elementDetailId = elementDetailId;
		this.cmnLanguageMst = cmnLanguageMst;
		this.orgUserMstByCreatedUserId = orgUserMstByCreatedUserId;
		this.orgUserMstByUpdatedUserId = orgUserMstByUpdatedUserId;
		this.elementCode = elementCode;
		this.elementName = elementName;
		this.elementDesc = elementDesc;
		this.elementTooltip = elementTooltip;
		this.activateFlag = activateFlag;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
	}

	public long getElementDetailId() {
		return this.elementDetailId;
	}

	public void setElementDetailId(long elementDetailId) {
		this.elementDetailId = elementDetailId;
	}

	public CmnLanguageMst getCmnLanguageMst() {
		return this.cmnLanguageMst;
	}

	public void setCmnLanguageMst(CmnLanguageMst cmnLanguageMst) {
		this.cmnLanguageMst = cmnLanguageMst;
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

	public long getElementCode() {
		return this.elementCode;
	}

	public void setElementCode(long elementCode) {
		this.elementCode = elementCode;
	}

	public String getElementName() {
		return this.elementName;
	}

	public void setElementName(String elementName) {
		this.elementName = elementName;
	}

	public String getElementDesc() {
		return this.elementDesc;
	}

	public void setElementDesc(String elementDesc) {
		this.elementDesc = elementDesc;
	}

	public String getElementTooltip() {
		return this.elementTooltip;
	}

	public void setElementTooltip(String elementTooltip) {
		this.elementTooltip = elementTooltip;
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