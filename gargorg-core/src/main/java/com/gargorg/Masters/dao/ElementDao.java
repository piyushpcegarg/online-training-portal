/**
 * 
 */
package com.gargorg.Masters.dao;

import java.util.List;

import com.gargorg.Admin.dto.OrgElementMstDto;
import com.gargorg.Masters.valueObject.OrgElementDetailsRlt;
import com.gargorg.Masters.valueObject.OrgElementMst;
import com.gargorg.Masters.valueObject.OrgRoleElementRlt;

/**
 * @author piyush
 *
 */
public interface ElementDao 
{
	public List<OrgElementMstDto> getAllElements() throws Exception;
	public List<OrgElementMstDto> getAllEditableElements() throws Exception;
	public void save(OrgElementMst element , List<OrgElementDetailsRlt> lstOrgElementDetailsRlt , OrgRoleElementRlt orgRoleElementRlt) throws Exception;
	public List<OrgElementMstDto> getElementByCode(long elementCode) throws Exception;
	public void update(OrgElementMst element , List<OrgElementDetailsRlt> lstOrgElementDetailsRlt) throws Exception;
	public Long getNextElementCode() throws Exception;
	public OrgElementMst getElementByName(String elementName) throws Exception;
}
