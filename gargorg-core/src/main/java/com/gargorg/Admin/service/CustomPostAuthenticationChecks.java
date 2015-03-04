package com.gargorg.Admin.service;

import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.gargorg.Admin.Exception.InvalidOtpException;
import com.gargorg.Admin.Exception.OtpRequiredException;
import com.gargorg.Admin.dao.CmnLookUpDao;
import com.gargorg.Admin.dto.CmnLookupMstDto;
import com.gargorg.Admin.valueObject.CustomUser;
import com.gargorg.common.Utils.CommonFunctions;
import com.gargorg.common.Utils.CommonUtility;

@Service
@Transactional
public class CustomPostAuthenticationChecks implements UserDetailsChecker , MessageSourceAware 
{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private CmnLookUpDao cmnLookUpDao;
	@Autowired
	private CommonUtility commonUtility;
	@Value("${otpRequired}") 
	private String otpRequired;
	
	protected MessageSourceAccessor messages;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomPostAuthenticationChecks.class);
	
	public CustomPostAuthenticationChecks()
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
        
        if (!(user.isCredentialsNonExpired())) 
		{
			LOGGER.debug("User account credentials have expired");

			throw new CredentialsExpiredException(
					messages.getMessage(
									"AbstractUserDetailsAuthenticationProvider.credentialsExpired",
									"Your credentials are expired, kindly change your password.",locale));
		}
        
		CustomUser customUser = (CustomUser)user;
		if ((customUser.isOtpEnabled()))
		{
			CmnLookupMstDto cmnLookupMstDto = null;
			try
			{
				cmnLookupMstDto = cmnLookUpDao.getLookUpByLookUpName(otpRequired, CommonFunctions.getLangIdByLocale(customUser.getLocale()));
			}
			catch(Exception e)
			{
				LOGGER.error("Error description", e);
			}
			if(cmnLookupMstDto != null && cmnLookupMstDto.isActivateFlag())
			{
				if(customUser != null && customUser.getUserOtp() == null)
				{
					throw new OtpRequiredException(
							messages.getMessage(
									"AbstractUserDetailsAuthenticationProvider.otpRequired",
									"Kindly enter OTP send to your registered mobile No.",locale)+customUser.getRegMobileNo().toString().replaceFirst(".{5}", "XXXXX"));
				}
				else if(customUser != null && customUser.getUserOtp() != null)
				{
					Date currDate = commonUtility.getCurrentDateFromDB();
					if(customUser.getUserOtp().longValue() != customUser.getOtp().longValue())
					{
						throw new InvalidOtpException(
								messages.getMessage(
										"AbstractUserDetailsAuthenticationProvider.invalidOtp1",
										"You entered wrong One Time Password.Kindly login again.",locale));
					}
					else if(customUser.getOtpValidity() != null && currDate != null && currDate.after(customUser.getOtpValidity()))
					{
						throw new InvalidOtpException(
								messages.getMessage(
										"AbstractUserDetailsAuthenticationProvider.invalidOtp2",
										"Your OTP is expired.Kindly login again.",locale));
					}
				}
			}
		}
	}
}