package com.gargorg.Admin.dao;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.gargorg.Admin.valueObject.CustomUser;

public class CustomDaoAuthenticationProvider extends DaoAuthenticationProvider
{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Value("${invalidLoginAttempts}") 
	private long invalidLoginAttempts;
	
	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		Assert.isInstanceOf(
				UsernamePasswordAuthenticationToken.class,
				authentication,
				this.messages
						.getMessage(
								"AbstractUserDetailsAuthenticationProvider.onlySupports",
								"Only UsernamePasswordAuthenticationToken is supported"));
		// Determine locale selected by User -> Start
		String newLocale = request.getParameter("locale");
        Locale locale = StringUtils.parseLocaleString(newLocale.toLowerCase());
        // Determine locale selected by User -> End
		        
		String username = (authentication.getPrincipal() == null) ? "NONE_PROVIDED"
				: authentication.getName();

		boolean cacheWasUsed = true;
		UserDetails user = super.getUserCache().getUserFromCache(username);

		if (user == null) {
			cacheWasUsed = false;
			try {
				user = super.retrieveUser(username,
						(UsernamePasswordAuthenticationToken) authentication);
			} catch (UsernameNotFoundException notFound) {
				this.logger.debug("User '" + username + "' not found");

				if (this.hideUserNotFoundExceptions) {
					throw new BadCredentialsException(
							this.messages
									.getMessage(
											"AbstractUserDetailsAuthenticationProvider.badCredentials",
											"Bad credentials",locale));
				}

				throw notFound;
			}

			Assert.notNull(user,
					"retrieveUser returned null - a violation of the interface contract");
		}
		try {
			super.getPreAuthenticationChecks().check(user);
			additionalAuthenticationChecks(user,
					(UsernamePasswordAuthenticationToken) authentication);
		} catch (AuthenticationException exception) {
			if (cacheWasUsed) {
				cacheWasUsed = false;
				user = retrieveUser(username,
						(UsernamePasswordAuthenticationToken) authentication);
				super.getPreAuthenticationChecks().check(user);
				additionalAuthenticationChecks(user,
						(UsernamePasswordAuthenticationToken) authentication);
			} else {
				throw exception;
			}
		}

		super.getPostAuthenticationChecks().check(user);

		if (!(cacheWasUsed)) {
			super.getUserCache().putUserInCache(user);
		}

		Object principalToReturn = user;

		if (super.isForcePrincipalAsString()) {
			principalToReturn = user.getUsername();
		}

		return super.createSuccessAuthentication(principalToReturn, authentication,
				user);
	}
	
	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		
		// Determine locale selected by User -> Start
		String newLocale = request.getParameter("locale");
        Locale locale = StringUtils.parseLocaleString(newLocale.toLowerCase());
        // Determine locale selected by User -> End
		
		final Logger LOGGER = LoggerFactory.getLogger(CustomDaoAuthenticationProvider.class);
		
		LOGGER.debug("CustomDaoAuthenticationProvider:additionalAuthenticationChecks method Called.");

		if (authentication.getCredentials() == null) {
			LOGGER.debug("Authentication failed: no credentials provided");

			throw new BadCredentialsException(this.messages.getMessage(
					"AbstractUserDetailsAuthenticationProvider.badCredentials",
					"Bad credentials",locale));
		}

		String presentedPassword = authentication.getCredentials().toString();

		if (!(passwordEncoder.matches(presentedPassword, userDetails.getPassword()))) {
			LOGGER.debug("Authentication failed: password does not match stored value");
			
			CustomUser customUser = (CustomUser)userDetails;
			Long attemptsRemaining = null;
			
			attemptsRemaining = (invalidLoginAttempts == customUser.getInvalidLoginCnt()) ? 0L : (invalidLoginAttempts - customUser.getInvalidLoginCnt() -  1L);
			
			String badCredentialExceptionMessage =  
					messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials1","The username or password is incorrect. Account is locked on ",locale)
					+invalidLoginAttempts+
					messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials2"," incorrect attempts.",locale)
					+ attemptsRemaining.longValue() +
					messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials3"," attempts are remaining. Please click Forgot Password to retrieve your account details.",locale);
			
			throw new BadCredentialsException(badCredentialExceptionMessage);
		}
	}
}
