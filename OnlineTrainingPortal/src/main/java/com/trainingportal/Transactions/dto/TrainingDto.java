package com.trainingportal.Transactions.dto;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import com.gargorg.Admin.dto.CmnLookupMstDto;
import com.trainingportal.Masters.dto.LocationDto;

public class TrainingDto implements java.io.Serializable {

	private static final long serialVersionUID = -4095007211714519537L;
	
	private long trainingCode;
	private String trainingName;
	private long trainingSerialNo;
	@NotBlank(message = "{TrainingDto.courseName.NotBlank}")
	@Length(min = 10, max = 200, message = "{TrainingDto.courseName.Length}")
	private String courseName;
	private String departmentProject;
	private long trainerCode;
	@NotNull(message = "{TrainingDto.trainerName.NotNull}")
	private String trainerName;
	private long trainingType;
	@NotNull(message = "{TrainingDto.trainingDate.NotNull}")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date trainingDate;
	@NotNull(message = "{TrainingDto.trainingStartTime.NotNull}")
	private String trainingStartTime;
	@NotNull(message = "{TrainingDto.trainingEndTime.NotNull}")
	private String trainingEndTime;
	private long locationCode;
	private boolean activateFlag;
	@NotEmpty(message = "{TrainingDto.lstTrainingType.NotEmpty}")
	private List<CmnLookupMstDto> lstTrainingType;
	@NotEmpty(message = "{TrainingDto.lstLocations.NotEmpty}")
	private List<LocationDto> lstLocations;
	private String trainingStatus;
	private long attendanceStatus;

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

	public String getTrainerName() {
		return trainerName;
	}

	public void setTrainerName(String trainerName) {
		this.trainerName = trainerName;
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

	public String getTrainingStartTime() {
		return this.trainingStartTime;
	}

	public void setTrainingStartTime(String trainingStartTime) {
		this.trainingStartTime = trainingStartTime;
	}

	public String getTrainingEndTime() {
		return this.trainingEndTime;
	}

	public void setTrainingEndTime(String trainingEndTime) {
		this.trainingEndTime = trainingEndTime;
	}

	public long getLocationCode() {
		return this.locationCode;
	}

	public void setLocationCode(long locationCode) {
		this.locationCode = locationCode;
	}

	public boolean isActivateFlag() {
		return this.activateFlag;
	}

	public void setActivateFlag(boolean activateFlag) {
		this.activateFlag = activateFlag;
	}
	
	public List<CmnLookupMstDto> getLstTrainingType() {
		return lstTrainingType;
	}

	public void setLstTrainingType(List<CmnLookupMstDto> lstTrainingType) {
		this.lstTrainingType = lstTrainingType;
	}

	public List<LocationDto> getLstLocations() {
		return lstLocations;
	}

	public void setLstLocations(List<LocationDto> lstLocations) {
		this.lstLocations = lstLocations;
	}

	public String getTrainingStatus() {
		return trainingStatus;
	}

	public void setTrainingStatus(String trainingStatus) {
		this.trainingStatus = trainingStatus;
	}

	public long getAttendanceStatus() {
		return attendanceStatus;
	}

	public void setAttendanceStatus(long attendanceStatus) {
		this.attendanceStatus = attendanceStatus;
	}
}
