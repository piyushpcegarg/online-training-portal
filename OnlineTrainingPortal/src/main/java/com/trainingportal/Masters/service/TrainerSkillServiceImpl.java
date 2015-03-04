/**
 * This Service will contain all the functionality related to trainerSkill.
 * Like add , update and delete
 */
package com.trainingportal.Masters.service;

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
import com.gargorg.Masters.valueObject.OrgUserMst;
import com.gargorg.common.Utils.CommonUtility;
import com.trainingportal.Masters.dao.SkillDao;
import com.trainingportal.Masters.dao.TrainerDao;
import com.trainingportal.Masters.dao.TrainerSkillDao;
import com.trainingportal.Masters.dto.SkillDto;
import com.trainingportal.Masters.dto.TrainerDto;
import com.trainingportal.Masters.dto.TrainerSkillDto;
import com.trainingportal.Masters.valueObject.OrgTrainerMst;
import com.trainingportal.Masters.valueObject.OrgTrainerSkillMpg;


/**
 * @author piyush
 *
 */
@Service
@Transactional
public class TrainerSkillServiceImpl implements TrainerSkillService 
{
	@Autowired
	private TrainerSkillDao trainerSkillDAO;
	@Autowired
	private SkillDao skillDAO;
	@Autowired
	private TrainerDao trainerDAO;
	@Autowired
	private LoginDetailsVOProviderService loginDetailsVOProviderService;
	@Autowired
	private CommonUtility commonUtility;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TrainerSkillServiceImpl.class);
	
	//Method to Get all TrainerSkills from database according to trainerCode -> Start
	@Override
	public List<TrainerSkillDto> getTrainerSkillList(long trainerCode) throws Exception
	{
		List<TrainerSkillDto> lstTrainerSkillDto = null; 
		try
		{
			List<SkillDto> lstSkillDto = skillDAO.getAllSkills();
			if(lstSkillDto != null && !lstSkillDto.isEmpty())
			{
				int lstSkillDtoSize = lstSkillDto.size();
				lstTrainerSkillDto = new ArrayList<TrainerSkillDto>();
				TrainerSkillDto trainerSkillDto = null;
				for(int loopCountOuter = 0 ; loopCountOuter < lstSkillDtoSize ; loopCountOuter++)
				{
					trainerSkillDto = new TrainerSkillDto();
					BeanUtils.copyProperties(lstSkillDto.get(loopCountOuter) , trainerSkillDto);
					lstTrainerSkillDto.add(trainerSkillDto);
				}
			}
		}
		catch(Exception e)
		{
			throw e;
		}
		return lstTrainerSkillDto;
	}
	//Method to Get all TrainerSkills from database -> End
	
	//Method to update all TrainerSkills from database according to trainerCode -> Start
	@Override
	public boolean updateTrainerSkillList(long trainerCode , List<Long> lstSkillCodes) throws Exception
	{
		boolean isValuesChanged = false;			// To determine whether user changed the values or not  
		try
		{
			// Create skill code list which is selected by User -> End
			List<OrgTrainerSkillMpg> lstTrainerSkillMpgPersistent = trainerSkillDAO.getAllTrainerSkills(trainerCode);
			int lstSkillCodesSize = lstSkillCodes.size();
			int lstSelectedSkillSize =  lstSkillCodesSize; // To determine whether user selected same skill or not
			int lstTrainerSkillMpgSize = lstTrainerSkillMpgPersistent.size();
			//Compare lstSkillCodes and lstTrainerSkillMpg whether user changed something or not - > Start
			if(lstSkillCodesSize == lstTrainerSkillMpgSize)		// Check whether user changed something or not
			{
				for(int loopCountOuter = 0 ; loopCountOuter < lstSkillCodesSize ; loopCountOuter++)
				{
					for(int loopCountInner = 0 ; loopCountInner < lstTrainerSkillMpgSize ; loopCountInner++)
					{
						if(lstSkillCodes.get(loopCountOuter).longValue() == lstTrainerSkillMpgPersistent.get(loopCountInner).getSkillCode())
						{
							lstSelectedSkillSize--;
						}
					}
				}
			}
			//Compare lstSkillCodes and lstTrainerSkillMpg whether user changed something or not - > End
			
			if(lstSelectedSkillSize != 0)
			{
				isValuesChanged = true;
				LoginDetailsVO loginDetailsVO = loginDetailsVOProviderService.getLoginDetailsVO();	//Get LoginDetailsVO from Security Context holder
				OrgUserMst createdUser =  loginDetailsVO.getUser();
				Date currDate = commonUtility.getCurrentDateFromDB();
				//Create List<OrgTrainerSkillMpg> for insertion in database -> Start
				List<OrgTrainerSkillMpg> lstOrgTrainerSkillMpg =  new ArrayList<OrgTrainerSkillMpg>();
				OrgTrainerSkillMpg orgTrainerSkillMpg = null;
				for(long skillCode : lstSkillCodes)
				{
					orgTrainerSkillMpg = new OrgTrainerSkillMpg();
					orgTrainerSkillMpg.setTrainerCode(trainerCode);
					orgTrainerSkillMpg.setSkillCode(skillCode);
					orgTrainerSkillMpg.setActivateFlag(true);
					orgTrainerSkillMpg.setOrgUserMstByCreatedUserId(createdUser);
					orgTrainerSkillMpg.setCreatedDate(currDate);
					lstOrgTrainerSkillMpg.add(orgTrainerSkillMpg);
				}
				//Create List<OrgTrainerSkillMpg> for insertion in database -> End
				// Delete previous mapped entries and insert new entries for trainerCode -> Start
				trainerSkillDAO.updateAllTrainerSkills(lstTrainerSkillMpgPersistent , lstOrgTrainerSkillMpg);
				// Delete previous mapped entries and insert new entries for trainerCode -> End
			}
		}
		catch(Exception e)
		{
			throw e;
		}
		return isValuesChanged;
	}
	//Method to update all TrainerSkills from database -> End
	
	//Method to Get TrainerDto from database according to Trainer Code-> Start
	@Override
	public TrainerDto getTrainerDtoFromCode(long trainerCode) throws Exception
	{
		TrainerDto trainerDto = null;
		try
		{
			OrgTrainerMst orgTrainerMst = trainerDAO.getTrainerByCode(trainerCode);
			trainerDto = new TrainerDto();
			// Fill roleDto form bean -> Start
			BeanUtils.copyProperties(orgTrainerMst, trainerDto);
			// Set lstSkillCodes in trainerDto -> Start
			List<Long> lstSkillCodes = new ArrayList<Long>();
			List<OrgTrainerSkillMpg> lstTrainerSkills = trainerSkillDAO.getAllTrainerSkills(trainerCode);
			if(lstTrainerSkills != null && !lstTrainerSkills.isEmpty())
			{
				for(OrgTrainerSkillMpg orgTrainerSkillMpg : lstTrainerSkills)
				{
					lstSkillCodes.add(orgTrainerSkillMpg.getSkillCode());
				}
			}
			trainerDto.setLstSkillCodes(lstSkillCodes);
			// Set lstElementCodes in roleDto -> End
			// Fill roleDto form bean -> End
		}
		catch(Exception e)
		{
			throw e;
		}
		return trainerDto;
	}
	//Method to Get TrainerDto from database according to Trainer Code-> End
}
