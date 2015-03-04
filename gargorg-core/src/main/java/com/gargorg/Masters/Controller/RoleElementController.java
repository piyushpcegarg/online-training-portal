/**
 * This will all the functionalities related to roleElement.
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

import com.gargorg.Admin.service.LoginDetailsVOProviderService;
import com.gargorg.Masters.dto.OrgRoleMstDto;
import com.gargorg.Masters.dto.RoleDto;
import com.gargorg.Masters.dto.RoleElementDto;
import com.gargorg.Masters.service.RoleElementService;
import com.gargorg.Masters.service.RoleService;
import com.gargorg.Masters.valueObject.CmnLanguageMst;
import com.gargorg.common.constant.CommonConstants;

/**
 * @author piyush
 *
 */
@Controller
@RequestMapping("/roleElement")
public class RoleElementController 
{
	private static final Logger LOGGER = LoggerFactory.getLogger(RoleElementController.class);
    
	@Autowired  
    private RoleElementService roleElementService;  
	@Autowired  
    private RoleService roleService;  
	@Autowired
	private LoginDetailsVOProviderService loginDetailsVOProviderService;
	@Autowired
	private HttpServletRequest reqest;
      
    /** 
     * Get called when RoleElement main link is clicked from menus for loading all the roleElements from database. 
     * displayRoleElement.jsp page is opened , which shows all the roleElements stored in the database with roleElement name.
     * This page shows 3 buttons (Create , Update , Close)
     * <p>Expected HTTP GET and request '/roleElement/allRoleElements'.</p>
     */
	
	@RequestMapping(value = "/allRoleElements", method = RequestMethod.GET)
    public String getAllRoles(ModelMap model) throws Exception
    {
    	try
    	{
    		List<OrgRoleMstDto> roleList = roleService.getRoleList();
    		model.addAttribute("roleList", roleList);
    		model.addAttribute("orgRoleMstDto", new OrgRoleMstDto());
    		return "displayRoleElement";
    	}
    	catch(Exception e)
    	{
    		LOGGER.error("Error description", e);
			throw e;
    	}
    }
	
	@RequestMapping(params = "viewRoleElementPage", method = RequestMethod.POST)
    public String getViewRoleElementPage(@RequestParam long roleCode , ModelMap model) throws Exception
    {
    	try
    	{
			List<RoleElementDto> lstRoleElementDto = roleElementService.getRoleElementList(roleCode);
			// To fetch Role Name with the help of roleCode -> Start
			RoleDto roleDto = roleElementService.getRoleDtoFromCode(roleCode);
			CmnLanguageMst cmnLanguageMst = loginDetailsVOProviderService.getLoginDetailsVO().getLang();
			if(cmnLanguageMst.getLangId() == CommonConstants.ENGLISH)
			{
				model.addAttribute("roleName", roleDto.getEngRoleName());
			}
			else
			{
				model.addAttribute("roleName", roleDto.getHinRoleName());
			}
			// To fetch Role Name with the help of roleCode -> End
			model.addAttribute("lstRoleElementDto", lstRoleElementDto);
			model.addAttribute("roleDto", roleDto);
			model.addAttribute("action", "view");
    		model.addAttribute("readOnly", true);
    		return "roleElement";
    	}
    	catch(Exception e)
    	{
    		LOGGER.error("Error description", e);
			throw e;
    	}
    }
	
	@RequestMapping(params = "updateRoleElementPage", method = RequestMethod.POST)
    public String getUpdateRoleElementPage(@RequestParam long roleCode , ModelMap model) throws Exception
    {
    	try
    	{
			List<RoleElementDto> lstRoleElementDto = roleElementService.getRoleElementList(roleCode);
			// To fetch Role Name with the help of roleCode -> Start
			RoleDto roleDto = roleElementService.getRoleDtoFromCode(roleCode);
			CmnLanguageMst cmnLanguageMst = loginDetailsVOProviderService.getLoginDetailsVO().getLang();
			if(cmnLanguageMst.getLangId() == CommonConstants.ENGLISH)
			{
				model.addAttribute("roleName", roleDto.getEngRoleName());
			}
			else
			{
				model.addAttribute("roleName", roleDto.getHinRoleName());
			}
			// To fetch Role Name with the help of roleCode -> End
			model.addAttribute("lstRoleElementDto", lstRoleElementDto);
			model.addAttribute("roleDto", roleDto);
			model.addAttribute("action", "update");
    		model.addAttribute("readOnly", false);
    		return "roleElement";
    	}
    	catch(Exception e)
    	{
    		LOGGER.error("Error description", e);
			throw e;
    	}
    }
	
    @RequestMapping(params = "updateRoleElement", method = RequestMethod.POST)
    public String updateRoleElementsByRoleCode(RoleDto roleDto,ModelMap model) throws Exception
    {
    	try
    	{
    		long roleCode = roleDto.getRoleCode();
    		//To determine from which locale user is logged in -> start 
    		CmnLanguageMst cmnLanguageMst = loginDetailsVOProviderService.getLoginDetailsVO().getLang();
    		String roleName = null;
    		if(cmnLanguageMst.getLangId() == CommonConstants.ENGLISH)
			{
    			roleName = roleDto.getEngRoleName();
			}
			else
			{
				roleName = roleDto.getHinRoleName();
			}
    		//To determine from which locale user is logged in -> end
    		
    		List<Long> lstElementCodes = roleDto.getLstElementCodes();
    		
    		//update Role element Mapping in the database -> Start
			boolean isRoleElementUpdated = roleElementService.updateRoleElementList(roleCode , lstElementCodes);
			if(isRoleElementUpdated)
			{
    			model.addAttribute("successMsg", "updateRoleElementSuccessMsg");
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
			List<RoleElementDto> lstRoleElementDto = roleElementService.getRoleElementList(roleCode);
			roleDto = roleElementService.getRoleDtoFromCode(roleCode); 
			model.addAttribute("lstRoleElementDto", lstRoleElementDto);
			model.addAttribute("roleDto", roleDto);
			model.addAttribute("roleName", roleName);
    		return "roleElement";
    	}
    	catch(Exception e)
    	{
    		LOGGER.error("Error description", e);
			throw e;
    	}
    }
    
    
}
