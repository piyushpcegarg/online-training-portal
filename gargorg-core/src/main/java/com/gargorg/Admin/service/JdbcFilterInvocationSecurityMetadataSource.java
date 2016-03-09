package com.gargorg.Admin.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import com.gargorg.Admin.dao.AccessControlDao;

public class JdbcFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
	
	@Autowired
	private AccessControlDao accessControlDao;
	@Value("${anonymousAccessElements}")
	private String anonymousAccessElements;

	private static final Logger LOGGER = LoggerFactory.getLogger(JdbcFilterInvocationSecurityMetadataSource.class);
	
    @Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
    	
    	List<String> roles = new ArrayList<String>();
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
			// Skip all the requests which are configured for anonymous user - start
			boolean isAnonymousRequest = false;
			String[] anonymousAccessElementsArray = anonymousAccessElements.split(",");
			List<String> anonymousAccessElementsList = Arrays.asList(anonymousAccessElementsArray);
			if(anonymousAccessElementsList.contains(requestUrl)) {
				isAnonymousRequest = true;
				roles.add("ROLE_ANONYMOUS");
			}
			// Skip all the requests which are configured for anonymous user - end
			if((!requestUrl.equals("/") && !isAnonymousRequest) || requestUrl.equals("/errorPage"))
			{
				 
				roles.addAll(accessControlDao.getAllowedRoles(requestUrl));
			}
    	}
    	catch (Exception e) 
		{
			LOGGER.error("Error description", e);
		}
    	return convertList(roles);
    }

    @Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
    	return null;
    }

    @Override
	public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
    
    private List<ConfigAttribute> convertList(List<String> roles) {
    	List<ConfigAttribute> rolesList = new ArrayList<ConfigAttribute>(roles.size());
    	for(String role : roles) {
    		rolesList.add(new SecurityConfig(role.trim()));
    	}
    	return rolesList;
    }
}