/**
 * This Service will contain all the functionality related to roleElement.
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

import com.gargorg.Admin.dto.OrgElementMstDto;
import com.gargorg.Admin.service.LoginDetailsVOProviderService;
import com.gargorg.Admin.valueObject.LoginDetailsVO;
import com.gargorg.Masters.dao.ElementDao;
import com.gargorg.Masters.dao.RoleDao;
import com.gargorg.Masters.dao.RoleElementDao;
import com.gargorg.Masters.dto.OrgRoleMstDto;
import com.gargorg.Masters.dto.RoleDto;
import com.gargorg.Masters.dto.RoleElementDto;
import com.gargorg.Masters.valueObject.OrgRoleElementRlt;
import com.gargorg.Masters.valueObject.OrgUserMst;
import com.gargorg.common.Utils.CommonUtility;


/**
 * @author piyush
 *
 */
@Service
@Transactional
public class RoleElementServiceImpl implements RoleElementService 
{
	@Autowired
	private RoleElementDao roleElementDAO;
	@Autowired
	private ElementDao elementDAO;
	@Autowired
	private RoleDao roleDAO;
	@Autowired
	private LoginDetailsVOProviderService loginDetailsVOProviderService;
	@Autowired
	private CommonUtility commonUtility;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RoleElementServiceImpl.class);
	
	//Method to Get all RoleElements from database according to roleCode -> Start
	@Override
	public List<RoleElementDto> getRoleElementList(long roleCode) throws Exception
	{
		List<RoleElementDto> lstRoleElementDto = null; 
		try
		{
			List<OrgElementMstDto> lstElementDto = elementDAO.getAllEditableElements();
			if(lstElementDto != null && !lstElementDto.isEmpty())
			{
				int lstElementsSize = lstElementDto.size();
				lstRoleElementDto = new ArrayList<RoleElementDto>();
				RoleElementDto roleElementDto = null;
				for(int loopCountOuter = 0 ; loopCountOuter < lstElementsSize ; loopCountOuter++)
				{
					roleElementDto = new RoleElementDto();
					BeanUtils.copyProperties(lstElementDto.get(loopCountOuter) , roleElementDto);
					lstRoleElementDto.add(roleElementDto);
				}
			}
		}
		catch(Exception e)
		{
			throw e;
		}
		return lstRoleElementDto;
	}
	//Method to Get all RoleElements from database -> End
	
	//Method to update all RoleElements from database according to roleCode -> Start
	@Override
	public boolean updateRoleElementList(long roleCode , List<Long> lstElementCodes) throws Exception
	{
		boolean isValuesChanged = false;			// To determine whether user changed the values or not  
		try
		{
			// Create element code list which is selected by User -> End
			List<OrgRoleElementRlt> lstRoleElementRltPersistent = roleElementDAO.getAllRoleElements(roleCode);
			int lstElementCodesSize = lstElementCodes.size();
			int lstSelectedElementSize =  lstElementCodesSize; // To determine whether user selected same elements or not
			int lstRoleElementRltSize = lstRoleElementRltPersistent.size();
			//Compare lstElementCodes and lstRoleElementRlt whether user changed something or not - > Start
			if(lstElementCodesSize == lstRoleElementRltSize)		// Check whether user changed something or not
			{
				for(int loopCountOuter = 0 ; loopCountOuter < lstElementCodesSize ; loopCountOuter++)
				{
					for(int loopCountInner = 0 ; loopCountInner < lstRoleElementRltSize ; loopCountInner++)
					{
						if(lstElementCodes.get(loopCountOuter).longValue() == lstRoleElementRltPersistent.get(loopCountInner).getElementCode())
						{
							lstSelectedElementSize--;
						}
					}
				}
			}
			//Compare lstElementCodes and lstRoleElementRlt whether user changed something or not - > End
			
			if(lstSelectedElementSize != 0)
			{
				isValuesChanged = true;
				LoginDetailsVO loginDetailsVO = loginDetailsVOProviderService.getLoginDetailsVO();	//Get LoginDetailsVO from Security Context holder
				OrgUserMst createdUser =  loginDetailsVO.getUser();
				Date currDate = commonUtility.getCurrentDateFromDB();
				//Create List<OrgRoleElementRlt> for insertion in database -> Start
				List<OrgRoleElementRlt> lstOrgRoleElementRlt =  new ArrayList<OrgRoleElementRlt>();
				OrgRoleElementRlt orgRoleElementRlt = null;
				for(long elementCode : lstElementCodes)
				{
					orgRoleElementRlt = new OrgRoleElementRlt();
					orgRoleElementRlt.setRoleCode(roleCode);
					orgRoleElementRlt.setElementCode(elementCode);
					orgRoleElementRlt.setActivateFlag(true);
					orgRoleElementRlt.setOrgUserMstByCreatedUserId(createdUser);
					orgRoleElementRlt.setCreatedDate(currDate);
					lstOrgRoleElementRlt.add(orgRoleElementRlt);
				}
				//Create List<OrgRoleElementRlt> for insertion in database -> End
				// Delete previous mapped entries and insert new entries for roleCode -> Start
				roleElementDAO.updateAllRoleElements(lstRoleElementRltPersistent , lstOrgRoleElementRlt);
				// Delete previous mapped entries and insert new entries for roleCode -> End
			}
		}
		catch(Exception e)
		{
			throw e;
		}
		return isValuesChanged;
	}
	//Method to update all RoleElements from database -> End
	
	//Method to Get RoleDto from database according to Role Code-> Start
	@Override
	public RoleDto getRoleDtoFromCode(long roleCode) throws Exception
	{
		RoleDto roleDto = null;
		try
		{
			List<OrgRoleMstDto> lstRoles = roleDAO.getRoleByCode(roleCode);
			roleDto = new RoleDto();
			// Fill roleDto form bean -> Start
			roleDto.setRoleCode(roleCode);
			roleDto.setEngRoleName(lstRoles.get(0).getRoleName());
			roleDto.setEngRoleShortName(lstRoles.get(0).getRoleShortName());
			roleDto.setEngRoleDesc(lstRoles.get(0).getRoleDesc());
			
			roleDto.setHinRoleName(lstRoles.get(1).getRoleName());
			roleDto.setHinRoleShortName(lstRoles.get(1).getRoleShortName());
			roleDto.setHinRoleDesc(lstRoles.get(1).getRoleDesc());
			
			roleDto.setActivateFlag(lstRoles.get(0).isActivateFlag());
			
			// Set lstElementCodes in roleDto -> Start
			List<Long> lstElementCodes = new ArrayList<Long>();
			List<OrgRoleElementRlt> lstRoleElements = roleElementDAO.getAllRoleElements(roleCode);
			if(lstRoleElements != null && !lstRoleElements.isEmpty())
			{
				for(OrgRoleElementRlt orgRoleElementRlt : lstRoleElements)
				{
					lstElementCodes.add(orgRoleElementRlt.getElementCode());
				}
			}
			roleDto.setLstElementCodes(lstElementCodes);
			// Set lstElementCodes in roleDto -> End
			// Fill roleDto form bean -> End
		}
		catch(Exception e)
		{
			throw e;
		}
		return roleDto;
	}
	//Method to Get RoleDto from database according to Role Code-> End
}
