/**
 * 
 */
package com.trainingportal.Transactions.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gargorg.Admin.service.LoginDetailsVOProviderService;
import com.gargorg.Masters.valueObject.OrgUserMst;
import com.gargorg.common.Utils.CommonFunctions;
import com.gargorg.common.Utils.CommonUtility;
import com.gargorg.common.constant.CommonConstants;
import com.trainingportal.Masters.dto.TrainerDto;
import com.trainingportal.Masters.valueObject.OrgTrainerMst;
import com.trainingportal.Transactions.dto.TrainingDto;
import com.trainingportal.Transactions.valueObject.OrgTrainingTxn;


/**
 * @author piyush
 *
 */
@Repository
public class TrainingDaoImpl implements TrainingDao 
{
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private LoginDetailsVOProviderService loginDetailsVOProviderService;
	@Autowired
	private CommonUtility commonUtility;
	
	Session session = null;
	Query hqlQuery = null;
	String hqlQueryString = null;
	private static final Logger LOGGER = LoggerFactory.getLogger(TrainingDaoImpl.class);
	
	//This method get list of trainings fetched from OrgTrainingTxn table - > Start
	@Override
	public List<TrainingDto> getAllTrainings() throws Exception
	{
		List<TrainingDto> lstTrainingDto = null;
		try
		{
			session = sessionFactory.getCurrentSession();
			Criteria c  = session.createCriteria(OrgTrainingTxn.class);
			List<OrgTrainingTxn> lstTrainings = c.list();
			TrainingDto trainingDto = null;
			if(lstTrainings != null && !lstTrainings.isEmpty())
			{
				lstTrainingDto = new ArrayList<TrainingDto>();
				for(OrgTrainingTxn training : lstTrainings)
				{
					trainingDto = new TrainingDto();
					//copy properties from (source, target , ignoreProperties)
					String[] ignoreProperties = new String[]{"trainingStartTime","trainingEndTime"};
					BeanUtils.copyProperties(training , trainingDto , ignoreProperties);
					// Determine training status -> Start
					Date currDate = commonUtility.getCurrentDateFromDB();
					trainingDto.setTrainingStatus(CommonFunctions.determineTrainingStatus(training.getTrainingStartTime(), training.getTrainingEndTime(), training.getTrainingDate(), currDate));
					// Determine training status -> End
					lstTrainingDto.add(trainingDto);
				}
			}
			else
			{
				// Do nothing return lstUsers as null
			}
		}
		catch(Exception e)
		{
			throw e;
		}
		return lstTrainingDto;
	}
	//This method get list of trainings fetched from OrgTrainingTxn table - > End
	
	//This method get list of trainings fetched from OrgTrainingTxn table for logged in trainer - > Start
	@Override
	public List<TrainingDto> getAllTrainingsForAttendance(long trainerCode) throws Exception
	{
		List<TrainingDto> lstTrainingDto = null;
		try
		{
			session = sessionFactory.getCurrentSession();
			Criteria c  = session.createCriteria(OrgTrainingTxn.class);
			c.add(Restrictions.eq("trainerCode", trainerCode));
			List<OrgTrainingTxn> lstTrainings = c.list();
			TrainingDto trainingDto = null;
			if(lstTrainings != null && !lstTrainings.isEmpty())
			{
				lstTrainingDto = new ArrayList<TrainingDto>();
				for(OrgTrainingTxn training : lstTrainings)
				{
					trainingDto = new TrainingDto();
					//copy properties from (source, target , ignoreProperties)
					String[] ignoreProperties = new String[]{"trainingStartTime","trainingEndTime"};
					BeanUtils.copyProperties(training , trainingDto , ignoreProperties);
					// Determine training status -> Start
					Date currDate = commonUtility.getCurrentDateFromDB();
					trainingDto.setTrainingStatus(CommonFunctions.determineTrainingStatus(training.getTrainingStartTime(), training.getTrainingEndTime(), training.getTrainingDate(), currDate));
					// Determine training status -> End
					lstTrainingDto.add(trainingDto);
				}
			}
			else
			{
				// Do nothing return lstUsers as null
			}
		}
		catch(Exception e)
		{
			throw e;
		}
		return lstTrainingDto;
	}
	//This method get list of trainings fetched from OrgTrainingTxn table for logged in trainer - > End
	
