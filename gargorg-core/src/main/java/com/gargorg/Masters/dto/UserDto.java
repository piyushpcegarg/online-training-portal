package com.gargorg.Masters.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import com.gargorg.Admin.dto.CmnLookupMstDto;

public class UserDto implements Serializable {

	private static final long serialVersionUID = 6425044707971893136L;

	private long userId;
	@NotBlank(message = "{UserDto.username.NotBlank}")
	@Length(min = 8, max = 30, message = "{UserDto.username.Length}")
	@Pattern(regexp = "^[a-zA-Z0-9.@]*$", message = "{UserDto.username.Pattern}")
	private String userName;
	private List<Long> lstRoleCodes;
	@NotBlank(message = "{UserDto.empGender.NotBlank}")
	private String empGender;
	@NotBlank(message = "{UserDto.engEmpFname.NotBlank}")
	@Length(min = 3, max = 30, message = "{UserDto.engEmpFname.Length}")
	private String engEmpFname;
	private String engEmpMname;
	@NotBlank(message = "{UserDto.engEmpLname.NotBlank}")
	@Length(min = 3, max = 30, message = "{UserDto.engEmpLname.Length}")
	private String engEmpLname;
	@NotBlank(message = "{UserDto.hinEmpFname.NotBlank}")
	@Length(min = 3, max = 30, message = "{UserDto.hinEmpFname.Length}")
	private String hinEmpFname;
	private String hinEmpMname;
	@NotBlank(message = "{UserDto.hinEmpLname.NotBlank}")
	@Length(min = 3, max = 30, message = "{UserDto.hinEmpLname.Length}")
	private String hinEmpLname;
	@NotNull(message = "{UserDto.empDob.NotNull}")
	@Past(message = "{UserDto.empDob.Past}")
	@DateTimeFormat(pattern = "dd/MM/YYYY")
	private Date empDob;
	@NotNull(message = "{UserDto.empDoj.NotNull}")
	@Past(message = "{UserDto.empDoj.Past}")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date empDoj;
	@NotBlank(message = "{UserDto.email.NotBlank}")
	@Length(min = 10, max = 40, message = "{UserDto.email.Length}")
	@Email(message = "{UserDto.email.Email}")
	private String email;
	@NotNull(message = "{UserDto.regMobileNo.NotNull}")
	@Range(min = 1000000000L, max = 9999999999L, message = "{UserDto.regMobileNo.Range}")
	private Long regMobileNo;
	private long secretQueCode;
	@NotBlank(message = "{UserDto.secretAnswer.NotBlank}")
	@Length(min = 3, message = "{UserDto.secretAnswer.Length}")
	private String secretAnswer;
	@NotEmpty(message = "{UserDto.lstSecurityQuestionsLookUp.NotEmpty}")
	private List<CmnLookupMstDto> lstSecurityQuestionsLookUp;
	private List<OrgRoleMstDto> lstRoles;
	private boolean activateFlag;
	private MultipartFile userImage;
	private String encodedImageString;
	private boolean otpEnabled;

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

	public List<Long> getLstRoleCodes() {
		return lstRoleCodes;
	}

	public void setLstRoleCodes(List<Long> lstRoleCodes) {
		this.lstRoleCodes = lstRoleCodes;
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

	public long getSecretQueCode() {
		return secretQueCode;
	}

	public void setSecretQueCode(long secretQueCode) {
		this.secretQueCode = secretQueCode;
	}

	public String getSecretAnswer() {
		return secretAnswer;
	}

	public void setSecretAnswer(String secretAnswer) {
		this.secretAnswer = secretAnswer;
	}

	public List<CmnLookupMstDto> getLstSecurityQuestionsLookUp() {
		return lstSecurityQuestionsLookUp;
	}

	public void setLstSecurityQuestionsLookUp(
			List<CmnLookupMstDto> lstSecurityQuestionsLookUp) {
		this.lstSecurityQuestionsLookUp = lstSecurityQuestionsLookUp;
	}

	public List<OrgRoleMstDto> getLstRoles() {
		return lstRoles;
	}

	public void setLstRoles(List<OrgRoleMstDto> lstRoles) {
		this.lstRoles = lstRoles;
	}

	public boolean isActivateFlag() {
		return activateFlag;
	}

	public void setActivateFlag(boolean activateFlag) {
		this.activateFlag = activateFlag;
	}

	public MultipartFile getUserImage() {
		return userImage;
	}

	public void setUserImage(MultipartFile userImage) {
		this.userImage = userImage;
	}

	public boolean isOtpEnabled() {
		return otpEnabled;
	}

	public void setOtpEnabled(boolean otpEnabled) {
		this.otpEnabled = otpEnabled;
	}

	public String getEncodedImageString() {
		return encodedImageString;
	}

	public void setEncodedImageString(String encodedImageString) {
		this.encodedImageString = encodedImageString;
	}
}
