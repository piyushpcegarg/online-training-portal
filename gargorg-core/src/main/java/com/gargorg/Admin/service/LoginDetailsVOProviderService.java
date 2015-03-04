/**
 * This Service will contains the functionality for loading logged in user information.
 */
package com.gargorg.Admin.service;

import com.gargorg.Admin.valueObject.LoginDetailsVO;



/**
 * @author piyush
 *
 */
public interface LoginDetailsVOProviderService 
{
	public LoginDetailsVO getLoginDetailsVO();
}

