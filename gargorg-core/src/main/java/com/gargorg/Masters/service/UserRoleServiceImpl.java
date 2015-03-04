/**
 * This Service will contain all the functionality related to userRole.
 * Like add , update and delete
 */
package com.gargorg.Masters.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gargorg.Admin.service.LoginDetailsVOProviderService;
import com.gargorg.Admin.valueObject.LoginDetailsVO;
import com.gargorg.Masters.dao.RoleDao;
import com.gargorg.Masters.dao.UserDao;
import com.gargorg.Masters.dao.UserRoleDao;
import com.gargorg.Masters.dto.OrgRoleMstDto;
import com.gargorg.Masters.dto.UserRoleDto;
import com.gargorg.Masters.valueObject.OrgUserMst;
import com.gargorg.Masters.valueObject.OrgUserRoleRlt;
import com.gargorg.common.Utils.CommonUtility;


/**
 * @author piyush
 *
 */
@Service
@Transactional
public class UserRoleServiceImpl implements UserRoleService 
{
	@Autowired
	private UserRoleDao userRoleDAO;
	@Autowired
	private RoleDao roleDAO;
	@Autowired
	private UserDao userDAO;
	@Autowired
	private LoginDetailsVOProviderService loginDetailsVOProviderService;
	@Autowired
	private CommonUtility commonUtility;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserRoleServiceImpl.class);
	
	//Method to Get all UserRoles from database according to roleCode -> Start
	@Override
	public List<UserRoleDto> getUserRoleList(long userId) throws Exception
	{
		List<UserRoleDto> lstUserRoleDto = null; 
		try
		{
			List<OrgRoleMstDto> lstRoles = roleDAO.getAllRoles();
			if(lstRoles != null && !lstRoles.isEmpty())
			{
				int lstRolesSize = lstRoles.size();
				lstUserRoleDto = new ArrayList<UserRoleDto>();
				UserRoleDto userRoleDto = null;
				for(int loopCountOuter = 0 ; loopCountOuter < lstRolesSize ; loopCountOuter++)
				{
					userRoleDto = new UserRoleDto();
					BeanUtils.copyProperties(lstRoles.get(loopCountOuter) , userRoleDto);
					lstUserRoleDto.add(userRoleDto);
				}
			}
		}
		catch(Exception e)
		{
			throw e;
		}
		return lstUserRoleDto;
	}
	//Method to Get all UserRoles from database -> End
	
	//Method to update all UserRoles from database according to userId -> Start
	@Override
	public boolean updateUserRoleList(long userId , List<Long> lstRoleCodes) throws Exception
	{
		boolean isValuesChanged = false;			// To determine whether user changed the values or not  
		try
		{
			// Create Role code list which is selected by User -> End
			List<OrgUserRoleRlt> lstUserRoleRltPersistent = userRoleDAO.getAllUserRoles(userId);
			int lstRoleCodeSize = lstRoleCodes.size();
			int lstSelectedRoleSize =  lstRoleCodeSize; // To determine whether user selected same roles or not
			int lstUserRoleRltSize = lstUserRoleRltPersistent.size();
			//Compare lstRoleCodes and lstUserRoleRltPersistent whether user changed something or not - > Start
			if(lstRoleCodeSize == lstUserRoleRltSize)		// Check whether user changed something or not
			{
				for(int loopCountOuter = 0 ; loopCountOuter < lstRoleCodeSize ; loopCountOuter++)
				{
					for(int loopCountInner = 0 ; loopCountInner < lstUserRoleRltSize ; loopCountInner++)
					{
						if(lstRoleCodes.get(loopCountOuter).longValue() == lstUserRoleRltPersistent.get(loopCountInner).getRoleCode())
						{
							lstSelectedRoleSize--;
						}
					}
				}
			}
			//Compare lstElementCodes and lstUserRoleRlt whether user changed something or not - > End
			
			if(lstSelectedRoleSize != 0)
			{
				isValuesChanged = true;
				LoginDetailsVO loginDetailsVO = loginDetailsVOProviderService.getLoginDetailsVO();	//Get LoginDetailsVO from Security Context holder
				OrgUserMst createdUser =  loginDetailsVO.getUser();
				Date currDate = commonUtility.getCurrentDateFromDB();
				//Create List<OrgUserRoleRlt> for insertion in database -> Start
				List<OrgUserRoleRlt> lstOrgUserRoleRlt =  new ArrayList<OrgUserRoleRlt>();
				OrgUserRoleRlt orgUserRoleRlt = null;
				OrgUserMst orgUserMst = userDAO.getUserById(userId);
				for(long roleCode : lstRoleCodes)
				{
					orgUserRoleRlt = new OrgUserRoleRlt();
					orgUserRoleRlt.setOrgUserMstByUserId(orgUserMst);
					orgUserRoleRlt.setRoleCode(roleCode);
					orgUserRoleRlt.setActivateFlag(true);
					orgUserRoleRlt.setOrgUserMstByCreatedUserId(createdUser);
					orgUserRoleRlt.setCreatedDate(currDate);
					lstOrgUserRoleRlt.add(orgUserRoleRlt);
				}
				//Create List<OrgUserRoleRlt> for insertion in database -> End
				// Delete previous mapped entries and insert new entries for roleCode -> Start
				userRoleDAO.updateAllUserRoles(lstUserRoleRltPersistent , lstOrgUserRoleRlt);
				// Delete previous mapped entries and insert new entries for roleCode -> End
			}
		}
		catch(Exception e)
		{
			throw e;
		}
		return isValuesChanged;
	}
	//Method to update all UserRoles from database -> End
}
