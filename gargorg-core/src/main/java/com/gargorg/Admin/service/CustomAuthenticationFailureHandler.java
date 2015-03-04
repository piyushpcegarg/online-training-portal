package com.gargorg.Admin.service;

import java.io.IOException;
import java.util.Date;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.ExceptionMappingAuthenticationFailureHandler;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;

import com.gargorg.Admin.dao.UserDetailsDao;
import com.gargorg.Admin.valueObject.CustomUser;
import com.gargorg.Masters.valueObject.OrgUserMst;
import com.gargorg.common.Utils.CommonFunctions;
import com.gargorg.common.Utils.CommonUtility;

@Transactional
public class CustomAuthenticationFailureHandler extends ExceptionMappingAuthenticationFailureHandler
{
	@Autowired
	private UserDetailsDao userDetailsDAO;
	
	@Autowired
	private CommonUtility commonUtility;
	
	private LocaleResolver localeResolver;
	public LocaleResolver getLocaleResolver() {
		return localeResolver;
	}
	public void setLocaleResolver(LocaleResolver localeResolver) {
		this.localeResolver = localeResolver;
	}
	
	@Value("${invalidLoginAttempts}") 
	private Long invalidLoginAttempts;
	@Value("${accountUnlockTime}") 
	private Long accountUnlockTime;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomAuthenticationFailureHandler.class);

	@Override
	public void onAuthenticationFailure(HttpServletRequest request,HttpServletResponse response, AuthenticationException exception)
	throws IOException, ServletException 
	{
		handleAuthenticationException(request , response , exception);
		saveException(request, exception);
		// Save user selected Locale in session after authentication - > Start 
		setLocale(request, response);
		// Save user selected Locale in session after authentication - > End
				
		super.onAuthenticationFailure(request,response,exception);
	}
	
	private void handleAuthenticationException(HttpServletRequest request,HttpServletResponse response, AuthenticationException exception)
	{
		if (exception instanceof BadCredentialsException)
		{
			CustomUser customUser = (CustomUser)exception.getExtraInformation();
			try
			{
				if(customUser != null)
				{
					if(customUser.getUsername() != null)
					{
						OrgUserMst orgUserMst = userDetailsDAO.findByUsername(customUser.getUsername());
						if(orgUserMst != null)
							
			        	{
				        	Long invalidLoginCnt = orgUserMst.getInvalidLoginCnt();  
				        	Date unlockTime = orgUserMst.getUnlockTime();
				            // update the failed login count -> Start 
				        	if((invalidLoginCnt < (invalidLoginAttempts - 1L)) && unlockTime == null )
				        	{
				        		orgUserMst.setInvalidLoginCnt(++invalidLoginCnt);
				        		userDetailsDAO.updateUser(orgUserMst);  
				        	}
				        	else if((invalidLoginCnt == (invalidLoginAttempts - 1L)) && unlockTime == null )
				        	{
				        		Date currDate = commonUtility.getCurrentDateFromDB();
				        		unlockTime = CommonFunctions.addMinutesInDate(currDate , accountUnlockTime.intValue());
				        		orgUserMst.setInvalidLoginCnt(++invalidLoginCnt);
				        		orgUserMst.setUnlockTime(unlockTime);
				        		userDetailsDAO.updateUser(orgUserMst);
				        	}
				        	else if((invalidLoginCnt == invalidLoginAttempts) && unlockTime != null )
				        	{
				        		Date currDate = commonUtility.getCurrentDateFromDB();
				        		unlockTime = CommonFunctions.addMinutesInDate(currDate , accountUnlockTime.intValue());
				        		orgUserMst.setUnlockTime(unlockTime);
				        		userDetailsDAO.updateUser(orgUserMst);
				        	}
				        	else
				        	{
				        		// Do Nothing
				        	}
				        	// update the failed login count -> End
			        	}
					}
				}
			}
			catch(Exception e)
			{
				LOGGER.error("Error description", e);
			}
			exception.clearExtraInformation();
		}
	}
	
	protected void setLocale(HttpServletRequest request, HttpServletResponse response) 
	{
		String newLocale = request.getParameter("locale");
		if(newLocale == null)
		{
			newLocale = "en";	//if locale not found set english locale by default
		}
	    Locale providedLocale = StringUtils.parseLocaleString(newLocale.toLowerCase());
	    localeResolver.setLocale(request, response, providedLocale);
    }
}