package com.gargorg.Admin.service;

import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.util.StringUtils;

import com.gargorg.Admin.valueObject.CustomUser;
import com.gargorg.common.Utils.CommonUtility;

public class CustomPreAuthenticationChecks implements UserDetailsChecker , MessageSourceAware 
{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private CommonUtility commonUtility;
	
	protected MessageSourceAccessor messages;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomPreAuthenticationChecks.class);
	
	public CustomPreAuthenticationChecks()
	{
		this.messages = SpringSecurityMessageSource.getAccessor();
	}
	
	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messages = new MessageSourceAccessor(messageSource);
	}
	
	@Override
	public void check(UserDetails user) 
	{
		// Determine locale selected by User -> Start
		String newLocale = request.getParameter("locale");
        Locale locale = StringUtils.parseLocaleString(newLocale.toLowerCase());
        // Determine locale selected by User -> End
        
		CustomUser customUser = (CustomUser)user;
		if (!(user.isAccountNonLocked())) 
		{
			LOGGER.debug("User account is locked");
			
			Date currDate = commonUtility.getCurrentDateFromDB();
			TimeUnit timeUnit = TimeUnit.MINUTES;
			long diffInMillies = customUser.getUnlockTime().getTime() - currDate.getTime();
			long diffInMinutes = timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
			String lockedExceptionMessage = messages.getMessage("AbstractUserDetailsAuthenticationProvider.locked1","Your account is locked, Try after ",locale)+diffInMinutes+
			messages.getMessage("AbstractUserDetailsAuthenticationProvider.locked2"," minutes.",locale);

			throw new LockedException(lockedExceptionMessage);
		}

		if (!(user.isEnabled())) 
		{
			LOGGER.debug("User account is disabled");

			throw new DisabledException(
					messages.getMessage(
									"AbstractUserDetailsAuthenticationProvider.disabled",
									"User is disabled",locale));
		}

		if (!(user.isAccountNonExpired())) 
		{
			LOGGER.debug("User account is expired");

			throw new AccountExpiredException(
					messages.getMessage(
									"AbstractUserDetailsAuthenticationProvider.expired",
									"User account has expired",locale));
		}
	}
}