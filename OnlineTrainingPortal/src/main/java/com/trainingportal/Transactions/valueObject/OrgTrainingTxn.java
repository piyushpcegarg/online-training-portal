package com.trainingportal.Transactions.valueObject;

// Generated Nov 25, 2014 11:19:12 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

import com.gargorg.Masters.valueObject.OrgUserMst;

/**
 * OrgTrainingTxn generated by hbm2java
 */
public class OrgTrainingTxn implements java.io.Serializable {

	private static final long serialVersionUID = 971591904737138997L;
	
	private long trainingId;
	private OrgUserMst orgUserMstByCreatedUserId;
	private OrgUserMst orgUserMstByUpdatedUserId;
	private long trainingCode;
	private String trainingName;
	private long trainingSerialNo;
	private String courseName;
	private String departmentProject;
	private long trainerCode;
	private long trainingType;
	private Date trainingDate;
	private Date trainingStartTime;
	private Date trainingEndTime;
	private long locationCode;
	private long attendanceStatus;
	private boolean activateFlag;
	private Date createdDate;
	private Date updatedDate;

	public OrgTrainingTxn() {
	}

	public OrgTrainingTxn(long trainingId,
			OrgUserMst orgUserMstByCreatedUserId, long trainingCode,
			String trainingName, long trainingSerialNo,
			String courseName, String departmentProject,
			long trainerCode, long trainingType, Date trainingDate,
			Date trainingStartTime, Date trainingEndTime,
			long locationCode, long attendanceStatus, boolean activateFlag, Date createdDate) {
		this.trainingId = trainingId;
		this.orgUserMstByCreatedUserId = orgUserMstByCreatedUserId;
		this.trainingCode = trainingCode;
		this.trainingName = trainingName;
		this.trainingSerialNo = trainingSerialNo;
		this.courseName = courseName;
		this.departmentProject = departmentProject;
		this.trainerCode = trainerCode;
		this.trainingType = trainingType;
		this.trainingDate = trainingDate;
		this.trainingStartTime = trainingStartTime;
		this.trainingEndTime = trainingEndTime;
		this.locationCode = locationCode;
		this.attendanceStatus = attendanceStatus;
		this.activateFlag = activateFlag;
		this.createdDate = createdDate;
	}

	public OrgTrainingTxn(long trainingId,
			OrgUserMst orgUserMstByCreatedUserId,
			OrgUserMst orgUserMstByUpdatedUserId, long trainingCode,
			String trainingName, long trainingSerialNo,
			String courseName, String departmentProject,
			long trainerCode, long trainingType, Date trainingDate,
			Date trainingStartTime, Date trainingEndTime,
			long locationCode,long attendanceStatus, boolean activateFlag, Date createdDate,
			Date updatedDate) {
		this.trainingId = trainingId;
		this.orgUserMstByCreatedUserId = orgUserMstByCreatedUserId;
		this.orgUserMstByUpdatedUserId = orgUserMstByUpdatedUserId;
		this.trainingCode = trainingCode;
		this.trainingName = trainingName;
		this.trainingSerialNo = trainingSerialNo;
		this.courseName = courseName;
		this.departmentProject = departmentProject;
		this.trainerCode = trainerCode;
		this.trainingType = trainingType;
		this.trainingDate = trainingDate;
		this.trainingStartTime = trainingStartTime;
		this.trainingEndTime = trainingEndTime;
		this.locationCode = locationCode;
		this.attendanceStatus = attendanceStatus;
		this.activateFlag = activateFlag;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
	}

	public long getTrainingId() {
		return this.trainingId;
	}

	public void setTrainingId(long trainingId) {
		this.trainingId = trainingId;
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

	public long getTrainingCode() {
		return this.trainingCode;
	}

	public void setTrainingCode(long trainingCode) {
		this.trainingCode = trainingCode;
	}

	public String getTrainingName() {
		return this.trainingName;
	}

	public void setTrainingName(String trainingName) {
		this.trainingName = trainingName;
	}

	public long getTrainingSerialNo() {
		return this.trainingSerialNo;
	}

	public void setTrainingSerialNo(long trainingSerialNo) {
		this.trainingSerialNo = trainingSerialNo;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getDepartmentProject() {
		return this.departmentProject;
	}

	public void setDepartmentProject(String departmentProject) {
		this.departmentProject = departmentProject;
	}

	public long getTrainerCode() {
		return this.trainerCode;
	}

	public void setTrainerCode(long trainerCode) {
		this.trainerCode = trainerCode;
	}

	public long getTrainingType() {
		return this.trainingType;
	}

	public void setTrainingType(long trainingType) {
		this.trainingType = trainingType;
	}

	public Date getTrainingDate() {
		return this.trainingDate;
	}

	public void setTrainingDate(Date trainingDate) {
		this.trainingDate = trainingDate;
	}

	public Date getTrainingStartTime() {
		return this.trainingStartTime;
	}

	public void setTrainingStartTime(Date trainingStartTime) {
		this.trainingStartTime = trainingStartTime;
	}

	public Date getTrainingEndTime() {
		return this.trainingEndTime;
	}

	public void setTrainingEndTime(Date trainingEndTime) {
		this.trainingEndTime = trainingEndTime;
	}

	public long getLocationCode() {
		return this.locationCode;
	}

	public void setLocationCode(long locationCode) {
		this.locationCode = locationCode;
	}
	
	public long getAttendanceStatus() {
		return attendanceStatus;
	}

	public void setAttendanceStatus(long attendanceStatus) {
		this.attendanceStatus = attendanceStatus;
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
