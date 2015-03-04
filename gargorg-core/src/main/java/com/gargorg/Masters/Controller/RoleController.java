/**
 * This will all the functionalities related to role.
 */
package com.gargorg.Masters.Controller;


import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.gargorg.Masters.dto.OrgRoleMstDto;
import com.gargorg.Masters.dto.RoleDto;
import com.gargorg.Masters.service.RoleService;

/**
 * @author piyush
 *
 */
@Controller
@RequestMapping("/role")
public class RoleController 
{
	private static final Logger LOGGER = LoggerFactory.getLogger(RoleController.class);
    
	@Autowired  
    private RoleService roleService;  
      
    /** 
     * Get called when Role main link is clicked from menus for loading all the roles from database. 
     * displayrole.jsp page is opened , which shows all the roles stored in the database with role name.
     * This page shows 3 buttons (Create , Update , Close)
     * <p>Expected HTTP GET and request '/role/allRoles'.</p>
     */
	// The following method called when user click on navigation menus
    @RequestMapping(value = "/allRoles", method = RequestMethod.GET)
    public String getAllRoles(ModelMap model) throws Exception
    {
    	try
    	{
    		List<OrgRoleMstDto> roleList = roleService.getRoleList();
    		model.addAttribute("roleList", roleList);
    		model.addAttribute("orgRoleMstDto",new OrgRoleMstDto());
    		return "displayrole";
    	}
    	catch(Exception e)
    	{
    		LOGGER.error("Error description", e);
			throw e;
    	}
    }
    
    @RequestMapping(params = "createRolePage", method = RequestMethod.POST)
    public String getCreateRolePage(ModelMap model) throws Exception
    {
    	try
    	{
    		RoleDto roleDto = new RoleDto();
    		model.addAttribute("action", "create");
    		model.addAttribute("roleDto", roleDto);
    		return "role";
    	}
    	catch(Exception e)
    	{
    		LOGGER.error("Error description", e);
			throw e;
    	}
    }
    
    @RequestMapping(params = "updateRolePage", method = RequestMethod.POST)
    public String getUpdateRolePage(@RequestParam long roleCode , ModelMap model) throws Exception
    {
    	try
    	{
    		RoleDto roleDto = roleService.getRoleDtoFromCode(roleCode);
    		model.addAttribute("roleDto", roleDto);
    		model.addAttribute("action", "update");
    		model.addAttribute("readOnly", false);
    		return "role";
    	}
    	catch(Exception e)
    	{
    		LOGGER.error("Error description", e);
			throw e;
    	}
    }
    
    @RequestMapping(params = "viewRolePage", method = RequestMethod.POST)
    public String getViewRolePage(@RequestParam long roleCode , ModelMap model) throws Exception
    {
    	try
    	{
    		RoleDto roleDto = roleService.getRoleDtoFromCode(roleCode);
    		model.addAttribute("roleDto", roleDto);
    		model.addAttribute("action", "view");
    		model.addAttribute("readOnly", true);
    		return "role";
    	}
    	catch(Exception e)
    	{
    		LOGGER.error("Error description", e);
			throw e;
    	}
    }
    
    @RequestMapping(params = "saveRole", method = RequestMethod.POST)
    public String saveRole(@Valid RoleDto roleDto ,BindingResult result, ModelMap model) throws Exception
    {
    	try
    	{
    		if(result.hasErrors()) 
			{
    			model.addAttribute("action", "create");
    			model.addAttribute("readOnly", false);
			} 
    		else
    		{
    			boolean isRoleExist = roleService.isRoleExist(roleDto);
    			if(isRoleExist)
    			{
    				model.addAttribute("errorMessages", "roleAlreadyExist");
    				model.addAttribute("action", "create");
        			model.addAttribute("readOnly", false);
    			}
    			else
    			{
	    			//Save roleDto in the database -> Start
    				roleDto.setActivateFlag(true);			// At time of save role will be active
	    			roleService.saveRole(roleDto);
	    			model.addAttribute("successMsg", "createRoleSuccessMsg");
	    			model.addAttribute("action", "view");
	    			model.addAttribute("readOnly", true);
	    			//Save roleDto in the database -> End
    			}
    		}
    	}
    	catch(Exception e)
    	{
    		LOGGER.error("Error description", e);
			throw e;
    	}
    	return "role";
    }
    
    @RequestMapping(params = "updateRole", method = RequestMethod.POST)
    public String updateRole(@Valid RoleDto roleDto ,BindingResult result, ModelMap model) throws Exception
    {
    	try
    	{
    		if(result.hasErrors()) 
			{
    			model.addAttribute("action", "update");
    			model.addAttribute("readOnly", false);
			} 
    		else
    		{
    			//update roleDto in the database -> Start
    			boolean isRoleUpdated = roleService.updateRole(roleDto);
    			if(isRoleUpdated)
    			{
	    			model.addAttribute("successMsg", "updateRoleSuccessMsg");
	    			model.addAttribute("action", "view");
	    			model.addAttribute("readOnly", true);
    			}
    			else
    			{
    				model.addAttribute("errorMessages", "noChange");
    				model.addAttribute("action", "update");
        			model.addAttribute("readOnly", false);
    			}
    			//update roleDto in the database -> End
    		}
    	}
    	catch(Exception e)
    	{
    		LOGGER.error("Error description", e);
			throw e;
    	}
    	return "role";
    }
}
