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
public interface CmnLookUpDao 
{
	public List<CmnLookupMstDto> getLookUpByParentLookUpCode(long parentLookupCode, long langId) throws Exception;
	public CmnLookupMstDto getLookUpByLookUpCode(long lookupCode, long langId) throws Exception;
	public CmnLookupMstDto getLookUpByLookUpName(String lookupName, long langId) throws Exception;
}
