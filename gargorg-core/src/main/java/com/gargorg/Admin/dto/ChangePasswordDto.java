package com.gargorg.Admin.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class ChangePasswordDto implements Serializable {
	
	private static final long serialVersionUID = -8474048648020613703L;
	
	/* 	String datePattern = "\\d{2}/\\d{2}/\\d{4}";		// datePattern should be dd/mm/yyyy
		String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[=!\\-@._*])(?=\\S+$).{8,20}$";
		passwordPattern Explanation:

		^                 # start-of-string
		(?=.*[0-9])       # a digit must occur at least once
		(?=.*[a-z])       # a lower case letter must occur at least once
		(?=.*[A-Z])       # an upper case letter must occur at least once
		(?=.*[=!\\-@._*])  # a special character must occur at least once(=!-@._* allowed)
		(?=\S+$)          # no whitespace allowed in the entire string
		.{8,20}             # anything, at least eight and maximum 20 places though
		$                 # end-of-string
	 */

	
	@NotBlank(message="{ChangePasswordDto.username.NotBlank}")
	@Length(min = 8, max = 30 , message = "{ChangePasswordDto.username.Length}")
	@Pattern(regexp = "^[a-zA-Z0-9.@]*$", message = "{ChangePasswordDto.username.Pattern}")
	private String username;
	@NotBlank(message="{ChangePasswordDto.oldPassword.NotBlank}")
	@Length(min = 8, max = 20 , message = "{ChangePasswordDto.oldPassword.Length}")
	@Pattern(regexp = "^(?=.*[A-Za-z0-9=!\\-@._*])(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[=!\\-@._*])(?=\\S+$).{8,20}$", message = "{ChangePasswordDto.oldPassword.Pattern}")
	private String oldPassword;
	@NotBlank(message="{ChangePasswordDto.newPassword.NotBlank}")
	@Length(min = 8, max = 20 , message = "{ChangePasswordDto.newPassword.Length}")
	@Pattern(regexp = "^(?=.*[A-Za-z0-9=!\\-@._*])(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[=!\\-@._*])(?=\\S+$).{8,20}$", message = "{ChangePasswordDto.newPassword.Pattern}")
	private String newPassword;
	@NotBlank(message="{ChangePasswordDto.confirmNewPassword.NotBlank}")
	@Length(min = 8, max = 20 , message = "{ChangePasswordDto.confirmNewPassword.Length}")
	@Pattern(regexp = "^(?=.*[A-Za-z0-9=!\\-@._*])(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[=!\\-@._*])(?=\\S+$).{8,20}$", message = "{ChangePasswordDto.confirmNewPassword.Pattern}")
	private String confirmNewPassword;
	private String txtUserDoj;
	private String txtUserDob;
	private long secretQueCode;
	private String userSecAnswer;
	private String userOtp;
	private String locale;
	private boolean dojRequired;
	private boolean dobRequired;
	private boolean secQuesRequired;
	private boolean otpRequired;
	private List<CmnLookupMstDto> lstSecurityQuestionsLookUp;
	private boolean beforeLogin;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getConfirmNewPassword() {
		return confirmNewPassword;
	}
	public void setConfirmNewPassword(String confirmNewPassword) {
		this.confirmNewPassword = confirmNewPassword;
	}
	public String getTxtUserDoj() {
		return txtUserDoj;
	}
	public void setTxtUserDoj(String txtUserDoj) {
		this.txtUserDoj = txtUserDoj;
	}
	public String getTxtUserDob() {
		return txtUserDob;
	}
	public void setTxtUserDob(String txtUserDob) {
		this.txtUserDob = txtUserDob;
	}
	public long getSecretQueCode() {
		return secretQueCode;
	}
	public void setSecretQueCode(long secretQueCode) {
		this.secretQueCode = secretQueCode;
	}
	public String getUserSecAnswer() {
		return userSecAnswer;
	}
	public void setUserSecAnswer(String userSecAnswer) {
		this.userSecAnswer = userSecAnswer;
	}
	public String getUserOtp() {
		return userOtp;
	}
	public void setUserOtp(String userOtp) {
		this.userOtp = userOtp;
	}
	public String getLocale() {
		return locale;
	}
	public void setLocale(String locale) {
		this.locale = locale;
	}
	public boolean isDojRequired() {
		return dojRequired;
	}
	public void setDojRequired(boolean dojRequired) {
		this.dojRequired = dojRequired;
	}
	public boolean isDobRequired() {
		return dobRequired;
	}
	public void setDobRequired(boolean dobRequired) {
		this.dobRequired = dobRequired;
	}
	public boolean isSecQuesRequired() {
		return secQuesRequired;
	}
	public void setSecQuesRequired(boolean secQuesRequired) {
		this.secQuesRequired = secQuesRequired;
	}
	public boolean isOtpRequired() {
		return otpRequired;
	}
	public void setOtpRequired(boolean otpRequired) {
		this.otpRequired = otpRequired;
	}
	public List<CmnLookupMstDto> getLstSecurityQuestionsLookUp() {
		return lstSecurityQuestionsLookUp;
	}
	public void setLstSecurityQuestionsLookUp(
			List<CmnLookupMstDto> lstSecurityQuestionsLookUp) {
		this.lstSecurityQuestionsLookUp = lstSecurityQuestionsLookUp;
	}
	public boolean isBeforeLogin() {
		return beforeLogin;
	}
	public void setBeforeLogin(boolean beforeLogin) {
		this.beforeLogin = beforeLogin;
	}
}