/**
 * 
 */
package com.gargorg.Admin.Controller;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gargorg.Admin.Validator.ChangePasswordValidator;
import com.gargorg.Admin.Validator.ForgotPasswordValidator;
import com.gargorg.Admin.dto.ChangePasswordDto;
import com.gargorg.Admin.dto.ForgotPasswordDto;
import com.gargorg.Admin.service.LoginDetailsVOProviderService;
import com.gargorg.Admin.service.PasswordService;
import com.gargorg.Admin.valueObject.LoginDetailsVO;
import com.gargorg.Masters.service.UserService;
import com.gargorg.Masters.valueObject.OrgUserMst;

/**
 * @author piyush
 *
 */
@Controller
public class PasswordController 
{
	private static final Logger LOGGER = LoggerFactory.getLogger(PasswordController.class);
	
	@Autowired  
    private PasswordService passwordService;
	@Autowired  
    private UserService userService;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private LoginDetailsVOProviderService loginDetailsVOProviderService;
	@Autowired
	private ChangePasswordValidator changePasswordValidator;
	@Autowired
	private ForgotPasswordValidator forgotPasswordValidator;
	@Autowired
	private LocaleResolver localeResolver;
	
	// The following method invokes when user login in the system first time or his password expires.
	@RequestMapping(value={"/changePassword/{error}"}, method = RequestMethod.GET)
	public String loadChangePasswordPage(@PathVariable String error, ModelMap model) throws Exception
	{
		try
		{
			String username = null;
			String locale = null;
			ChangePasswordDto changePasswordDto = null;
			HttpSession session = request.getSession();
			username = (String)session.getAttribute("username");
			locale = (String)session.getAttribute("locale");
			//Removing username and password from session -> Start
			session.removeAttribute("username");
			session.removeAttribute("password");
			session.removeAttribute("locale");
			//Removing username and password from session -> End
			changePasswordDto = passwordService.getSecurityQuestionAndOtp(username,locale);
			changePasswordDto.setUsername(username);
			changePasswordDto.setLocale(locale);
			changePasswordDto.setBeforeLogin(true);
			model.addAttribute("beforeLogin", true);		// This decides whether change password page loaded before login or after login
			model.addAttribute("error", error);
			model.addAttribute("changePasswordDto", changePasswordDto);
		}
		catch(Exception e)
		{
			LOGGER.error("Error description", e);
			throw e;
		}
		return "changePassword";
	}
	
	// The following method invokes when user changes his password after login -> Start
	@RequestMapping(value="/passwordChange", method = RequestMethod.GET)
	public String getChangePasswordPage(ModelMap model) throws Exception 
	{
		try
		{
			LoginDetailsVO loginDetailsVO = loginDetailsVOProviderService.getLoginDetailsVO();	//Get LoginDetailsVO from Security Context holder
			ChangePasswordDto changePasswordDto = null;
			changePasswordDto = passwordService.getSecurityQuestionAndOtp(loginDetailsVO.getUsername() , loginDetailsVO.getLocale());
			changePasswordDto.setUsername(loginDetailsVO.getUsername());
			changePasswordDto.setLocale(loginDetailsVO.getLocale());
			model.addAttribute("beforeLogin", false);		// This decides whether change password page loaded before login or after login
			model.addAttribute("changePasswordDto", changePasswordDto);
		}
		catch(Exception e)
		{
			LOGGER.error("Error description", e);
			throw e;
		}
		return "changePassword";
	}
	// The following method invokes when user changes his password after login -> End
	
