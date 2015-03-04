/**
 * 
 */
package com.gargorg.Admin.Controller;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gargorg.Admin.service.LoginDetailsVOProviderService;
import com.gargorg.Admin.service.LoginUserDetailsService;
import com.gargorg.Admin.valueObject.LoginDetailsVO;
import com.gargorg.Admin.valueObject.UserElements;
import com.gargorg.Masters.Validator.UserFormValidator;
import com.gargorg.Masters.service.UserService;
import com.gargorg.common.Utils.CommonFunctions;
import com.gargorg.common.Utils.CommonUtility;

/**
 * @author piyush
 *
 */
@Controller
public class HomeController 
{
	private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private LoginUserDetailsService loginUserDetailsService;
	@Autowired
	private LoginDetailsVOProviderService loginDetailsVOProviderService;
	@Autowired
	private UserService userService;
	@Autowired
	private UserFormValidator userFormValidator;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private CommonUtility commonUtility;
	
	@RequestMapping({"/authenticated"})
	public String showHomePageAfterLogin(ModelMap model) throws Exception 
	{
		UserElements navigationUserElements = null;
		try
		{
			LoginDetailsVO loginDetailsVO = loginDetailsVOProviderService.getLoginDetailsVO();	//Get LoginDetailsVO from Security Context holder
			Date currDate = commonUtility.getCurrentDateFromDB();
			long passwordExpiryDaysRemaining = CommonFunctions.daysBetween(currDate , loginDetailsVO.getPwdchangedDate());
			navigationUserElements = loginUserDetailsService.getMappedUserElements();
			if(passwordExpiryDaysRemaining < 5)
			{
				model.addAttribute("passwordExpiryDaysRemaining", passwordExpiryDaysRemaining);
			}
		}
		catch(Exception e)
		{
			LOGGER.error("Error description", e);
			throw e;
		}
		request.getSession().setAttribute("navigationUserElements", navigationUserElements);
		return "home";
    }
	
	@RequestMapping({"/home","/parent"})
	public String showHomePage(ModelMap model) throws Exception 
	{
		try
		{
			return "home";
		}
		catch(Exception e)
		{
			LOGGER.error("Error description", e);
			throw e;
		}
    }
	
	@RequestMapping({"/errorPage"})
	public String showErrorPage(ModelMap model) 
	{
        return "error";
    }
	
}