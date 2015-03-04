package com.gargorg.Admin.service;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;

import com.gargorg.Admin.dao.UserDetailsDao;
import com.gargorg.Admin.valueObject.CustomUser;
import com.gargorg.Admin.valueObject.LoginDetailsVO;
import com.gargorg.Masters.valueObject.OrgUserMst;

@Transactional
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler
{
	@Autowired
	private UserDetailsDao userDetailsDAO;
	
	private LocaleResolver localeResolver;
	public LocaleResolver getLocaleResolver() {
		return localeResolver;
	}

	public void setLocaleResolver(LocaleResolver localeResolver) {
		this.localeResolver = localeResolver;
	}
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomAuthenticationSuccessHandler.class);

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		
		LoginDetailsVO loginDetailsVO = generateLoginDetailsVO(authentication, request);
		// Put loginDetailsVO as principal in SecurityContextHolder -> Start
		authentication = new UsernamePasswordAuthenticationToken(loginDetailsVO ,loginDetailsVO.getPassword(),loginDetailsVO.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		// Put loginDetailsVO as principal in SecurityContextHolder -> End
		
		// Save user selected Locale in session after authentication - > Start 
		setLocale(authentication, request, response);
		// Save user selected Locale in session after authentication - > End
		
		//Removing username and password from session -> Start
		request.getSession().removeAttribute("username");
		request.getSession().removeAttribute("password");
		request.getSession().removeAttribute("locale");
		//Removing username and password from session -> End
		
		response.sendRedirect(request.getContextPath() + super.getDefaultTargetUrl());
	}

	private LoginDetailsVO generateLoginDetailsVO(Authentication authentication, HttpServletRequest request) 
	{
		CustomUser customUser = null;
		LoginDetailsVO loginDetailsVO = null;
		try 
		{
			customUser =  ((CustomUser) authentication.getPrincipal());
			if(customUser.getInvalidLoginCnt() != 0L || customUser.getUnlockTime() != null || customUser.getOtp() != null ||  customUser.getOtpValidity() != null)
			{
				OrgUserMst orgUserMst = userDetailsDAO.findByUsername(customUser.getUsername());
				if(orgUserMst != null)
	        	{
					orgUserMst.setInvalidLoginCnt(0L);
					orgUserMst.setUnlockTime(null);
					orgUserMst.setOtp(null);
					orgUserMst.setOtpValidity(null);
		        	userDetailsDAO.updateUser(orgUserMst);
	        	}
			}
			loginDetailsVO = userDetailsDAO.fillLoginDetailsVO(customUser.isActivateFlag(),customUser.getLocale(),customUser.getUserId());
		} 
		catch (Exception e) 
		{
			LOGGER.error("Error description", e);
		}
		return loginDetailsVO;
	}
	
	protected void setLocale(Authentication authentication, HttpServletRequest request, HttpServletResponse response) 
	{
        if (authentication != null) 
        {
        	LoginDetailsVO loginDetailsVO = (LoginDetailsVO)authentication.getPrincipal();
            if (loginDetailsVO != null) 
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
    }
}