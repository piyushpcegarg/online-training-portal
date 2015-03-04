/**
 * 
 */
package com.gargorg.Admin.Controller;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gargorg.Admin.service.PasswordService;

/**
 * @author piyush
 *
 */
@Controller
public class LoginController 
{
	@Autowired
	private PasswordService passwordService;
	@Autowired
	private HttpServletRequest request;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
	
	@RequestMapping({"/","/login"})
	public String showLoginPage() 
	{
        return "login";
    }
	
	@RequestMapping(value="/loginfailed/{error}", method = RequestMethod.GET)
	public String loginerror(@PathVariable String error, ModelMap model) throws Exception
	{
		try
		{
			String username = null , password = null;
			HttpSession session = request.getSession();
			if(error != null && error.equals("otpRequired"))
			{
				//Generate 4 digit One Time Password and send this to registered mobile no of user.
				//Update Org_User_Mst with generated OTP and enter validity of otpvalidity = currTime + otpValidityMinutes.
				username = (String)session.getAttribute("username");
				password = (String)session.getAttribute("password");
				boolean isOtpGenerated = passwordService.generateOtpForUser(username);
				model.addAttribute("OTP_REQUIRED", isOtpGenerated);
			}
			//Removing username and password from session -> Start
			session.removeAttribute("username");
			session.removeAttribute("password");
			session.removeAttribute("locale");
			//Removing username and password from session -> End
			model.addAttribute("username", username);
			model.addAttribute("password", password);
			//model.addAttribute("error", error);
		}
		catch(Exception e)
		{
			LOGGER.error("Error description", e);
			throw e;
		}
		return "login";
	}
 
	@RequestMapping(value="/logoutSuccessful", method = RequestMethod.GET)
	public String logout(ModelMap model) 
	{
		model.addAttribute("successLogoutMessage", "You have been logged out.");
		return "login";
	}
	
	@RequestMapping(value="/accessDenied", method = RequestMethod.GET)
	public String accessDenied(ModelMap model) throws Exception 
	{
		try
		{
			return "accessDenied";
		}
		catch(Exception e)
		{
			LOGGER.error("Error description", e);
			throw e;
		}
	}
}
