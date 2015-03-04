package com.gargorg.Admin.service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

public class CustomSecurityContextLogoutHandler extends SecurityContextLogoutHandler 
{
	public CustomSecurityContextLogoutHandler() {
		super();
	}

	@Override
	public void logout(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication) {
		
		super.logout(request, response, authentication);
		Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) 
        {
            cookie.setMaxAge(0);
            cookie.setValue(null);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
	}
}