	// The following method is used to change the password before or after login -> Start
	@RequestMapping(value={"/changePassword/save","/passwordChange/save"}, method = RequestMethod.POST)
	public String saveNewPassword(@Valid ChangePasswordDto changePasswordDto ,BindingResult result, ModelMap model,RedirectAttributes redirectAttributes) throws Exception 
	{
		String redirectPage = null;
		try 
		{
			changePasswordValidator.validate(changePasswordDto, result);
			if (result.hasErrors()) 
			{
				redirectPage = "changePassword";
			} 
			else 
			{  //Check custom validation
				Map<String,Object> resultMap = passwordService.changePassword(changePasswordDto);
				if((Boolean)resultMap.get("isPasswordChangedSuccessfully"))
				{
					// Redirect to logout page URL so session can be invalidated after successful change of password - start
					redirectAttributes.addFlashAttribute("changePasswordSuccessMsg", true);
					redirectPage = "redirect:/logout";
					// Redirect to logout page URL so session can be invalidated after successful change of password - end
				}
				else
				{
					model.addAttribute("errorMessages",resultMap.get("errorMessages"));
					redirectPage = "changePassword";
				}
			} 
			if(redirectPage != null && redirectPage.equals("changePassword"))
			{
				model.addAttribute("beforeLogin", changePasswordDto.isBeforeLogin());		// This decides whether change password page loaded before login or after login
			}
		}
		catch(Exception e)
		{
			LOGGER.error("Error description", e);
			throw e;
		}
		return redirectPage;
	}
	// The following method is used to change the password before or after login -> End
	
	// The following method invokes when user forgot his password before login -> Start
	@RequestMapping(value="/forgotPassword", method = RequestMethod.GET)
	public String getForgotPasswordPage(ModelMap model) throws Exception 
	{
		try
		{
			model.addAttribute("displayForgotPasswordForm", false);
		}
		catch(Exception e)
		{
			LOGGER.error("Error description", e);
			throw e;
		}
		return "forgotPassword";
	}
	// The following method invokes when user forgot his password before login -> End
	
	@RequestMapping(value = "/forgotPassword/checkUsernameAvailability", method = RequestMethod.POST)
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
	
	@RequestMapping(value={"/forgotPassword/search"}, method = RequestMethod.POST)
	public String loadForgotPasswordPage(ModelMap model , HttpServletResponse response) throws Exception
	{
		try
		{
			String username = request.getParameter("searchUsername");
			String locale = request.getParameter("locale");;
			Locale providedLocale = StringUtils.parseLocaleString(locale.toLowerCase());
		    localeResolver.setLocale(request, response, providedLocale);
			ForgotPasswordDto forgotPasswordDto = new ForgotPasswordDto();
			ChangePasswordDto changePasswordDto = passwordService.getSecurityQuestionAndOtp(username,locale);
			BeanUtils.copyProperties(changePasswordDto, forgotPasswordDto);
			forgotPasswordDto.setUsername(username);
			forgotPasswordDto.setLocale(locale);
			model.addAttribute("displayForgotPasswordForm", true);
			model.addAttribute("forgotPasswordDto", forgotPasswordDto);
		}
		catch(Exception e)
		{
			LOGGER.error("Error description", e);
			throw e;
		}
		return "forgotPassword";
	}
	
	// The following method is used to change the password when user clicks forgot password link -> Start
	@RequestMapping(value={"/forgotPassword/save"}, method = RequestMethod.POST)
	public String saveForgotPassword(@Valid ForgotPasswordDto forgotPasswordDto ,BindingResult result, ModelMap model) throws Exception 
	{
		String redirectPage = null;
		try 
		{
			forgotPasswordValidator.validate(forgotPasswordDto, result);
			if (result.hasErrors()) 
			{
				redirectPage = "forgotPassword";
			} 
			else 
			{  //Check custom validation
				Map<String,Object> resultMap = passwordService.forgotPassword(forgotPasswordDto);
				if((Boolean)resultMap.get("isPasswordChangedSuccessfully"))
				{
					model.addAttribute("changePasswordSuccessMsg", true);
					redirectPage = "login";
				}
				else
				{
					model.addAttribute("errorMessages",resultMap.get("errorMessages"));
					redirectPage = "forgotPassword";
				}
			} 
			if(redirectPage != null && redirectPage.equals("forgotPassword"))
			{
				model.addAttribute("displayForgotPasswordForm", true);
			}
		}
		catch(Exception e)
		{
			LOGGER.error("Error description", e);
			throw e;
		}
		return redirectPage;
	}
	// The following method is used to change the password when user clicks forgot password link -> End
}