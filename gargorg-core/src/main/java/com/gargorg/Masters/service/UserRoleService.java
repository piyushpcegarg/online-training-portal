/**
 * 
 */
package com.gargorg.Masters.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gargorg.Masters.dto.UserRoleDto;


/**
 * @author piyush
 *
 */
@Service
public interface UserRoleService 
{
	public List<UserRoleDto> getUserRoleList(long userId) throws Exception;
	public boolean updateUserRoleList(long userId , List<Long> lstRoleCodes) throws Exception;
}
