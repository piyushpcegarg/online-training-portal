/**
 * 
 */
package com.gargorg.Masters.dao;

import java.util.List;

import com.gargorg.Masters.dto.UserDto;
import com.gargorg.Masters.valueObject.OrgEmpMst;
import com.gargorg.Masters.valueObject.OrgUserImageMst;
import com.gargorg.Masters.valueObject.OrgUserMst;
import com.gargorg.Masters.valueObject.OrgUserRoleRlt;

/**
 * @author piyush
 *
 */
public interface UserDao 
{
	public List<UserDto> getAllUsers() throws Exception;
	public void save(OrgUserMst user , List<OrgEmpMst> lstOrgEmpMst , OrgUserImageMst userImage , List<OrgUserRoleRlt> lstOrgUserRoleRlt) throws Exception;
	public void update(OrgUserMst user , List<OrgEmpMst> lstOrgEmpMst , OrgUserImageMst userImage) throws Exception;
	public OrgUserMst getUserById(long userId) throws Exception;
	public List<OrgEmpMst> getEmployeeById(long userId) throws Exception;
	public OrgUserImageMst getUserImageById(long userId) throws Exception;
	public List<OrgUserRoleRlt> getUserRoleById(long userId) throws Exception;
	public OrgUserMst getUserByMobileno(String mobileNo) throws Exception;
}
