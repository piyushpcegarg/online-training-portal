/**
 * 
 */
package com.gargorg.Admin.dao;

import java.util.List;

import com.gargorg.Admin.dto.CmnLookupMstDto;


/**
 * @author piyush
 *
 */
public interface SettingDao 
{
	public void updateSecuritySettings(List<CmnLookupMstDto> lstCmnLookupMst) throws Exception;
}
