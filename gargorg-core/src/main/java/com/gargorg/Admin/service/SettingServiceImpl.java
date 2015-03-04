/**
 * This Service will contains the functionality for loading logged in user information.
 */
package com.gargorg.Admin.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gargorg.Admin.dao.CmnLookUpDao;
import com.gargorg.Admin.dao.SettingDao;
import com.gargorg.Admin.dao.UserDetailsDao;
import com.gargorg.Admin.dto.CmnLookupMstDto;
import com.gargorg.Admin.valueObject.LoginDetailsVO;
import com.gargorg.Masters.valueObject.OrgUserMst;
import com.gargorg.common.Utils.CommonFunctions;
import com.gargorg.common.Utils.CommonUtility;


/**
 * @author piyush
 *
 */
@Service
@Transactional
public class SettingServiceImpl implements SettingService 
{
	@Autowired
	private UserDetailsDao userDetailsDao;
	@Autowired
	private CommonUtility commonUtility;
	@Autowired
	private CmnLookUpDao cmnLookUpDao;
	@Autowired
	private LoginDetailsVOProviderService loginDetailsVOProviderService;
	@Autowired
	private SettingDao settingDao;
	@Value("${defaultPassword}") 
	private String defaultPassword;
	@Value("${passwordExpireDays}") 
	private int passwordExpireDays;
	@Value("${securityRequired}") 
	private String securityRequired;
	

	private static final Logger LOGGER = LoggerFactory.getLogger(SettingServiceImpl.class);
	
	//Method to reset password and set the password change date -> Start
    @Override
	public OrgUserMst resetPassword(String username) throws Exception
    {
    	OrgUserMst orgUserMst = null;
    	try
    	{
    		orgUserMst = userDetailsDao.findByUsername(username);
	    	if(orgUserMst != null)
	    	{
	    		LoginDetailsVO loginDetailsVO = loginDetailsVOProviderService.getLoginDetailsVO();	//Get LoginDetailsVO from Security Context holder
	    		
	    		Date currDate = commonUtility.getCurrentDateFromDB();
	    		orgUserMst.setPassword(CommonFunctions.getEncodedPassword(defaultPassword));
	    		orgUserMst.setPwdchangedDate(CommonFunctions.addDaysInDate(currDate, passwordExpireDays));
	    		orgUserMst.setOrgUserMstByUpdatedUserId(loginDetailsVO.getUser());
	    		orgUserMst.setUpdatedDate(currDate);
	    		userDetailsDao.updateUser(orgUserMst);
	    	}
    	}
    	catch(Exception e)
    	{
    		throw e;
    	}
    	return orgUserMst;
    }
    //Method to reset password and set the password change date -> End
    
    //Method to get all security settings -> Start
    @Override
	public List<CmnLookupMstDto> getSecuritySettings() throws Exception
    {
    	List<CmnLookupMstDto> lstSecuritySettings = new ArrayList<CmnLookupMstDto>();
    	try
    	{
    		LoginDetailsVO loginDetailsVO = loginDetailsVOProviderService.getLoginDetailsVO();	//Get LoginDetailsVO from Security Context holder
    		CmnLookupMstDto securityRequiredLookUp = cmnLookUpDao.getLookUpByLookUpName(securityRequired, loginDetailsVO.getLang().getLangId());
    		if(securityRequiredLookUp != null)
    		{
    			List<CmnLookupMstDto> lstSecurityRequiredLookUp = cmnLookUpDao.getLookUpByParentLookUpCode(securityRequiredLookUp.getLookupCode() , loginDetailsVO.getLang().getLangId());
    			lstSecuritySettings.add(securityRequiredLookUp);
        		lstSecuritySettings.addAll(lstSecurityRequiredLookUp);
    		}
    	}
    	catch(Exception e)
    	{
    		throw e;
    	}
    	return lstSecuritySettings;
    }
	//Method to get all security settings -> End
    
    //Method to update all security settings -> Start
    @Override
	public void updateSecuritySettings(List<CmnLookupMstDto> lstCmnLookupMst) throws Exception
    {
    	try
    	{
    		settingDao.updateSecuritySettings(lstCmnLookupMst);
    	}
    	catch(Exception e)
    	{
    		throw e;
    	}
    }
  //Method to update all security settings -> End
}


