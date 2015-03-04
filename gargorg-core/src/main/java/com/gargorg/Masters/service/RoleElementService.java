/**
 * 
 */
package com.gargorg.Masters.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gargorg.Masters.dto.RoleDto;
import com.gargorg.Masters.dto.RoleElementDto;


/**
 * @author piyush
 *
 */
@Service
public interface RoleElementService 
{
	public List<RoleElementDto> getRoleElementList(long roleCode) throws Exception;
	public boolean updateRoleElementList(long roleCode , List<Long> lstElementCodes) throws Exception;
	public RoleDto getRoleDtoFromCode(long roleCode) throws Exception;
}
