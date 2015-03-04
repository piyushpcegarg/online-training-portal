/**
 * This Service will contain all the functionality related to skill.
 * Like add , update and delete
 */
package com.trainingportal.Masters.service;

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
import com.gargorg.Masters.valueObject.OrgUserMst;
import com.gargorg.common.Utils.CommonUtility;
import com.trainingportal.Masters.dao.SkillDao;
import com.trainingportal.Masters.dto.SkillDto;
import com.trainingportal.Masters.valueObject.OrgSkillMst;


/**
 * @author piyush
 *
 */
@Service
@Transactional
public class SkillServiceImpl implements SkillService 
{
	@Autowired
	private SkillDao skillDAO;
	@Autowired
	private LoginDetailsVOProviderService loginDetailsVOProviderService;
	@Autowired
	private CommonUtility commonUtility;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SkillServiceImpl.class);
	
	//Method to Get all Skills from database -> Start
	@Override
	public List<SkillDto> getSkillList() throws Exception
	{
		try
		{
			return skillDAO.getAllSkills();
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	//Method to Get all Skills from database -> End
	
	//Method to Get SkillDto from database according to Skill Code-> Start
	@Override
	public SkillDto getSkillDtoFromCode(long skillCode) throws Exception
	{
		SkillDto skillDto = null;
		try
		{
			
			OrgSkillMst skill = skillDAO.getSkillByCode(skillCode);
			skillDto = new SkillDto();
			BeanUtils.copyProperties(skill, skillDto);
		}
		catch(Exception e)
		{
			throw e;
		}
		return skillDto;
	}
	//Method to Get SkillDto from database according to Skill Code-> End
	
	//Method to save skill in the database -> Start
	@Override
	public void saveSkill(SkillDto skillDto) throws Exception
	{
		try
		{
			//Convert skillDto into  OrgSkillMst -> Start
			LoginDetailsVO loginDetailsVO = loginDetailsVOProviderService.getLoginDetailsVO();	//Get LoginDetailsVO from Security Context holder
			OrgSkillMst skill = null;
			OrgUserMst createdUser =  loginDetailsVO.getUser();
			Date currDate = commonUtility.getCurrentDateFromDB();
			long skillCode = skillDAO.getNextSkillCode();
			boolean activateFlag = skillDto.isActivateFlag();
			
			//Create OrgSkillMst VO -> Start
			skill = new OrgSkillMst();
			skill.setSkillCode(skillCode);
			skill.setSkillName(skillDto.getSkillName());
			skill.setSkillDesc(skillDto.getSkillDesc());
			skill.setActivateFlag(activateFlag);
			skill.setOrgUserMstByCreatedUserId(createdUser);
			skill.setCreatedDate(currDate);
			//Create OrgSkillMst VO -> End
			
			//Convert skillDto into  OrgSkillMst -> End
			skillDAO.save(skill);
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	//Method to save skill in the database -> End
	
	//Method to update skill in the database -> Start
	@Override
	public boolean updateSkill(SkillDto skillDto) throws Exception
	{
		boolean isValuesChanged = false;			// To determine whether user changed the values or not
		try
		{
			//Convert skillDto into  OrgSkillMst  -> Start
			LoginDetailsVO loginDetailsVO = loginDetailsVOProviderService.getLoginDetailsVO();	//Get LoginDetailsVO from Security Context holder
			long skillCode = skillDto.getSkillCode();
			OrgUserMst updatedUser =  loginDetailsVO.getUser();
			Date updatedDate = commonUtility.getCurrentDateFromDB();
			//Convert skillDto into  OrgSkillMst  -> End
			
			OrgSkillMst orgSkillMstPersistent = skillDAO.getSkillByCode(skillCode);
			//Compare lstOrgSkillDetailsRlt and lstOrgSkillMstPersistent whether user changed something or not - > Start
			SkillDto skillFromScreen = null;
			OrgSkillMst skillFromDb = null;
						
			skillFromScreen = skillDto;
			skillFromDb = orgSkillMstPersistent;
			
			if(	!skillFromScreen.getSkillName().equals(skillFromDb.getSkillName()) || 						// SkillName
						!skillFromScreen.getSkillDesc().equals(skillFromDb.getSkillDesc())	||				// SkillDesc
							skillFromScreen.isActivateFlag() != skillFromDb.isActivateFlag()	||				// ActivateFlag
			  					isValuesChanged
			  )
			{
				isValuesChanged = true;
			}
			//Compare lstOrgSkillMst and lstSkills(Persistent) whether user changed something or not - > End
			if(isValuesChanged)
			{
				BeanUtils.copyProperties(skillDto , orgSkillMstPersistent);
				// Copy other properties fetched from database to make these objects persistent - > STart
				orgSkillMstPersistent.setOrgUserMstByUpdatedUserId(updatedUser);
				orgSkillMstPersistent.setUpdatedDate(updatedDate);
				// Copy other properties fetched from database to make these objects persistent - > End
				
				skillDAO.update(orgSkillMstPersistent);
			}
		}
		catch(Exception e)
		{
			throw e;
		}
		return isValuesChanged;
	}
	//Method to update skill in the database -> End
	
	//Method to check Skill availability from database -> Start
	@Override
	public boolean isSkillExist(SkillDto skillDto) throws Exception
	{
		boolean isSkillExist = false;
		try
		{
			// Check Skill Name in English -> Start
			if(skillDAO.getSkillByName(skillDto.getSkillName()) != null)
			{
				isSkillExist = true;
			}
			// Check Skill Name in English -> End
		}
		catch(Exception e)
		{
			throw e;
		}
		return isSkillExist;
	}
	//Method to check Skill availability from database -> End
}
