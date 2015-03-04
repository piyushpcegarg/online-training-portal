/**
 * This Service will contain all the functionality related to trainer.
 * Like add , update and delete
 */
package com.trainingportal.Masters.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gargorg.Admin.dao.CmnLookUpDao;
import com.gargorg.Admin.dao.UserDetailsDao;
import com.gargorg.Admin.service.LoginDetailsVOProviderService;
import com.gargorg.Admin.valueObject.LoginDetailsVO;
import com.gargorg.Masters.dao.UserDao;
import com.gargorg.Masters.dto.UserDto;
import com.gargorg.Masters.valueObject.OrgEmpMst;
import com.gargorg.Masters.valueObject.OrgUserImageMst;
import com.gargorg.Masters.valueObject.OrgUserMst;
import com.gargorg.common.Utils.CommonUtility;
import com.trainingportal.Masters.dao.TrainerDao;
import com.trainingportal.Masters.dto.TrainerDto;
import com.trainingportal.Masters.valueObject.OrgTrainerMst;


/**
 * @author piyush
 *
 */
@Service
@Transactional
public class TrainerServiceImpl implements TrainerService
{
	@Autowired
	private TrainerDao trainerDAO;
	@Autowired
	private UserDao userDAO;
	@Autowired
	private UserDetailsDao userDetailsDao;
	@Autowired
	private CmnLookUpDao cmnLookUpDao;
	@Autowired
	private LoginDetailsVOProviderService loginDetailsVOProviderService;
	@Autowired
	private CommonUtility commonUtility;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TrainerServiceImpl.class);
	
	//Method to Get all Trainers from database -> Start
	@Override
	public List<TrainerDto> getTrainerList() throws Exception
	{
		try
		{
			return trainerDAO.getAllTrainers();
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	//Method to Get all Trainers from database -> End
	
	//Method to Get TrainerDto from database according to User Name -> Start
	@Override
	public TrainerDto getTrainerDtoFromName(String username) throws Exception
	{
		TrainerDto trainerDto = null;
		try
		{
			OrgUserMst user = userDetailsDao.findByUsername(username);
			if(user != null && user.isActivateFlag() == true)
			{
				long userId = user.getUserId();
				List<OrgEmpMst> lstEmployees = userDAO.getEmployeeById(userId);
				OrgUserImageMst userImage = userDAO.getUserImageById(userId);
				trainerDto = new TrainerDto();
				// Fill trainerDto form bean -> Start
				trainerDto.setUserId(userId);
				trainerDto.setUserName(user.getUserName());
				trainerDto.setRegMobileNo(user.getRegMobileNo());
				trainerDto.setOtpEnabled(user.isOtpEnabled());
				
				// Convert byte array to encoded string to show image on JSP -> Start
				trainerDto.setEncodedImageString(new String(Base64.encodeBase64(userImage.getImage())));
				// Convert byte array to encoded string to show image on JSP -> End
				
				trainerDto.setEmpGender(lstEmployees.get(0).getEmpGender());
				trainerDto.setEmpDob(lstEmployees.get(0).getEmpDob());		
				trainerDto.setEmpDoj(lstEmployees.get(0).getEmpDoj());
				trainerDto.setEmail(lstEmployees.get(0).getEmail());
				
				trainerDto.setEngEmpFname(lstEmployees.get(0).getEmpFname());
				trainerDto.setEngEmpMname(lstEmployees.get(0).getEmpMname());
				trainerDto.setEngEmpLname(lstEmployees.get(0).getEmpLname());
				
				trainerDto.setHinEmpFname(lstEmployees.get(1).getEmpFname());
				trainerDto.setHinEmpMname(lstEmployees.get(1).getEmpMname());
				trainerDto.setHinEmpLname(lstEmployees.get(1).getEmpLname());
				
				// Fill trainerDto form bean -> End
			}
		}
		catch(Exception e)
		{
			throw e;
		}
		return trainerDto;
	}
	//Method to Get TrainerDto from database according to User Name -> End
	
	//Method to save trainer in the database -> Start
	@Override
	public long saveTrainer(TrainerDto trainerDto) throws Exception
	{
		long trainerCode;
		try
		{
			//Convert trainerDto into OrgTrainerMst  -> Start
			LoginDetailsVO loginDetailsVO = loginDetailsVOProviderService.getLoginDetailsVO();	//Get LoginDetailsVO from Security Context holder
			trainerCode = trainerDAO.getNextTrainerCode();
			OrgUserMst createdUser =  loginDetailsVO.getUser();
			Date currDate = commonUtility.getCurrentDateFromDB();
			boolean activateFlag = trainerDto.isActivateFlag();
			long userId = trainerDto.getUserId();
			
			OrgTrainerMst trainer = new OrgTrainerMst();
			trainer.setTrainerCode(trainerCode);
			trainer.setOrgUserMstByUserId(userDAO.getUserById(userId));
			trainer.setActivateFlag(activateFlag);
			trainer.setOrgUserMstByCreatedUserId(createdUser);
			trainer.setCreatedDate(currDate);
			
			//Convert trainerDto into OrgTrainerMst  -> End
			trainerDAO.save(trainer);
		}
		catch(Exception e)
		{
			throw e;
		}
		return trainerCode;
	}
	//Method to save role in the database -> End
	
	//Method to Get TrainerDto from database according to Trainer Id -> Start
	@Override
	public TrainerDto getTrainerDtoFromCode(long trainerCode) throws Exception
	{
		TrainerDto trainerDto = null;
		try
		{
			OrgTrainerMst trainer = trainerDAO.getTrainerByCode(trainerCode);
			long userId = trainer.getOrgUserMstByUserId().getUserId();
			OrgUserMst user = userDAO.getUserById(userId);
			List<OrgEmpMst> lstEmployees = userDAO.getEmployeeById(userId);
			OrgUserImageMst userImage = userDAO.getUserImageById(userId);
			trainerDto = new TrainerDto();
			// Fill trainerDto form bean -> Start
			trainerDto.setActivateFlag(trainer.isActivateFlag());
			trainerDto.setTrainerCode(trainerCode);
			
			trainerDto.setUserId(userId);
			trainerDto.setUserName(user.getUserName());
			trainerDto.setRegMobileNo(user.getRegMobileNo());
			trainerDto.setOtpEnabled(user.isOtpEnabled());
			
			// Convert byte array to encoded string to show image on JSP -> Start
			trainerDto.setEncodedImageString(new String(Base64.encodeBase64(userImage.getImage())));
			// Convert byte array to encoded string to show image on JSP -> End
			
			trainerDto.setEmpGender(lstEmployees.get(0).getEmpGender());
			trainerDto.setEmpDob(lstEmployees.get(0).getEmpDob());		
			trainerDto.setEmpDoj(lstEmployees.get(0).getEmpDoj());
			trainerDto.setEmail(lstEmployees.get(0).getEmail());
			
			trainerDto.setEngEmpFname(lstEmployees.get(0).getEmpFname());
			trainerDto.setEngEmpMname(lstEmployees.get(0).getEmpMname());
			trainerDto.setEngEmpLname(lstEmployees.get(0).getEmpLname());
			
			trainerDto.setHinEmpFname(lstEmployees.get(1).getEmpFname());
			trainerDto.setHinEmpMname(lstEmployees.get(1).getEmpMname());
			trainerDto.setHinEmpLname(lstEmployees.get(1).getEmpLname());
			
			// Fill trainerDto form bean -> End
		}
		catch(Exception e)
		{
			throw e;
		}
		return trainerDto;
	}
	//Method to Get TrainerDto from database according to Trainer Id -> End
	
	//Method to update trainer in the database -> Start
	@Override
	public boolean updateTrainer(TrainerDto trainerDto) throws Exception
	{
		boolean isValuesChanged = false;			// To determine whether trainer changed the values or not
		try
		{
			//Convert trainerDto into OrgTrainerMst -> Start
			LoginDetailsVO loginDetailsVO = loginDetailsVOProviderService.getLoginDetailsVO();	//Get LoginDetailsVO from Security Context holder
			OrgUserMst updateUser =  loginDetailsVO.getUser();
			Date currDate = commonUtility.getCurrentDateFromDB();
			boolean activateFlag = trainerDto.isActivateFlag();
			
			OrgTrainerMst trainer = new OrgTrainerMst();
			trainer.setActivateFlag(activateFlag);
			trainer.setTrainerCode(trainerDto.getTrainerCode());
			trainer.setOrgUserMstByUserId(userDAO.getUserById(trainerDto.getUserId()));
			trainer.setOrgUserMstByUpdatedUserId(updateUser);
			trainer.setUpdatedDate(currDate);
			
			//Convert trainerDto into OrgTrainerMst -> End
			long trainerCode = trainerDto.getTrainerCode();
			OrgTrainerMst trainerPersistent = trainerDAO.getTrainerByCode(trainerCode);
			//Compare trainer and trainerPersistent whether user changed something or not - > Start
			// Compare Trainer -> Start
			if(	trainer.isActivateFlag() != trainerPersistent.isActivateFlag()	||				// Activate flag
			  					isValuesChanged
			  )
			{
				isValuesChanged = true;
			}
			// Compare Trainer -> End
			
			if(isValuesChanged)
			{
				OrgUserMst orgUserMstByCreatedUserId = trainerPersistent.getOrgUserMstByCreatedUserId();
				Date createdDate = trainerPersistent.getCreatedDate();
				// Copy other properties fetched from database to make these objects persistent - > STart
				trainer.setTrainerId(trainerPersistent.getTrainerId());
				trainer.setOrgUserMstByCreatedUserId(orgUserMstByCreatedUserId);
				trainer.setCreatedDate(createdDate);
				// Copy other properties fetched from database to make these objects persistent - > End
				
				trainerDAO.update(trainer);
			}
		}
		catch(Exception e)
		{
			throw e;
		}
		return isValuesChanged;
	}
	//Method to update trainer in the database -> End

	//This method get trainer fetched from orgTrainerMst table(userId) - > Start
	@Override
	public OrgTrainerMst getTrainerByUserId(long userId) throws Exception
	{
		try
		{
			return trainerDAO.getTrainerByUserId(userId);
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	//This method get trainer fetched from orgTrainerMst table(userId) - > End
	
	//Method to Get all activate Users from database -> Start
	@Override
	public List<UserDto> getUserList(String username) throws Exception
	{
		try
		{
			return trainerDAO.getAllUsers(username);
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	//Method to Get all activate Users from database -> End
	
}
