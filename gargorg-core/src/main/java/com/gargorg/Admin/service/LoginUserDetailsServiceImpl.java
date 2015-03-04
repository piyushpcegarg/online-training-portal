/**
 * This Service will contains the functionality for loading logged in user information.
 */
package com.gargorg.Admin.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gargorg.Admin.dao.LoginUserDetailsDao;
import com.gargorg.Admin.dto.OrgElementMstDto;
import com.gargorg.Admin.valueObject.LoginDetailsVO;
import com.gargorg.Admin.valueObject.UserElements;
import com.gargorg.Masters.valueObject.OrgRoleMst;


/**
 * @author piyush
 *
 */
@Service
@Transactional
public class LoginUserDetailsServiceImpl implements LoginUserDetailsService 
{
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginUserDetailsServiceImpl.class);
	
	@Autowired
	private LoginUserDetailsDao loginUserDetailsDAO;
	@Autowired
	private LoginDetailsVOProviderService loginDetailsVOProviderService;
	
    //Method to get all elements list mapped to particular user -> Start
    @Override
	public UserElements getMappedUserElements() throws Exception
    {
    	long rootId = -1L;
		List<OrgElementMstDto> moduleScreenList = null;
		UserElements navigationUserElements = null;
		try
		{
			LoginDetailsVO loginDetailsVO = loginDetailsVOProviderService.getLoginDetailsVO();	//Get LoginDetailsVO from Security Context holder
			
			// Get distinct role code from OrgRoleMst -> Start
			List<OrgRoleMst> roles = loginDetailsVO.getRoles();
			List<Long> lstRoleCodes = null;
			if(roles != null && !roles.isEmpty())
			{
				lstRoleCodes = new ArrayList<Long>();
				for(OrgRoleMst orgRoleMst :roles)
				{
					lstRoleCodes.add(orgRoleMst.getRoleCode());
				}
			}
			// Get distinct role code from OrgRoleMst -> End
			moduleScreenList = loginUserDetailsDAO.getMappedUserElements(loginDetailsVO.getUser().isActivateFlag() , loginDetailsVO.getLocale() , lstRoleCodes);
			if(moduleScreenList != null && !moduleScreenList.isEmpty())
			{
				navigationUserElements = new UserElements(moduleScreenList , rootId);
			}
		}
		catch(Exception e)
		{
			throw e;
		}
    	return navigationUserElements;
    }
    //Method to get all elements list mapped to particular user -> End
}


