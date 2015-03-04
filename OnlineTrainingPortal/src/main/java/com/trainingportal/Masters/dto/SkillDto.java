package com.trainingportal.Masters.dto;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class SkillDto implements Serializable {
	
	
	private static final long serialVersionUID = 6925469512627859286L;
	
	private long skillCode;
	@NotBlank(message="{SkillDto.skillName.NotBlank}")
	@Length(min = 3, max = 30 , message = "{SkillDto.skillName.Length}")
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