	//This method get list of trainings fetched from OrgTrainingTxn table for logged in userId for feedback - > Start
	@Override
	public List<TrainingDto> getAllTrainingsForFeedback() throws Exception
	{
		List<TrainingDto> lstTrainingDto = null;
		try
		{
		}
		catch(Exception e)
		{
			throw e;
		}
		return lstTrainingDto;
	}
	//This method get list of trainings fetched from OrgTrainingTxn table for logged in userId for feedback - > End
	
	//This method get list of trainers fetched from database - > Start
	@Override
	public List<TrainerDto> getAllTrainers(String trainername) throws Exception
	{
		List<TrainerDto> lstTrainers = null;
		try
		{
			hqlQueryString = " select user , trainer " +
					" from OrgUserMst as user , OrgTrainerMst as trainer " +
					" where user.userId = trainer.orgUserMstByUserId " +
         			" and user.activateFlag = trainer.activateFlag " +
         			" and user.activateFlag = 1 " +
         			" and lower(user.userName) like :trainername ";
			
			session = sessionFactory.getCurrentSession();
			hqlQuery = session.createQuery(hqlQueryString);
			hqlQuery.setString("trainername", "%"+trainername.toLowerCase()+"%");
			List<Object[]> resultList = hqlQuery.list();
			OrgUserMst user = null;
			OrgTrainerMst trainer = null;
			TrainerDto trainerDto = null;
			if(resultList != null && !resultList.isEmpty())
			{
				lstTrainers = new ArrayList<TrainerDto>();
				for(Object[] objArray : resultList)
				{
					user = (OrgUserMst)objArray[0];
					trainer = (OrgTrainerMst)objArray[1];
					trainerDto = new TrainerDto();
					BeanUtils.copyProperties(trainer, trainerDto);
					BeanUtils.copyProperties(user, trainerDto);
					lstTrainers.add(trainerDto);
				}
			}
			else
			{
				// Do nothing return lstUsers as null
			}
		}
		catch(Exception e)
		{
			throw e;
		}
		return lstTrainers;
	}
	//This method get list of users fetched from database - > End
	
	//This method get training fetched from orgTrainingTxn table(trainingCode) - > Start
	@Override
	public TrainingDto getTrainingByCode(long trainingCode) throws Exception
	{
		TrainingDto trainingDto = null;
		try
		{
			hqlQueryString = " select training , trainer , user " +
					" from OrgTrainingTxn as training , OrgTrainerMst as trainer , OrgUserMst as user " +
					" where training.trainerCode = trainer.trainerCode " +
					" and trainer.orgUserMstByUserId = user.userId " +
					" and training.trainingCode = :trainingCode " ;
			
			session = sessionFactory.getCurrentSession();
			hqlQuery = session.createQuery(hqlQueryString);
	     	hqlQuery.setLong("trainingCode", trainingCode);
			List<Object[]> resultList = hqlQuery.list();
			OrgTrainingTxn training = null;
			OrgTrainerMst trainer = null;
			OrgUserMst user = null;
			if(resultList != null && !resultList.isEmpty())
			{
				for(Object[] objArray : resultList)
				{
					training = (OrgTrainingTxn)objArray[0];
					trainer = (OrgTrainerMst)objArray[1];
					user = (OrgUserMst)objArray[2];
					trainingDto = new TrainingDto();
					//copy properties from (source, target , ignoreProperties)
					String[] ignoreProperties = new String[]{"trainingStartTime","trainingEndTime"};
					BeanUtils.copyProperties(training , trainingDto , ignoreProperties);
					trainingDto.setTrainerName(user.getUserName());
					// Convert time into HH:MM formst -> Start
					SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
					trainingDto.setTrainingStartTime(sdf.format(training.getTrainingStartTime()));
					trainingDto.setTrainingEndTime(sdf.format(training.getTrainingEndTime()));
					// Convert time into HH:MM formst -> End
					Date currDate = commonUtility.getCurrentDateFromDB();
					trainingDto.setTrainingStatus(CommonFunctions.determineTrainingStatus(training.getTrainingStartTime(), training.getTrainingEndTime(), training.getTrainingDate(), currDate));
				}
			}
			else
			{
				// Do nothing return trainingDto as null
			}
		}
		catch(Exception e)
		{
			throw e;
		}
		return trainingDto;
	}
	//This method get training fetched from orgTrainingTxn table(trainingCode) - > End
	
