package com.gargorg.Admin.service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.util.StringUtils;

public class CustomSecurityContextLogoutHandler implements LogoutHandler 
{
	@Override
	public void logout(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication) {
		
		Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) 
        {
            cookie.setMaxAge(0);
            cookie.setValue(null);
            String cookiePath = request.getContextPath();
            if (!StringUtils.hasLength(cookiePath)) {
				cookiePath = "/";
			}
            cookie.setPath(cookiePath);
            response.addCookie(cookie);
        }
	}
}