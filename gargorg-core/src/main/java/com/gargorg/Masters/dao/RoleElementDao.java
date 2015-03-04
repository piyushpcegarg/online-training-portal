/**
 * 
 */
package com.gargorg.Masters.dao;

import java.util.List;

import com.gargorg.Masters.valueObject.OrgRoleElementRlt;

/**
 * @author piyush
 *
 */
public interface RoleElementDao 
{
	public List<OrgRoleElementRlt> getAllRoleElements(long roleCode) throws Exception;
	public void updateAllRoleElements(List<OrgRoleElementRlt> lstRoleElementRltPersistent , List<OrgRoleElementRlt> lstOrgRoleElementRlt) throws Exception;
}