	//This method get training fetched from orgTrainingTxn table(trainingCode) - > Start
	@Override
	public OrgTrainingTxn getTrainingTxnByCode(long trainingCode) throws Exception
	{
		OrgTrainingTxn training = null;
		try
		{
			session = sessionFactory.getCurrentSession();
			Criteria c  = session.createCriteria(OrgTrainingTxn.class);
			c.add(Restrictions.eq("trainingCode", trainingCode));
			training = (OrgTrainingTxn)c.uniqueResult();
		}
		catch(Exception e)
		{
			throw e;
		}
		return training;
	}
	//This method get training fetched from orgTrainingTxn table(trainingCode) - > End
	
	//This method save  OrgTrainingTxn in database - > Start
	@Override
	public void save(OrgTrainingTxn training) throws Exception
	{
		try
		{
			session = sessionFactory.getCurrentSession();
			session.save(training);
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	//This method save  OrgTrainingTxn in database - > End
	
	//This method update  OrgTrainingTxn in database - > Start
	@Override
	public void update(OrgTrainingTxn training) throws Exception
	{
		try
		{
			session = sessionFactory.getCurrentSession();
			session.clear();
			session.update(training);
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	//This method update  OrgTrainingTxn in database - > End
	
	//This method gives next trainingCode from OrgTrainingTxn table - > Start
	@Override
	public Long getNextTrainingCode() throws Exception
	{
		Long trainingCode = null;
		try
		{
			session = sessionFactory.getCurrentSession();
			Criteria c  = session.createCriteria(OrgTrainingTxn.class);
			c.setProjection(Projections.max("trainingCode"));
			trainingCode = (Long)c.uniqueResult();
			if(trainingCode == null)
			{
				trainingCode = CommonConstants.DEFAULT_TRAINING_CODE + 1L;
			}
			else
			{
				trainingCode = trainingCode + 1L;
			}
		}
		catch(Exception e)
		{
			throw e;
		}
	    return trainingCode;
	}
	//This method gives next trainingCode from OrgTrainingTxn table - > End
	
	//This method gives maximum training serial no accoding to date from OrgTrainingTxn table - > Start
	@Override
	public Long getMaxTrainingSerialNo(Date trainingDate) throws Exception
	{
		Long trainingSerialNo = null;
		try
		{
			session = sessionFactory.getCurrentSession();
			Criteria c  = session.createCriteria(OrgTrainingTxn.class);
			c.add(Restrictions.eq("trainingDate", trainingDate));
			c.setProjection(Projections.max("trainingSerialNo"));
			trainingSerialNo = (Long)c.uniqueResult();
			if(trainingSerialNo == null)
			{
				trainingSerialNo = 1L;
			}
			else
			{
				trainingSerialNo = trainingSerialNo + 1L;
			}
		}
		catch(Exception e)
		{
			throw e;
		}
	    return trainingSerialNo;
	}
	//This method gives maximum training serial no accoding to date from OrgTrainingTxn table - > End
	
}
