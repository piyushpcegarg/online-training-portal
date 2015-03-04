/**
 * 
 */
package com.gargorg.Masters.dao;

import java.util.List;

import com.gargorg.Masters.dto.OrgRoleMstDto;
import com.gargorg.Masters.valueObject.OrgRoleDetailsRlt;
import com.gargorg.Masters.valueObject.OrgRoleMst;

/**
 * @author piyush
 *
 */
public interface RoleDao 
{
	public List<OrgRoleMstDto> getAllRoles() throws Exception;
	public void save(OrgRoleMst role , List<OrgRoleDetailsRlt> lstOrgRoleDetailsRlt) throws Exception;
	public List<OrgRoleMstDto> getRoleByCode(long roleCode) throws Exception;
	public void update(OrgRoleMst role , List<OrgRoleDetailsRlt> lstOrgRoleDetailsRlt) throws Exception;
	public Long getNextRoleCode() throws Exception;
	public OrgRoleDetailsRlt getRoleByName(String roleName) throws Exception;
}
