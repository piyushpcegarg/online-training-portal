/**
 * This Service will contain all the functionality related to role.
 * Like add , update and delete
 */
package com.gargorg.Masters.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gargorg.Admin.service.LoginDetailsVOProviderService;
import com.gargorg.Admin.valueObject.LoginDetailsVO;
import com.gargorg.Masters.dao.RoleDao;
import com.gargorg.Masters.dto.OrgRoleMstDto;
import com.gargorg.Masters.dto.RoleDto;
import com.gargorg.Masters.valueObject.OrgRoleDetailsRlt;
import com.gargorg.Masters.valueObject.OrgRoleMst;
import com.gargorg.Masters.valueObject.OrgUserMst;
import com.gargorg.common.Utils.CommonUtility;
import com.gargorg.common.constant.CommonConstants;


/**
 * @author piyush
 *
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService 
{
	@Autowired
	private RoleDao roleDAO;
	@Autowired
	private LoginDetailsVOProviderService loginDetailsVOProviderService;
	@Autowired
	private CommonUtility commonUtility;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RoleServiceImpl.class);
	
	//Method to Get all Roles from database -> Start
	@Override
	public List<OrgRoleMstDto> getRoleList() throws Exception
	{
		try
		{
			return roleDAO.getAllRoles();
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	//Method to Get all Roles from database -> End
	
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
			// Fill roleDto form bean -> End
		}
		catch(Exception e)
		{
			throw e;
		}
		return roleDto;
	}
	//Method to Get RoleDto from database according to Role Code-> End
	
	//Method to save role in the database -> Start
	@Override
	public void saveRole(RoleDto roleDto) throws Exception
	{
		try
		{
			//Convert roleDto into  OrgRoleMst and List<OrgRoleDetailsRlt> -> Start
			List<OrgRoleDetailsRlt> lstOrgRoleDetailsRlt = new ArrayList<OrgRoleDetailsRlt>();
			LoginDetailsVO loginDetailsVO = loginDetailsVOProviderService.getLoginDetailsVO();	//Get LoginDetailsVO from Security Context holder
			OrgRoleMst role = null;
			OrgRoleDetailsRlt roleDtls = null;
			OrgUserMst createdUser =  loginDetailsVO.getUser();
			Date currDate = commonUtility.getCurrentDateFromDB();
			long roleCode = roleDAO.getNextRoleCode();
			boolean activateFlag = roleDto.isActivateFlag();
			
			//Create OrgRoleMst VO -> Start
			role = new OrgRoleMst();
			role.setRoleCode(roleCode);
			role.setActivateFlag(activateFlag);
			role.setOrgUserMstByCreatedUserId(createdUser);
			role.setCreatedDate(currDate);
			//Create OrgRoleMst VO -> End
			//Create OrgRoleDetailsRlt language specific VO -> Start
			roleDtls = new OrgRoleDetailsRlt();	// For English Language
			roleDtls.setRoleCode(roleCode);
			roleDtls.setCmnLanguageMst(commonUtility.getCmnLanguageMstFromLangId(CommonConstants.ENGLISH));
			roleDtls.setRoleName(roleDto.getEngRoleName());
			roleDtls.setRoleShortName(roleDto.getEngRoleShortName());
			roleDtls.setRoleDesc(roleDto.getEngRoleDesc());
			roleDtls.setOrgUserMstByCreatedUserId(createdUser);
			roleDtls.setCreatedDate(currDate);
			roleDtls.setActivateFlag(activateFlag);
			lstOrgRoleDetailsRlt.add(roleDtls);
			
			roleDtls = new OrgRoleDetailsRlt();	// For Hindi Language
			roleDtls.setRoleCode(roleCode);
			roleDtls.setCmnLanguageMst(commonUtility.getCmnLanguageMstFromLangId(CommonConstants.HINDI));
			roleDtls.setRoleName(roleDto.getHinRoleName());
			roleDtls.setRoleShortName(roleDto.getHinRoleShortName());
			roleDtls.setRoleDesc(roleDto.getHinRoleDesc());
			roleDtls.setOrgUserMstByCreatedUserId(createdUser);
			roleDtls.setCreatedDate(currDate);
			roleDtls.setActivateFlag(activateFlag);
			lstOrgRoleDetailsRlt.add(roleDtls);
			//Create OrgRoleDetailsRlt language specific VO -> End
			
			//Convert roleDto into  OrgRoleMst and List<OrgRoleDetailsRlt> -> End
			roleDAO.save(role , lstOrgRoleDetailsRlt);
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	//Method to save role in the database -> End
	
	//Method to update role in the database -> Start
	@Override
	public boolean updateRole(RoleDto roleDto) throws Exception
	{
		boolean isValuesChanged = false;			// To determine whether user changed the values or not
		try
		{
			//Convert roleDto into  OrgRoleMst and List<OrgRoleDetailsRlt>  -> Start
			List<OrgRoleDetailsRlt> lstOrgRoleDetailsRlt = new ArrayList<OrgRoleDetailsRlt>();
			LoginDetailsVO loginDetailsVO = loginDetailsVOProviderService.getLoginDetailsVO();	//Get LoginDetailsVO from Security Context holder
			OrgRoleMst role = null;
			OrgRoleDetailsRlt roleDtls = null;
			long roleCode = roleDto.getRoleCode();
			OrgUserMst updatedUser =  loginDetailsVO.getUser();
			Date currDate = commonUtility.getCurrentDateFromDB();
			//Create OrgRoleMst VO -> Start
			role = new OrgRoleMst();
			role.setRoleCode(roleCode);
			role.setActivateFlag(roleDto.isActivateFlag());
			role.setOrgUserMstByUpdatedUserId(updatedUser);
			role.setUpdatedDate(currDate);
			//Create OrgRoleMst VO -> End
			//Create OrgRoleDetailsRlt language specific VO -> Start
			roleDtls = new OrgRoleDetailsRlt();	// For English Language
			roleDtls.setRoleCode(roleCode);
			roleDtls.setCmnLanguageMst(commonUtility.getCmnLanguageMstFromLangId(CommonConstants.ENGLISH));
			roleDtls.setRoleName(roleDto.getEngRoleName());
			roleDtls.setRoleShortName(roleDto.getEngRoleShortName());
			roleDtls.setRoleDesc(roleDto.getEngRoleDesc());
			roleDtls.setOrgUserMstByUpdatedUserId(updatedUser);
			roleDtls.setUpdatedDate(currDate);
			roleDtls.setActivateFlag(roleDto.isActivateFlag());
			lstOrgRoleDetailsRlt.add(roleDtls);
			
			roleDtls = new OrgRoleDetailsRlt();	// For Hindi Language
			roleDtls.setRoleCode(roleCode);
			roleDtls.setCmnLanguageMst(commonUtility.getCmnLanguageMstFromLangId(CommonConstants.HINDI));
			roleDtls.setRoleName(roleDto.getHinRoleName());
			roleDtls.setRoleShortName(roleDto.getHinRoleShortName());
			roleDtls.setRoleDesc(roleDto.getHinRoleDesc());
			roleDtls.setOrgUserMstByUpdatedUserId(updatedUser);
			roleDtls.setUpdatedDate(currDate);
			roleDtls.setActivateFlag(roleDto.isActivateFlag());
			lstOrgRoleDetailsRlt.add(roleDtls);
			//Create OrgRoleDetailsRlt language specific VO -> End
			
			//Convert roleDto into  OrgRoleMst and List<OrgRoleDetailsRlt>  -> End
			
			List<OrgRoleMstDto> lstOrgRoleMstDtoPersistent = roleDAO.getRoleByCode(roleCode);
			//Compare lstOrgRoleDetailsRlt and lstOrgRoleMstPersistent whether user changed something or not - > Start
			int lstOrgRoleDetailsRltSize = lstOrgRoleDetailsRlt.size();
			int lstOrgRoleMstPersistentSize = lstOrgRoleMstDtoPersistent.size();
			OrgRoleDetailsRlt roleFromScreen = null;
			OrgRoleMstDto roleFromDb = null;
			for(int loopCountOuter = 0 ; loopCountOuter < lstOrgRoleDetailsRltSize ; loopCountOuter++)
			{
				for(int loopCountInner = 0 ; loopCountInner < lstOrgRoleMstPersistentSize ; loopCountInner++)
				{
					if(lstOrgRoleDetailsRlt.get(loopCountOuter).getCmnLanguageMst().getLangId() == lstOrgRoleMstDtoPersistent.get(loopCountInner).getCmnLanguageMst().getLangId())
					{
						roleFromScreen = lstOrgRoleDetailsRlt.get(loopCountOuter);
						roleFromDb = lstOrgRoleMstDtoPersistent.get(loopCountInner);
						if(	!roleFromScreen.getRoleName().equals(roleFromDb.getRoleName()) || 						// RoleName
								!roleFromScreen.getRoleShortName().equals(roleFromDb.getRoleShortName())	||			// RoleShortName
									!roleFromScreen.getRoleDesc().equals(roleFromDb.getRoleDesc())	||				// RoleDesc
										roleFromScreen.isActivateFlag() != roleFromDb.isActivateFlag()	||				// ActivateFlag
						  					isValuesChanged
						  )
						{
							isValuesChanged = true;
						}
					}
				}
			}
			//Compare lstOrgRoleMst and lstRoles(Persistent) whether user changed something or not - > End
			if(isValuesChanged)
			{
				OrgUserMst orgUserMstByCreatedUserId = lstOrgRoleMstDtoPersistent.get(0).getOrgUserMstByCreatedUserId();
				Date createdDate = lstOrgRoleMstDtoPersistent.get(0).getCreatedDate();
				// Copy other properties fetched from database to make these objects persistent - > STart
				role.setRoleId(lstOrgRoleMstDtoPersistent.get(0).getRoleId());
				role.setOrgUserMstByCreatedUserId(orgUserMstByCreatedUserId);
				role.setCreatedDate(createdDate);
				
				lstOrgRoleDetailsRlt.get(0).setRoleDetailId(lstOrgRoleMstDtoPersistent.get(0).getRoleDetailId());
				lstOrgRoleDetailsRlt.get(0).setOrgUserMstByCreatedUserId(orgUserMstByCreatedUserId);
				lstOrgRoleDetailsRlt.get(0).setCreatedDate(createdDate);
				
				lstOrgRoleDetailsRlt.get(1).setRoleDetailId(lstOrgRoleMstDtoPersistent.get(1).getRoleDetailId());
				lstOrgRoleDetailsRlt.get(1).setOrgUserMstByCreatedUserId(orgUserMstByCreatedUserId);
				lstOrgRoleDetailsRlt.get(1).setCreatedDate(createdDate);
				// Copy other properties fetched from database to make these objects persistent - > End
				
				roleDAO.update(role , lstOrgRoleDetailsRlt);
			}
		}
		catch(Exception e)
		{
			throw e;
		}
		return isValuesChanged;
	}
	//Method to update role in the database -> End
	
	//Method to check Role availability from database -> Start
	@Override
	public boolean isRoleExist(RoleDto roleDto) throws Exception
	{
		boolean isRoleExist = false;
		try
		{
			// Check Role Name in English -> Start
			if(roleDAO.getRoleByName(roleDto.getEngRoleName()) != null)
			{
				isRoleExist = true;
			}
			// Check Role Name in English -> End
			// Check Role Name in Hindi -> Start
			if(!isRoleExist && roleDAO.getRoleByName(roleDto.getHinRoleName()) != null)
			{
				isRoleExist = true;
			}
			// Check Role Name in Hindi -> End
		}
		catch(Exception e)
		{
			throw e;
		}
		return isRoleExist;
	}
	//Method to check Role availability from database -> End
}
