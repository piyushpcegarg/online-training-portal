/**
 * 
 */
package com.gargorg.Admin.Controller;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gargorg.Admin.dto.CmnLookupMstDto;
import com.gargorg.Admin.service.SettingService;
import com.gargorg.Masters.dto.UserDto;
import com.gargorg.Masters.valueObject.OrgUserMst;


/**
 * @author piyush
 *
 */
@Controller
public class SettingController 
{
	@Autowired
	private SettingService settingService;
	@Autowired
	private HttpServletRequest request;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SettingController.class);
    
	@RequestMapping(value = "/resetPassword", method = RequestMethod.GET)  
    public String displayResetPasswordPage(ModelMap model) throws Exception
    {
		try
		{
			model.addAttribute("userDto", new UserDto());
			return "resetPassword";
		}
		catch(Exception e)
		{
			LOGGER.error("Error description", e);
			throw e;
		}
    }
	
	
	@RequestMapping(value = "/resetPassword/reset", method = RequestMethod.POST)  
    public String resetPassword(ModelMap model) throws Exception
    {  
		try
		{
			boolean isValidUsername = true;
	    	String username = request.getParameter("userName");
			if(username.equals(""))
			{
				model.addAttribute("errorMessages", "username_required");
				isValidUsername = false;
			}else if(username.length() < 8)
			{
				model.addAttribute("errorMessages", "username_minlength");
				isValidUsername = false;
			}else if(username.length() > 20)
			{
				model.addAttribute("errorMessages", "username_maxlength");
				isValidUsername = false;
			}
			if(isValidUsername)
			{
				OrgUserMst orgUserMst = settingService.resetPassword(username);
				if(orgUserMst == null)
				{
					model.addAttribute("errorMessages", "invalidUser");
				}
				else
				{
					model.addAttribute("username", username);
					model.addAttribute("resetPasswordSuccessMessage", true);
				}
			}
			model.addAttribute("userDto", new UserDto());
		}
		catch(Exception e)
		{
			LOGGER.error("Error description", e);
			throw e;
		}
  	    return "resetPassword";
    }
	
	@RequestMapping(value = "/clearCache", method = RequestMethod.GET)  
    public String displayCachePage(ModelMap model) throws Exception
    {  
		try
		{
			model.addAttribute("clearCache", "clearCache");	//To only add commandName in spring form , this attribute is added. This has of no use. 
			return "clearCache";
		}
		catch(Exception e)
		{
			LOGGER.error("Error description", e);
			throw e;
		}
    }
	
	@CacheEvict(value = "opgCache", allEntries=true)
	@RequestMapping(value = "/clearCache/clear", method = RequestMethod.POST)  
    public String clearSystemCache(ModelMap model) throws Exception
    {
		try
		{
			model.addAttribute("clearCache", "clearCache");	//To only add commandName in spring form , this attribute is added. This has of no use.
			model.addAttribute("clearCacheSuccessMessage", true);
	    }
		catch(Exception e)
		{
			LOGGER.error("Error description", e);
			throw e;
		}
	  	return "clearCache";
    }
	
	@RequestMapping(value = "/securitySetting", method = RequestMethod.GET)  
    public String displaySecuritySettingPage(ModelMap model) throws Exception
    {
		try
		{
			model.addAttribute("lstSecuritySettings", settingService.getSecuritySettings());
		}
		catch(Exception e)
		{
			LOGGER.error("Error description", e);
			throw e;
		}
  	    return "securitySetting";
    }
	
	@RequestMapping(value = "/securitySetting/update", method = RequestMethod.POST)  
    public String updateSecuritySetting(ModelMap model) throws Exception
    {
		try
		{
			String[] selectedChkboxArray = request.getParameterValues("chkbox");
			// Iterate selectedChkboxArray and determine whether user changed the values - > Start
			
			int selectedChkboxArrayLength = selectedChkboxArray.length;
			long lookupCode = 0L;
			boolean activateFlagFromDb = false;
			boolean activateFlagFromScreen = false;
			List<CmnLookupMstDto> lstCmnLookupMst = new ArrayList<CmnLookupMstDto>();
			CmnLookupMstDto cmnLookupMstDto = null;
			for(int loopCount = 0 ; loopCount < selectedChkboxArrayLength ; loopCount++)
			{
				lookupCode = Long.valueOf(selectedChkboxArray[loopCount].split("~")[0]).longValue() ;
				activateFlagFromDb = Boolean.parseBoolean(selectedChkboxArray[loopCount].split("~")[1]);
				activateFlagFromScreen = Boolean.parseBoolean(request.getParameter("lookupCode_"+lookupCode));
				if(activateFlagFromDb != activateFlagFromScreen)
				{
					cmnLookupMstDto = new CmnLookupMstDto();
					cmnLookupMstDto.setLookupCode(lookupCode);
					cmnLookupMstDto.setActivateFlag(activateFlagFromScreen);
					lstCmnLookupMst.add(cmnLookupMstDto);
				}
			}
			// If user has change the value of some setting , then list will not empty. So update it in the database -> Start 
			if(lstCmnLookupMst != null && !lstCmnLookupMst.isEmpty())
			{
				settingService.updateSecuritySettings(lstCmnLookupMst);
				model.addAttribute("settingSuccessMessage", true);
			}
			else
			{
				model.addAttribute("settingErrorMessage", true);
			}
			// If user has change the value of some setting , then list will not empty. So update it in the database -> End
			// Iterate selectedChkboxArray and determine whether user changed the values - > End
			model.addAttribute("lstSecuritySettings", settingService.getSecuritySettings());
		}
		catch(Exception e)
		{
			LOGGER.error("Error description", e);
			throw e;
		}
  	    return "securitySetting";
    }
	
	@RequestMapping(value = "/applicationLogs", method = RequestMethod.GET)  
    public String displayApplicationLogsPage(ModelMap model) throws Exception
    {
		try
		{
		}
		catch(Exception e)
		{
			LOGGER.error("Error description", e);
			throw e;
		}
		//TODO change this
  	    return "home";
    }
}