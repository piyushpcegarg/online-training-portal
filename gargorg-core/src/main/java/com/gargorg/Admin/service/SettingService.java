/**
 * This Service will contains the functionality for setting related activity.
 */
package com.gargorg.Admin.service;

import java.util.List;

import com.gargorg.Admin.dto.CmnLookupMstDto;
import com.gargorg.Masters.valueObject.OrgUserMst;


/**
 * @author piyush
 *
 */
public interface SettingService 
{
	public OrgUserMst resetPassword(String username) throws Exception;
	public List<CmnLookupMstDto> getSecuritySettings() throws Exception;
	public void updateSecuritySettings(List<CmnLookupMstDto> lstCmnLookupMst) throws Exception;
}

