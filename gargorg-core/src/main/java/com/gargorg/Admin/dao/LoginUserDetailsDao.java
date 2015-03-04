/**
 * 
 */
package com.gargorg.Admin.dao;

import java.util.List;

import com.gargorg.Admin.dto.OrgElementMstDto;

/**
 * @author piyush
 *
 */
public interface LoginUserDetailsDao 
{
    public List<OrgElementMstDto> getMappedUserElements(Boolean activateFlag , String langShortName , List<Long> roleCodes) throws Exception;
}
