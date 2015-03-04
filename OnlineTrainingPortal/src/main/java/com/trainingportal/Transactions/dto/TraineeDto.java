package com.trainingportal.Transactions.dto;

import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

public class TraineeDto implements java.io.Serializable {

	private static final long serialVersionUID = 7684438395365466107L;
	
	private long trainingCode;
	private String trainingName;
	private String courseName;
	private Date trainingDate;
	private long userId;
	private String userName;
	private String empFname;
	private String empLname;
	private Long regMobileNo;
	private boolean activateFlag;
	@NotEmpty(message = "{TraineeDto.lstTraineeId.NotEmpty}")
	private List<Long> lstTraineeId;

	public long getTrainingCode() {
		return trainingCode;
	}
	public void setTrainingCode(long trainingCode) {
		this.trainingCode = trainingCode;
	}
	public String getTrainingName() {
		return trainingName;
	}
	public void setTrainingName(String trainingName) {
		this.trainingName = trainingName;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public Date getTrainingDate() {
		return trainingDate;
	}
	public void setTrainingDate(Date trainingDate) {
		this.trainingDate = trainingDate;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmpFname() {
		return empFname;
	}
	public void setEmpFname(String empFname) {
		this.empFname = empFname;
	}
	public String getEmpLname() {
		return empLname;
	}
	public void setEmpLname(String empLname) {
		this.empLname = empLname;
	}
	public Long getRegMobileNo() {
		return regMobileNo;
	}
	public void setRegMobileNo(Long regMobileNo) {
		this.regMobileNo = regMobileNo;
	}
	public boolean isActivateFlag() {
		return activateFlag;
	}
	public void setActivateFlag(boolean activateFlag) {
		this.activateFlag = activateFlag;
	}
	public List<Long> getLstTraineeId() {
		return lstTraineeId;
	}
	public void setLstTraineeId(List<Long> lstTraineeId) {
		this.lstTraineeId = lstTraineeId;
	}
}
