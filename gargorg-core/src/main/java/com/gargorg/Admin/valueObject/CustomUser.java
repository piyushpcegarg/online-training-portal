package com.gargorg.Admin.valueObject;

import java.util.Date;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.gargorg.Masters.valueObject.OrgUserMst;

public class CustomUser extends User 
{
	private static final long serialVersionUID = 1518188523931789016L;
	
	private long userId;
	private boolean activateFlag;
	private String firstlogin;
	private Date pwdchangedDate;
	private long secretQueCode;
	private String secretAnswer;
	private long invalidLoginCnt;
	private Date unlockTime;
	private boolean otpEnabled;
	private Long otp;
	private Date otpValidity;
	private String locale;
	private Long userOtp;
	private Long regMobileNo;
	
	public CustomUser(String username, String password, boolean enabled,
			boolean accountNonExpired, boolean credentialsNonExpired,
			boolean accountNonLocked, Set<GrantedAuthority> authorities ,String locale,Long userOtp ,OrgUserMst orgUserMstVO) 
	 {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.userId = orgUserMstVO.getUserId();
		this.activateFlag = orgUserMstVO.isActivateFlag();
		this.firstlogin = orgUserMstVO.getFirstlogin();
		this.pwdchangedDate = orgUserMstVO.getPwdchangedDate();
		this.secretQueCode = orgUserMstVO.getSecretQueCode();
		this.secretAnswer = orgUserMstVO.getSecretAnswer();
		this.invalidLoginCnt = orgUserMstVO.getInvalidLoginCnt();
		this.unlockTime = orgUserMstVO.getUnlockTime();
		this.otpEnabled = orgUserMstVO.isOtpEnabled();
		this.otp = orgUserMstVO.getOtp();
		this.otpValidity = orgUserMstVO.getOtpValidity();
		this.locale = locale;
		this.userOtp = userOtp;
		this.regMobileNo = orgUserMstVO.getRegMobileNo();
	 }

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public boolean isActivateFlag() {
		return activateFlag;
	}

	public void setActivateFlag(boolean activateFlag) {
		this.activateFlag = activateFlag;
	}

	public String getFirstlogin() {
		return firstlogin;
	}

	public void setFirstlogin(String firstlogin) {
		this.firstlogin = firstlogin;
	}

	public Date getPwdchangedDate() {
		return pwdchangedDate;
	}

	public void setPwdchangedDate(Date pwdchangedDate) {
		this.pwdchangedDate = pwdchangedDate;
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

	public long getInvalidLoginCnt() {
		return invalidLoginCnt;
	}

	public void setInvalidLoginCnt(long invalidLoginCnt) {
		this.invalidLoginCnt = invalidLoginCnt;
	}

	public Date getUnlockTime() {
		return unlockTime;
	}

	public void setUnlockTime(Date unlockTime) {
		this.unlockTime = unlockTime;
	}
	
	public boolean isOtpEnabled() {
		return otpEnabled;
	}

	public void setOtpEnabled(boolean otpEnabled) {
		this.otpEnabled = otpEnabled;
	}

	public Long getOtp() {
		return otp;
	}

	public void setOtp(Long otp) {
		this.otp = otp;
	}

	public Date getOtpValidity() {
		return otpValidity;
	}

	public void setOtpValidity(Date otpValidity) {
		this.otpValidity = otpValidity;
	}
	
	public Long getRegMobileNo() {
		return regMobileNo;
	}

	public void setRegMobileNo(Long regMobileNo) {
		this.regMobileNo = regMobileNo;
	}
	
	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}
	
	public Long getUserOtp() {
		return userOtp;
	}

	public void setUserOtp(Long userOtp) {
		this.userOtp = userOtp;
	}
}