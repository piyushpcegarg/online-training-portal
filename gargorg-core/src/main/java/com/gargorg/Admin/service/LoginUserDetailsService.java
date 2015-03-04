/**
 * This Service will contains the functionality for loading logged in user information.
 */
package com.gargorg.Admin.service;

import com.gargorg.Admin.valueObject.UserElements;



/**
 * @author piyush
 *
 */
public interface LoginUserDetailsService 
{
	public UserElements getMappedUserElements() throws Exception;
}

