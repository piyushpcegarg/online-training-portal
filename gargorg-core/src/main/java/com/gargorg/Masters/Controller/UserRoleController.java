/**
 * This will all the functionalities related to userRole.
 */
package com.gargorg.Masters.Controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.gargorg.Masters.dto.UserDto;
import com.gargorg.Masters.dto.UserRoleDto;
import com.gargorg.Masters.service.UserRoleService;
import com.gargorg.Masters.service.UserService;

/**
 * @author piyush
 *
 */
@Controller
@RequestMapping("/userRole")
public class UserRoleController 
{
	private static final Logger LOGGER = LoggerFactory.getLogger(UserRoleController.class);
    
	@Autowired  
    private UserRoleService userRoleService;  
	@Autowired  
    private UserService userService;  
	@Autowired
	private HttpServletRequest reqest;
      
    /** 
     * Get called when UserRole main link is clicked from menus for loading all the userRoles from database. 
     * displayUserRole.jsp page is opened , which shows all the userRoles stored in the database with userRole name.
     * This page shows 3 buttons (Create , Update , Close)
     * <p>Expected HTTP GET and request '/userRole/allUserRoles'.</p>
     */
	
	@RequestMapping(value = "/allUserRoles", method = RequestMethod.GET)
    public String getAllRoles(ModelMap model) throws Exception
    {
    	try
    	{
    		List<UserDto> userList = userService.getUserList();
    		model.addAttribute("userList", userList);
    		model.addAttribute("userDto", new UserDto());
    		return "displayUserRole";
    	}
    	catch(Exception e)
    	{
    		LOGGER.error("Error description", e);
			throw e;
    	}
    }
	
	@RequestMapping(params = "viewUserRolePage", method = RequestMethod.POST)
    public String getViewUserRolePage(@RequestParam long userId ,ModelMap model) throws Exception
    {
    	try
    	{
			List<UserRoleDto> lstUserRoleDto = userRoleService.getUserRoleList(userId);
			UserDto userDto = userService.getUserDtoFromId(userId);
			model.addAttribute("lstUserRoleDto", lstUserRoleDto);
			model.addAttribute("userDto", userDto);
			model.addAttribute("userName", userDto.getUserName());
			model.addAttribute("action", "view");
    		model.addAttribute("readOnly", true);
    		return "userRole";
    	}
    	catch(Exception e)
    	{
    		LOGGER.error("Error description", e);
			throw e;
    	}
    }
	
	@RequestMapping(params = "updateUserRolePage", method = RequestMethod.POST)
    public String getUpdateUserRolePage(@RequestParam long userId , ModelMap model) throws Exception
    {
    	try
    	{
			List<UserRoleDto> lstUserRoleDto = userRoleService.getUserRoleList(userId);
			UserDto userDto = userService.getUserDtoFromId(userId);
			model.addAttribute("lstUserRoleDto", lstUserRoleDto);
			model.addAttribute("userDto", userDto);
			model.addAttribute("userName", userDto.getUserName());
			model.addAttribute("action", "update");
    		model.addAttribute("readOnly", false);
    		return "userRole";
    	}
    	catch(Exception e)
    	{
    		LOGGER.error("Error description", e);
			throw e;
    	}
    }
	
    @RequestMapping(params = "updateUserRole", method = RequestMethod.POST)
    public String updateUserRolesByUserId(UserDto userDto ,ModelMap model) throws Exception
    {
    	try
    	{
    		long userId = userDto.getUserId();
    		String userName = userDto.getUserName();
    		List<Long> lstRoleCodes = userDto.getLstRoleCodes();
    		
    		//update Role element Mapping in the database -> Start
			boolean isUserRoleUpdated = userRoleService.updateUserRoleList(userId , lstRoleCodes);
			if(isUserRoleUpdated)
			{
    			model.addAttribute("successMsg", "updateUserRoleSuccessMsg");
    			model.addAttribute("action", "view");
    			model.addAttribute("readOnly", true);
			}
			else
			{
				model.addAttribute("errorMessages", "noChange");
				model.addAttribute("action", "update");
    			model.addAttribute("readOnly", false);
			}
			//update Role element Mapping in the database -> End
			List<UserRoleDto> lstUserRoleDto = userRoleService.getUserRoleList(userId);
			userDto = userService.getUserDtoFromId(userId);
			model.addAttribute("lstUserRoleDto", lstUserRoleDto);
			model.addAttribute("userDto", userDto);
			model.addAttribute("userName", userName);
    		return "userRole";
    	}
    	catch(Exception e)
    	{
    		LOGGER.error("Error description", e);
			throw e;
    	}
    }
}
