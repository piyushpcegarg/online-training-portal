/**
 * This Service will contains the functionality for loading logged in user information.
 */
package com.gargorg.Admin.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.gargorg.Admin.valueObject.LoginDetailsVO;


/**
 * @author piyush
 *
 */
@Component
public class LoginDetailsVOProviderServiceImpl implements LoginDetailsVOProviderService 
{
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginUserDetailsServiceImpl.class);
	
	//Method to get loggerd in user information from Spring Security -> Start
    @Override
	public LoginDetailsVO getLoginDetailsVO() 	
    {
    	LoginDetailsVO loginDetailsVO = null;
		try
		{
			//Logged in uer information
			loginDetailsVO = (LoginDetailsVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		catch(Exception e)
		{
			LOGGER.error("Error description", e);
		}
		return loginDetailsVO;
	}
    //Method to get loggerd in user information from Spring Security -> End
}


