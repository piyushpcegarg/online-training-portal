/**
 * This will all the functionalities related to myProfile.
 */
package com.gargorg.Admin.Controller;


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
import org.springframework.web.bind.annotation.ResponseBody;

import com.gargorg.Admin.service.LoginDetailsVOProviderService;
import com.gargorg.Admin.valueObject.LoginDetailsVO;
import com.gargorg.Masters.Validator.UserFormValidator;
import com.gargorg.Masters.dto.UserDto;
import com.gargorg.Masters.service.UserService;
import com.gargorg.Masters.valueObject.OrgUserMst;

/**
 * @author piyush
 *
 */
@Controller
@RequestMapping("/myProfile")
public class MyProfileController 
{
	private static final Logger LOGGER = LoggerFactory.getLogger(MyProfileController.class);
    
	@Autowired
	private LoginDetailsVOProviderService loginDetailsVOProviderService;
	@Autowired  
    private UserService userService;
	@Autowired
	private UserFormValidator userFormValidator;
	@Autowired
	HttpServletRequest request;
	
	@RequestMapping(method = RequestMethod.GET)
	public String showMyProfilePage(ModelMap model) throws Exception 
	{
		try
		{
			LoginDetailsVO loginDetailsVO = loginDetailsVOProviderService.getLoginDetailsVO();	//Get LoginDetailsVO from Security Context holder
			long userId = loginDetailsVO.getUserId();
			UserDto userDto = userService.getUserDtoFromId(userId);
    		model.addAttribute("userDto", userDto);
    		model.addAttribute("action", "myProfile");
    		model.addAttribute("readOnly", false);
    		return "myProfile";
		}
		catch(Exception e)
		{
			LOGGER.error("Error description", e);
			throw e;
		}
    }
	
    @RequestMapping(params = "updateMyProfile", method = RequestMethod.POST)
    public String updateMyProfile(@Valid UserDto userDto ,BindingResult result, ModelMap model) throws Exception
    {
    	try
    	{
    		userFormValidator.validate(userDto, result);
    		if(result.hasErrors()) 
			{
    			model.addAttribute("action", "myProfile");
    			model.addAttribute("readOnly", false);
			} 
    		else
    		{
    			//update userDto in the database -> Start
    			boolean isUserUpdated = userService.updateUser(userDto);
    			if(isUserUpdated)
    			{
    				userDto.setEncodedImageString(new String(Base64.encodeBase64(userDto.getUserImage().getBytes())));		// Convert bytes into encoded string to show image on successful update.
	    			model.addAttribute("successMsg", "updateUserSuccessMsg");
	    			model.addAttribute("action", "myProfile");
	    			model.addAttribute("readOnly", true);
    			}
    			else
    			{
    				model.addAttribute("errorMessages", "noChange");
    				model.addAttribute("action", "myProfile");
        			model.addAttribute("readOnly", false);
    			}
    			//update userDto in the database -> End
    		}
    	}
    	catch(Exception e)
    	{
    		LOGGER.error("Error description", e);
			throw e;
    	}
    	return "myProfile";
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