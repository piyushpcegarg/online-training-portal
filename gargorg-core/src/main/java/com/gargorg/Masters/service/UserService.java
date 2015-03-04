/**
 * 
 */
package com.gargorg.Masters.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gargorg.Admin.dto.CmnLookupMstDto;
import com.gargorg.Masters.dto.OrgRoleMstDto;
import com.gargorg.Masters.dto.UserDto;
import com.gargorg.Masters.valueObject.OrgUserMst;


/**
 * @author piyush
 *
 */
@Service
public interface UserService
{
	public List<CmnLookupMstDto> getSecurityQuestions() throws Exception;
	public List<OrgRoleMstDto> getRoles() throws Exception;
	public List<UserDto> getUserList() throws Exception;
	public void saveUser(UserDto userDto) throws Exception;
	public boolean updateUser(UserDto userDto) throws Exception;
	public UserDto getUserDtoFromId(long userId) throws Exception;
	public OrgUserMst getUserFromUsername(String username) throws Exception;
	public OrgUserMst getUserFromMobileno(String mobileNo) throws Exception;
}
