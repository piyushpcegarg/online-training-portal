package com.gargorg.Admin.dto;
// Generated May 9, 2014 11:23:19 PM by Hibernate Tools 3.4.0.CR1


/**
 * CmnLookupMst generated by hbm2java
 */
public class CmnLookupMstDto implements java.io.Serializable {

	private static final long serialVersionUID = 5800719916174290880L;
	private long lookupCode;
	private long parentLookupCode;
	private String lookupName;
	private String lookupShortName;
	private String lookupDesc;
	private boolean activateFlag;
	
	public long getLookupCode() {
		return lookupCode;
	}
	public void setLookupCode(long lookupCode) {
		this.lookupCode = lookupCode;
	}
	public long getParentLookupCode() {
		return parentLookupCode;
	}
	public void setParentLookupCode(long parentLookupCode) {
		this.parentLookupCode = parentLookupCode;
	}
	public String getLookupName() {
		return lookupName;
	}
	public void setLookupName(String lookupName) {
		this.lookupName = lookupName;
	}
	public String getLookupShortName() {
		return lookupShortName;
	}
	public void setLookupShortName(String lookupShortName) {
		this.lookupShortName = lookupShortName;
	}
	public String getLookupDesc() {
		return lookupDesc;
	}
	public void setLookupDesc(String lookupDesc) {
		this.lookupDesc = lookupDesc;
	}
	public boolean isActivateFlag() {
		return activateFlag;
	}
	public void setActivateFlag(boolean activateFlag) {
		this.activateFlag = activateFlag;
	}
}