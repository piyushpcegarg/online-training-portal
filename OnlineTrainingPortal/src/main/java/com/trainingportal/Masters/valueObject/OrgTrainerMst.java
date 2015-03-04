package com.trainingportal.Masters.valueObject;

// Generated Nov 25, 2014 11:19:12 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

import com.gargorg.Masters.valueObject.OrgUserMst;

/**
 * OrgTrainerMst generated by hbm2java
 */
public class OrgTrainerMst implements java.io.Serializable {

	private static final long serialVersionUID = 8948002918568459917L;
	
	private long trainerId;
	private OrgUserMst orgUserMstByUserId;
	private OrgUserMst orgUserMstByCreatedUserId;
	private OrgUserMst orgUserMstByUpdatedUserId;
	private long trainerCode;
	private boolean activateFlag;
	private Date createdDate;
	private Date updatedDate;

	public OrgTrainerMst() {
	}

	public OrgTrainerMst(long trainerId, OrgUserMst orgUserMstByUserId,
			OrgUserMst orgUserMstByCreatedUserId, long trainerCode,
			boolean activateFlag, Date createdDate) {
		this.trainerId = trainerId;
		this.orgUserMstByUserId = orgUserMstByUserId;
		this.orgUserMstByCreatedUserId = orgUserMstByCreatedUserId;
		this.trainerCode = trainerCode;
		this.activateFlag = activateFlag;
		this.createdDate = createdDate;
	}

	public OrgTrainerMst(long trainerId, OrgUserMst orgUserMstByUserId,
			OrgUserMst orgUserMstByCreatedUserId,
			OrgUserMst orgUserMstByUpdatedUserId, long trainerCode,
			boolean activateFlag, Date createdDate, Date updatedDate) {
		this.trainerId = trainerId;
		this.orgUserMstByUserId = orgUserMstByUserId;
		this.orgUserMstByCreatedUserId = orgUserMstByCreatedUserId;
		this.orgUserMstByUpdatedUserId = orgUserMstByUpdatedUserId;
		this.trainerCode = trainerCode;
		this.activateFlag = activateFlag;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
	}

	public long getTrainerId() {
		return this.trainerId;
	}

	public void setTrainerId(long trainerId) {
		this.trainerId = trainerId;
	}

	public OrgUserMst getOrgUserMstByUserId() {
		return this.orgUserMstByUserId;
	}

	public void setOrgUserMstByUserId(OrgUserMst orgUserMstByUserId) {
		this.orgUserMstByUserId = orgUserMstByUserId;
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

	public long getTrainerCode() {
		return this.trainerCode;
	}

	public void setTrainerCode(long trainerCode) {
		this.trainerCode = trainerCode;
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
