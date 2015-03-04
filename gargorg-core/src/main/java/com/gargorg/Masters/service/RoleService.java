/**
 * 
 */
package com.gargorg.Masters.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gargorg.Masters.dto.OrgRoleMstDto;
import com.gargorg.Masters.dto.RoleDto;


/**
 * @author piyush
 *
 */
@Service
public interface RoleService 
{
	public List<OrgRoleMstDto> getRoleList() throws Exception;
	public RoleDto getRoleDtoFromCode(long roleCode) throws Exception;
	public void saveRole(RoleDto roleDto) throws Exception;
	public boolean updateRole(RoleDto roleDto) throws Exception;
	public boolean isRoleExist(RoleDto roleDto) throws Exception;
}
