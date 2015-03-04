/**
 * This Service will contains the functionality for loading logged in user information.
 */
package com.gargorg.Admin.service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gargorg.Admin.dao.UserDetailsDao;
import com.gargorg.Admin.valueObject.CustomUser;
import com.gargorg.Masters.valueObject.OrgUserMst;
import com.gargorg.common.Utils.CommonUtility;


/**
 * @author piyush
 *
 */
@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService 
{
	@Autowired
	private UserDetailsDao userDetailsDAO;
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private CommonUtility commonUtility;
	@Value("${invalidLoginAttempts}") 
	private long invalidLoginAttempts;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
	
	//Method to get user information from database -> Start
	@Override
    public UserDetails loadUserByUsername(String username)
    		throws UsernameNotFoundException, DataAccessException	
    {
		CustomUser customUser = null;
		OrgUserMst user = null;
		String strUserOtp = request.getParameter("userOtp");
		String userPassword = request.getParameter("password");
		if((username != null && username.equals("")) || (userPassword != null && userPassword.equals("")) || (strUserOtp != null && strUserOtp.equals("")) )
		{
			throw new UsernameNotFoundException("Either Username or password or OTP found blank.");
		}
		else
		{
			user = userDetailsDAO.findByUsername(username);
		}
		
		 if(user == null) 
	     {
	         throw new UsernameNotFoundException("user not found");
	     }
		
		String locale = request.getParameter("locale");
		Long userOtp = null;
		if(strUserOtp != null && !strUserOtp.equals(""))
		{
			userOtp = Long.valueOf(strUserOtp);
		}
		
        // Set username and password in session for auto population on login.jsp if otpRequiredException is thrown -> Start
     	request.getSession().setAttribute("username", username);
     	request.getSession().setAttribute("password", userPassword);
     	request.getSession().setAttribute("locale", locale);
     	// Set username and password in session for auto population on login.jsp if otpRequiredException is thrown -> End
        
        boolean accountNonLocked = true;
        boolean enabled = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		
		Date currDate = commonUtility.getCurrentDateFromDB();
		if((user.getUnlockTime() != null && currDate != null && user.getUnlockTime().after(currDate)) && (user.getInvalidLoginCnt() == invalidLoginAttempts))
		{
			accountNonLocked = false;
		}
		if(!user.isActivateFlag())
		{
			enabled = false;
		}
		if((user.getFirstlogin() != null && user.getFirstlogin().equals("Y")) || (user.getPwdchangedDate() != null && currDate != null && user.getPwdchangedDate().before(currDate)))
		{
			credentialsNonExpired = false;
		}
		
        // Blank authorities created to avoid null pointer exception -> Start
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        // Blank authorities created to avoid null pointer exception -> End
        
		customUser = new CustomUser(user.getUserName() , user.getPassword() ,enabled,
				accountNonExpired, credentialsNonExpired, accountNonLocked, authorities ,locale ,userOtp , user);
		
		return customUser;
	}
	//Method to get user information from database -> End
}
