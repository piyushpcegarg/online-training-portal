package com.gargorg.Admin.service;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import com.gargorg.Admin.dao.AccessControlDao;

public class JdbcFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
	
	@Autowired
	private AccessControlDao accessControlDao;

	private static final Logger LOGGER = LoggerFactory.getLogger(JdbcFilterInvocationSecurityMetadataSource.class);
	
    @Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
    	
    	String[] roles = new String[0];
    	try
    	{
	        FilterInvocation fi = (FilterInvocation) object;
	        
	        String url = fi.getRequestUrl();
	        // Instead of hard coding the roles lookup the roles from the database using the url
	        // Do not forget to add caching of the lookup   
	        String requestUrl = null;
			int indexOfForwardSlash = StringUtils.ordinalIndexOf(url , "/", 2);	// Find second occurrence of / character
			int indexOfQuestionMark = StringUtils.ordinalIndexOf(url , "?", 1);	// Find first occurrence of ? character
			if(indexOfForwardSlash != -1)
			{
				requestUrl = url.substring(0 , indexOfForwardSlash);
			}
			else if(indexOfQuestionMark != -1)
			{
				requestUrl = url.substring(0 , indexOfQuestionMark);
			}
			else
			{
				requestUrl = url;
			}
			if(!requestUrl.equals("/"))
			{
				roles = accessControlDao.getAllowedRoles(requestUrl);
			}
    	}
    	catch (Exception e) 
		{
			LOGGER.error("Error description", e);
		}
    	return SecurityConfig.createList(roles);
    }

    @Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
	public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}