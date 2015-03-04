package com.trainingportal.Masters.dto;

import java.io.Serializable;

public class TrainerSkillDto implements Serializable {
	
	
	private static final long serialVersionUID = -4747238344290920916L;
	
	private long skillCode;
	private String skillName;
	private String skillDesc;
	private boolean activateFlag;
	
	
	public long getSkillCode() {
		return skillCode;
	}
	public void setSkillCode(long skillCode) {
		this.skillCode = skillCode;
	}
	public String getSkillName() {
		return skillName;
	}
	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}
	public String getSkillDesc() {
		return skillDesc;
	}
	public void setSkillDesc(String skillDesc) {
		this.skillDesc = skillDesc;
	}
	public boolean isActivateFlag() {
		return activateFlag;
	}
	public void setActivateFlag(boolean activateFlag) {
		this.activateFlag = activateFlag;
	}
	
}
