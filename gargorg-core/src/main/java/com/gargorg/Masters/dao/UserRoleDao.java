/**
 * 
 */
package com.gargorg.Masters.dao;

import java.util.List;

import com.gargorg.Masters.valueObject.OrgUserRoleRlt;

/**
 * @author piyush
 *
 */
public interface UserRoleDao 
{
	public List<OrgUserRoleRlt> getAllUserRoles(long roleCode) throws Exception;
	public void updateAllUserRoles(List<OrgUserRoleRlt> lstUserRoleRltPersistent , List<OrgUserRoleRlt> lstOrgUserRoleRlt) throws Exception;
}
