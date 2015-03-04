package com.trainingportal.Masters.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class TrainerDto implements Serializable {

	private static final long serialVersionUID = 7386406382118744813L;
	
	private long trainerCode;
	private long userId;
	private String userName;
	private String empGender;
	private String engEmpFname;
	private String engEmpMname;
	private String engEmpLname;
	private String hinEmpFname;
	private String hinEmpMname;
	private String hinEmpLname;
	private Date empDob;
	private Date empDoj;
	private String email;
	private Long regMobileNo;
	private boolean activateFlag;
	private String encodedImageString;
	private boolean otpEnabled;
	private List<Long> lstSkillCodes;

	public long getTrainerCode() {
		return trainerCode;
	}
	public void setTrainerCode(long trainerCode) {
		this.trainerCode = trainerCode;
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
	public String getEmpGender() {
		return empGender;
	}
	public void setEmpGender(String empGender) {
		this.empGender = empGender;
	}
	public String getEngEmpFname() {
		return engEmpFname;
	}
	public void setEngEmpFname(String engEmpFname) {
		this.engEmpFname = engEmpFname;
	}
	public String getEngEmpMname() {
		return engEmpMname;
	}
	public void setEngEmpMname(String engEmpMname) {
		this.engEmpMname = engEmpMname;
	}
	public String getEngEmpLname() {
		return engEmpLname;
	}
	public void setEngEmpLname(String engEmpLname) {
		this.engEmpLname = engEmpLname;
	}
	public String getHinEmpFname() {
		return hinEmpFname;
	}
	public void setHinEmpFname(String hinEmpFname) {
		this.hinEmpFname = hinEmpFname;
	}
	public String getHinEmpMname() {
		return hinEmpMname;
	}
	public void setHinEmpMname(String hinEmpMname) {
		this.hinEmpMname = hinEmpMname;
	}
	public String getHinEmpLname() {
		return hinEmpLname;
	}
	public void setHinEmpLname(String hinEmpLname) {
		this.hinEmpLname = hinEmpLname;
	}
	public Date getEmpDob() {
		return empDob;
	}
	public void setEmpDob(Date empDob) {
		this.empDob = empDob;
	}
	public Date getEmpDoj() {
		return empDoj;
	}
	public void setEmpDoj(Date empDoj) {
		this.empDoj = empDoj;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public String getEncodedImageString() {
		return encodedImageString;
	}
	public void setEncodedImageString(String encodedImageString) {
		this.encodedImageString = encodedImageString;
	}
	public boolean isOtpEnabled() {
		return otpEnabled;
	}
	public void setOtpEnabled(boolean otpEnabled) {
		this.otpEnabled = otpEnabled;
	}
	public List<Long> getLstSkillCodes() {
		return lstSkillCodes;
	}
	public void setLstSkillCodes(List<Long> lstSkillCodes) {
		this.lstSkillCodes = lstSkillCodes;
	}
}
