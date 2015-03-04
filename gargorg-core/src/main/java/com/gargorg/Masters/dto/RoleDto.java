package com.gargorg.Masters.dto;

import java.io.Serializable;
import java.util.List;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class RoleDto implements Serializable {
	
	private static final long serialVersionUID = 1226056621935178424L;
	
	private long roleCode;
	@NotBlank(message="{OrgRoleMstDto.engRoleName.NotBlank}")
	@Length(min = 4, max = 120 , message = "{OrgRoleMstDto.engRoleName.Length}")
	private String engRoleName;
	@NotBlank(message="{OrgRoleMstDto.hinRoleName.NotBlank}")
	@Length(min = 4, max = 120 , message = "{OrgRoleMstDto.hinRoleName.Length}")
	private String hinRoleName;
	@NotBlank(message="{OrgRoleMstDto.engRoleShortName.NotBlank}")
	@Length(min = 4, max = 60 , message = "{OrgRoleMstDto.engRoleShortName.Length}")
	private String engRoleShortName;
	@NotBlank(message="{OrgRoleMstDto.hinRoleShortName.NotBlank}")
	@Length(min = 4, max = 60 , message = "{OrgRoleMstDto.hinRoleShortName.Length}")
	private String hinRoleShortName;
	@NotBlank(message="{OrgRoleMstDto.engRoleDesc.NotBlank}")
	@Length(min = 4, max = 200 , message = "{OrgRoleMstDto.engRoleDesc.Length}")
	private String engRoleDesc;
	@NotBlank(message="{OrgRoleMstDto.hinRoleDesc.NotBlank}")
	@Length(min = 4, max = 200 , message = "{OrgRoleMstDto.hinRoleDesc.Length}")
	private String hinRoleDesc;
	private boolean activateFlag;
	private List<Long> lstElementCodes;
	
	public long getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(long roleCode) {
		this.roleCode = roleCode;
	}
	public String getEngRoleName() {
		return engRoleName;
	}
	public void setEngRoleName(String engRoleName) {
		this.engRoleName = engRoleName;
	}
	public String getHinRoleName() {
		return hinRoleName;
	}
	public void setHinRoleName(String hinRoleName) {
		this.hinRoleName = hinRoleName;
	}
	public String getEngRoleShortName() {
		return engRoleShortName;
	}
	public void setEngRoleShortName(String engRoleShortName) {
		this.engRoleShortName = engRoleShortName;
	}
	public String getHinRoleShortName() {
		return hinRoleShortName;
	}
	public void setHinRoleShortName(String hinRoleShortName) {
		this.hinRoleShortName = hinRoleShortName;
	}
	public String getEngRoleDesc() {
		return engRoleDesc;
	}
	public void setEngRoleDesc(String engRoleDesc) {
		this.engRoleDesc = engRoleDesc;
	}
	public String getHinRoleDesc() {
		return hinRoleDesc;
	}
	public void setHinRoleDesc(String hinRoleDesc) {
		this.hinRoleDesc = hinRoleDesc;
	}
	public boolean isActivateFlag() {
		return activateFlag;
	}
	public void setActivateFlag(boolean activateFlag) {
		this.activateFlag = activateFlag;
	}
	public List<Long> getLstElementCodes() {
		return lstElementCodes;
	}
	public void setLstElementCodes(List<Long> lstElementCodes) {
		this.lstElementCodes = lstElementCodes;
	}
}
