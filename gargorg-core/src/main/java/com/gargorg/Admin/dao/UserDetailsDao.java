/**
 * 
 */
package com.gargorg.Admin.dao;

import org.springframework.stereotype.Component;

import com.gargorg.Admin.valueObject.LoginDetailsVO;
import com.gargorg.Masters.valueObject.OrgEmpMst;
import com.gargorg.Masters.valueObject.OrgUserMst;

/**
 * @author piyush
 *
 */
@Component
public interface UserDetailsDao 
{
	public OrgUserMst findByUsername(String username);
	public void updateUser(OrgUserMst user) throws Exception;
	public LoginDetailsVO fillLoginDetailsVO(Boolean activateFlag , String langShortName , Long userId) throws Exception;
	public OrgEmpMst getEmployeeDetails(String username , String locale) throws Exception;
}
