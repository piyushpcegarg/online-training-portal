/**
 * This Service will contain all the functionality related to training.
 * Like add , update and delete
 */
package com.trainingportal.Transactions.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gargorg.Admin.dao.CmnLookUpDao;
import com.gargorg.Admin.dao.UserDetailsDao;
import com.gargorg.Admin.dto.CmnLookupMstDto;
import com.gargorg.Admin.service.LoginDetailsVOProviderService;
import com.gargorg.Admin.valueObject.LoginDetailsVO;
import com.gargorg.Masters.dao.UserDao;
import com.gargorg.Masters.valueObject.OrgUserMst;
import com.gargorg.common.Utils.CommonUtility;
import com.gargorg.common.constant.CommonConstants;
import com.trainingportal.Masters.dao.TrainerDao;
import com.trainingportal.Masters.dto.TrainerDto;
import com.trainingportal.Transactions.dao.TrainingDao;
import com.trainingportal.Transactions.dto.TrainingDto;
import com.trainingportal.Transactions.valueObject.OrgTrainingTxn;


/**
 * @author piyush
 *
 */
@Service
@Transactional
public class TrainingServiceImpl implements TrainingService
{
	@Autowired
	private TrainingDao trainingDAO;
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
	
	private static final String trainingType = "TRAINING_TYPE";
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TrainingServiceImpl.class);
	
	//Method to Get all Training from database -> Start
	@Override
	public List<TrainingDto> getTrainingList(boolean attendanceTrainingList , boolean feedbackTrainingList) throws Exception
	{
		List<TrainingDto> lstTrainingDto = null;
		try
		{
			if(attendanceTrainingList == false && feedbackTrainingList == false)
			{
				lstTrainingDto = trainingDAO.getAllTrainings();
			}
			else if(attendanceTrainingList == true && feedbackTrainingList == false)
			{
				long userId = loginDetailsVOProviderService.getLoginDetailsVO().getUserId();	//Get logged in user Id	
				long trainerCode = trainerDAO.getTrainerByUserId(userId).getTrainerCode();
				lstTrainingDto = trainingDAO.getAllTrainingsForAttendance(trainerCode);
			}
			else if(attendanceTrainingList == false && feedbackTrainingList == true)
			{
				lstTrainingDto = trainingDAO.getAllTrainingsForFeedback();
			}
			else
			{
				// Do nothing return lstTrainingDto as null
			}
		}
		catch(Exception e)
		{
			throw e;
		}
		return lstTrainingDto;
	}
	//Method to Get all Training from database -> End
		
	//Method to Get all Training Types from database -> Start
	@Override
	public List<CmnLookupMstDto> getTrainingType() throws Exception
	{
		try
		{
			LoginDetailsVO loginDetailsVO = loginDetailsVOProviderService.getLoginDetailsVO();	//Get LoginDetailsVO from Security Context holder
			long langId = loginDetailsVO.getLang().getLangId();
			CmnLookupMstDto securityQuestionsLookUp = cmnLookUpDao.getLookUpByLookUpName(trainingType, langId);
    		List<CmnLookupMstDto> lstSecurityQuestionsLookUp = cmnLookUpDao.getLookUpByParentLookUpCode(securityQuestionsLookUp.getLookupCode() , langId);
    		return lstSecurityQuestionsLookUp;
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	//Method to Get all Training Types from database -> End
	
	//Method to save training in the database -> Start
	@Override
	public long saveTraining(TrainingDto trainingDto) throws Exception
	{
		long trainingCode;
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
			//Convert trainingDto into OrgTrainingTxn  -> Start
			LoginDetailsVO loginDetailsVO = loginDetailsVOProviderService.getLoginDetailsVO();	//Get LoginDetailsVO from Security Context holder
			trainingCode = trainingDAO.getNextTrainingCode();
			OrgUserMst createdUser =  loginDetailsVO.getUser();
			Date currDate = commonUtility.getCurrentDateFromDB();
			boolean activateFlag = trainingDto.isActivateFlag();
			// Get Training Serial No -> Start
			long trainingSerialNo = trainingDAO.getMaxTrainingSerialNo(trainingDto.getTrainingDate());
			// Get Training Serial No -> End
			// Generate Training Name -> Start
			SimpleDateFormat outputDateFormat = new SimpleDateFormat("DDMMYYYY");
			String strCurrDate = outputDateFormat.format(trainingDto.getTrainingDate());
			String trainingName = "TNG_"+strCurrDate+"_"+trainingSerialNo;
			// Generate Training Name -> End
			OrgTrainingTxn training = new OrgTrainingTxn();
			training.setTrainingCode(trainingCode);
			training.setTrainingName(trainingName);
			training.setTrainingSerialNo(trainingSerialNo);
			training.setCourseName(trainingDto.getCourseName());
			training.setDepartmentProject(trainingDto.getDepartmentProject());
			training.setTrainerCode(10001L);
			training.setTrainingType(trainingDto.getTrainingType());
			training.setTrainingDate(trainingDto.getTrainingDate());
			training.setTrainingStartTime(sdf.parse(trainingDto.getTrainingStartTime()));	// Convert string(HH:mm) to date object
			training.setTrainingEndTime(sdf.parse(trainingDto.getTrainingEndTime()));	// Convert string(HH:mm) to date object
			training.setLocationCode(trainingDto.getLocationCode());
			training.setAttendanceStatus(CommonConstants.ATTENDANCE_NOT_TAKEN);
			training.setActivateFlag(activateFlag);
			training.setOrgUserMstByCreatedUserId(createdUser);
			training.setCreatedDate(currDate);
			
			//Convert trainingDto into OrgTrainingTxn  -> End
			trainingDAO.save(training);
		}
		catch(Exception e)
		{
			throw e;
		}
		return trainingCode;
	}
	//Method to save training in the database -> End
	
	//Method to update OrgTrainingTxn in the database -> Start
	@Override
	public boolean updateTraining(TrainingDto trainingDto) throws Exception
	{
		boolean isValuesChanged = false;			// To determine whether training changed the values or not
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");	
			//Convert trainingDto into OrgTrainingMst -> Start
			LoginDetailsVO loginDetailsVO = loginDetailsVOProviderService.getLoginDetailsVO();	//Get LoginDetailsVO from Security Context holder
			OrgUserMst updateUser =  loginDetailsVO.getUser();
			Date currDate = commonUtility.getCurrentDateFromDB();
			
			//Convert trainingDto into OrgTrainingTxn -> End
			long trainingCode = trainingDto.getTrainingCode();
			TrainingDto trainingPersistentDto = trainingDAO.getTrainingByCode(trainingCode);
			//Compare trainingDto and trainingPersistent whether user changed something or not - > Start
			// Compare Training -> Start
			if( !trainingDto.getCourseName().equals(trainingPersistentDto.getCourseName())	||
						trainingDto.getTrainerCode() != trainingPersistentDto.getTrainerCode()	||
								!trainingDto.getDepartmentProject().equals(trainingPersistentDto.getDepartmentProject())	||
										trainingDto.getTrainingDate().compareTo(trainingPersistentDto.getTrainingDate()) != 0	||
												!trainingDto.getTrainingStartTime().equals(trainingPersistentDto.getTrainingStartTime())	||
													!trainingDto.getTrainingEndTime().equals(trainingPersistentDto.getTrainingEndTime())	||
														trainingDto.getTrainingType() != trainingPersistentDto.getTrainingType()	||
															trainingDto.getLocationCode() != trainingPersistentDto.getLocationCode() ||
																	trainingDto.isActivateFlag() != trainingPersistentDto.isActivateFlag()	||
																		isValuesChanged
			  )
			{
				isValuesChanged = true;
			}
			// Compare Training -> End
			
			if(isValuesChanged)
			{
				OrgTrainingTxn training = trainingDAO.getTrainingTxnByCode(trainingCode);
				String[] ignoreProperties = null;
				//copy properties from (source, target , ignoreProperties)
				ignoreProperties = new String[]{"trainingId","trainingCode","trainingName","trainingSerialNo","attendanceStatus","orgUserMstByCreatedUserId","createdDate"};
				BeanUtils.copyProperties(trainingDto , training , ignoreProperties);
				training.setTrainingStartTime(sdf.parse(trainingDto.getTrainingStartTime()));	// Convert string(HH:mm) to date object
				training.setTrainingEndTime(sdf.parse(trainingDto.getTrainingEndTime()));	// Convert string(HH:mm) to date object
				training.setOrgUserMstByUpdatedUserId(updateUser);
				training.setUpdatedDate(currDate);
				trainingDAO.update(training);
			}
		}
		catch(Exception e)
		{
			throw e;
		}
		return isValuesChanged;
	}
	//Method to update OrgTrainingTxn in the database -> End
	
	//Method to Get TrainingDto from database according to Training Id -> Start
	@Override
	public TrainingDto getTrainingDtoFromCode(long trainingCode) throws Exception
	{
		TrainingDto trainingDto = null;
		try
		{
			trainingDto = trainingDAO.getTrainingByCode(trainingCode);
		}
		catch(Exception e)
		{
			throw e;
		}
		return trainingDto;
	}
	//Method to Get TrainingDto from database according to Training Id -> End
	
	//Method to Get all activate Trainers from database -> Start
	@Override
	public List<TrainerDto> getTrainerList(String trainername) throws Exception
	{
		try
		{
			return trainingDAO.getAllTrainers(trainername);
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	//Method to Get all activate Trainers from database -> End
	
}
