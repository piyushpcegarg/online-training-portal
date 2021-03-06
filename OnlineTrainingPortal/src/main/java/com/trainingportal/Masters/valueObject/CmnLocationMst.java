package com.trainingportal.Masters.valueObject;

// Generated Nov 25, 2014 11:19:12 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

import com.gargorg.Masters.valueObject.OrgUserMst;


/**
 * CmnLocationMst generated by hbm2java
 */
public class CmnLocationMst implements java.io.Serializable {

	private static final long serialVersionUID = 7887766840315429604L;
	
	private long locationId;
	private OrgUserMst orgUserMstByCreatedUserId;
	private OrgUserMst orgUserMstByUpdatedUserId;
	private long locationCode;
	private String locationName;
	private boolean activateFlag;
	private Date createdDate;
	private Date updatedDate;

	public CmnLocationMst() {
	}

	public CmnLocationMst(long locationId,
			OrgUserMst orgUserMstByCreatedUserId, long locationCode,
			String locationName, boolean activateFlag, Date createdDate) {
		this.locationId = locationId;
		this.orgUserMstByCreatedUserId = orgUserMstByCreatedUserId;
		this.locationCode = locationCode;
		this.locationName = locationName;
		this.activateFlag = activateFlag;
		this.createdDate = createdDate;
	}

	public CmnLocationMst(long locationId,
			OrgUserMst orgUserMstByCreatedUserId,
			OrgUserMst orgUserMstByUpdatedUserId, long locationCode,
			String locationName, boolean activateFlag, Date createdDate,
			Date updatedDate) {
		this.locationId = locationId;
		this.orgUserMstByCreatedUserId = orgUserMstByCreatedUserId;
		this.orgUserMstByUpdatedUserId = orgUserMstByUpdatedUserId;
		this.locationCode = locationCode;
		this.locationName = locationName;
		this.activateFlag = activateFlag;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
	}

	public long getLocationId() {
		return this.locationId;
	}

	public void setLocationId(long locationId) {
		this.locationId = locationId;
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

	public long getLocationCode() {
		return this.locationCode;
	}

	public void setLocationCode(long locationCode) {
		this.locationCode = locationCode;
	}

	public String getLocationName() {
		return this.locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
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
