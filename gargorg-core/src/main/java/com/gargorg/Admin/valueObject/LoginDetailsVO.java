package com.gargorg.Admin.valueObject;

import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

import com.gargorg.Masters.valueObject.CmnLanguageMst;
import com.gargorg.Masters.valueObject.OrgEmpMst;
import com.gargorg.Masters.valueObject.OrgRoleMst;
import com.gargorg.Masters.valueObject.OrgUserImageMst;
import com.gargorg.Masters.valueObject.OrgUserMst;
import com.gargorg.Masters.valueObject.OrgUserRoleRlt;


public class LoginDetailsVO extends CustomUser 
{

	private static final long serialVersionUID = 2669289177155246867L;
	
	private OrgUserMst user;
	private OrgEmpMst employee;
	private CmnLanguageMst lang;
	private List<OrgUserRoleRlt> userRoles;
	private List<OrgRoleMst> roles;
	private OrgUserImageMst userImage;
	
	public LoginDetailsVO(OrgUserMst userMst ,OrgEmpMst  employee , CmnLanguageMst lang ,List<OrgUserRoleRlt> userRoles ,
			List<OrgRoleMst> roles ,Set<GrantedAuthority> authorities , OrgUserImageMst userImage)
	{
		super(userMst.getUserName() , userMst.getPassword() , true ,  true , true,
				true, authorities , lang.getLangShortName() , userMst.getOtp() , userMst);
		
		this.user = userMst;
		this.employee = employee;
		this.lang = lang;
		this.userRoles = userRoles;
		this.roles = roles;
		this.userImage = userImage;
	}
	
	public OrgUserMst getUser() {
		return user;
	}

	public void setUser(OrgUserMst user) {
		this.user = user;
	}

	public OrgEmpMst getEmployee() {
		return employee;
	}

	public void setEmployee(OrgEmpMst employee) {
		this.employee = employee;
	}

	public CmnLanguageMst getLang() {
		return lang;
	}

	public void setLang(CmnLanguageMst lang) {
		this.lang = lang;
	}

	public List<OrgUserRoleRlt> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<OrgUserRoleRlt> userRoles) {
		this.userRoles = userRoles;
	}

	public List<OrgRoleMst> getRoles() {
		return roles;
	}

	public void setRoles(List<OrgRoleMst> roles) {
		this.roles = roles;
	}
	
	public OrgUserImageMst getUserImage() {
		return userImage;
	}

	public void setUserImage(OrgUserImageMst userImage) {
		this.userImage = userImage;
	}
}