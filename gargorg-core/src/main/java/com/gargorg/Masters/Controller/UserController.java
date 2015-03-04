/**
 * This will all the functionalities related to user.
 */
package com.gargorg.Masters.Controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gargorg.Masters.Validator.UserFormValidator;
import com.gargorg.Masters.dto.UserDto;
import com.gargorg.Masters.service.UserService;
import com.gargorg.Masters.valueObject.OrgUserMst;

/**
 * @author piyush
 *
 */
@Controller
@RequestMapping("/user")
public class UserController 
{
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    
	@Autowired  
    private UserService userService;
	@Autowired
	private UserFormValidator userFormValidator;
	@Autowired
	HttpServletRequest request;

    /** 
     * Get called when User main link is clicked from menus for loading all the users from database. 
     * displayuser.jsp page is opened , which shows all the users stored in the database with user name.
     * This page shows 3 buttons (Create , Update , Close)
     * <p>Expected HTTP GET and request '/user/allUsers'.</p>
     */
    @RequestMapping(value = "/allUsers", method = RequestMethod.GET)
    public String getAllUsers(ModelMap model) throws Exception
    {
    	try
    	{
    		List<UserDto> userList = userService.getUserList();
    		model.addAttribute("userList", userList);
    		model.addAttribute("userDto", new UserDto());
    		return "displayuser";
    	}
    	catch(Exception e)
    	{
    		LOGGER.error("Error description", e);
			throw e;
    	}
    }
    
    @RequestMapping(params = "createUserPage", method = RequestMethod.POST)
    public String getCreateUserPage(ModelMap model) throws Exception
    {
    	try
    	{
    		UserDto userDto = new UserDto();
    		userDto.setLstSecurityQuestionsLookUp(userService.getSecurityQuestions());		// Get all Security Questions to fill in the dropdown
    		userDto.setLstRoles(userService.getRoles());											// Get all Roles to fill in the dropdown
    		model.addAttribute("action", "create");
    		model.addAttribute("userDto", userDto);
    		return "user";
    	}
    	catch(Exception e)
    	{
    		LOGGER.error("Error description", e);
			throw e;
    	}
    }
    
    @RequestMapping(params = "updateUserPage", method = RequestMethod.POST)
    public String getUpdateUserPage(@RequestParam long userId , ModelMap model) throws Exception
    {
    	try
    	{
    		UserDto userDto = userService.getUserDtoFromId(userId);
    		model.addAttribute("userDto", userDto);
    		model.addAttribute("action", "update");
    		model.addAttribute("readOnly", false);
    		return "user";
    	}
    	catch(Exception e)
    	{
    		LOGGER.error("Error description", e);
			throw e;
    	}
    }
    
    @RequestMapping(params = "viewUserPage", method = RequestMethod.POST)
    public String getViewUserPage(@RequestParam long userId , ModelMap model) throws Exception
    {
    	try
    	{
    		UserDto userDto = userService.getUserDtoFromId(userId);
    		model.addAttribute("userDto", userDto);
    		model.addAttribute("action", "view");
    		model.addAttribute("readOnly", true);
    		return "user";
    	}
    	catch(Exception e)
    	{
    		LOGGER.error("Error description", e);
			throw e;
    	}
    }
    
    @RequestMapping(params = "saveUser", method = RequestMethod.POST)
    public String saveUser(@Valid UserDto userDto ,BindingResult result,ModelMap model) throws Exception
    {
    	try
    	{
    		userFormValidator.validate(userDto, result);
    		if(result.hasErrors())
			{
    			model.addAttribute("action", "create");
    			model.addAttribute("readOnly", false);
			} 
    		else
    		{
    			//Save userDto in the database -> Start
    			userDto.setActivateFlag(true);			// At time of save user will be active
    			userDto.setEncodedImageString(new String(Base64.encodeBase64(userDto.getUserImage().getBytes())));		// Convert bytes into encoded string to show image on successful save.
    			userService.saveUser(userDto);
    			model.addAttribute("successMsg", "createUserSuccessMsg");
    			model.addAttribute("action", "view");
    			model.addAttribute("readOnly", true);
    			//Save userDto in the database -> End
    		}
    	}
    	catch(Exception e)
    	{
    		LOGGER.error("Error description", e);
			throw e;
    	}
    	return "user";
    }
    
    @RequestMapping(params = "updateUser", method = RequestMethod.POST)
    public String updateUser(@Valid UserDto userDto ,BindingResult result, ModelMap model) throws Exception
    {
    	try
    	{
    		userFormValidator.validate(userDto, result);
    		if(result.hasErrors()) 
			{
    			model.addAttribute("action", "update");
    			model.addAttribute("readOnly", false);
			} 
    		else
    		{
    			//update userDto in the database -> Start
    			boolean isUserUpdated = userService.updateUser(userDto);
    			if(isUserUpdated)
    			{
	    			model.addAttribute("successMsg", "updateUserSuccessMsg");
	    			model.addAttribute("action", "view");
	    			model.addAttribute("readOnly", true);
    			}
    			else
    			{
    				model.addAttribute("errorMessages", "noChange");
    				model.addAttribute("action", "update");
        			model.addAttribute("readOnly", false);
    			}
    			//update userDto in the database -> End
    			userDto = userService.getUserDtoFromId(userDto.getUserId());
    			model.addAttribute("userDto", userDto);
    		}
    	}
    	catch(Exception e)
    	{
    		LOGGER.error("Error description", e);
			throw e;
    	}
    	return "user";
    }
    
    @RequestMapping(value = "/checkUsernameAvailability", method = RequestMethod.GET)
    public @ResponseBody boolean checkUsernameAvailability() throws Exception
    {
    	boolean isAvailable = false;
    	try
    	{
    		
    		String username = request.getParameter("username");
    		if(username != null && !username.equals(""))
			{
				OrgUserMst user = userService.getUserFromUsername(username);
				if(user == null)
				{
					isAvailable = true;
				}
			}
    	}
    	catch(Exception e)
    	{
    		LOGGER.error("Error description", e);
			throw e;
    	}
    	return isAvailable;
    }
    
    @RequestMapping(value = "/checkMobilenoAvailability", method = RequestMethod.GET)
    public @ResponseBody boolean checkMobilenoAvailability() throws Exception
    {
    	boolean isAvailable = false;
    	try
    	{
    		
    		String mobileNo = request.getParameter("mobileNo");
    		if(mobileNo != null && !mobileNo.equals(""))
			{
				OrgUserMst user = userService.getUserFromMobileno(mobileNo);
				if(user == null)
				{
					isAvailable = true;
				}
			}
    	}
    	catch(Exception e)
    	{
    		LOGGER.error("Error description", e);
			throw e;
    	}
    	return isAvailable;
    }
}