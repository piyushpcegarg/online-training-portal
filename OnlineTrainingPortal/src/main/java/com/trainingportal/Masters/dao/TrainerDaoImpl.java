/**
 * 
 */
package com.trainingportal.Masters.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gargorg.Admin.service.LoginDetailsVOProviderService;
import com.gargorg.Admin.valueObject.LoginDetailsVO;
import com.gargorg.Masters.dto.UserDto;
import com.gargorg.Masters.valueObject.OrgEmpMst;
import com.gargorg.Masters.valueObject.OrgUserMst;
import com.gargorg.common.constant.CommonConstants;
import com.trainingportal.Masters.dto.TrainerDto;
import com.trainingportal.Masters.valueObject.OrgTrainerMst;


/**
 * @author piyush
 *
 */
@Repository
public class TrainerDaoImpl implements TrainerDao 
{
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private LoginDetailsVOProviderService loginDetailsVOProviderService;
	
	Session session = null;
	Query hqlQuery = null;
	String hqlQueryString = null;
	private static final Logger LOGGER = LoggerFactory.getLogger(TrainerDaoImpl.class);
	
	//This method get list of trainers fetched from database - > Start
	@Override
	public List<TrainerDto> getAllTrainers() throws Exception
	{
		List<TrainerDto> lstTrainers = null;
		try
		{
			LoginDetailsVO loginDetailsVO = loginDetailsVOProviderService.getLoginDetailsVO();	//Get LoginDetailsVO from Security Context holder
			
			hqlQueryString = " select emp , user , trainer " +
					" from OrgUserMst as user , OrgEmpMst as emp , CmnLanguageMst as lang , OrgTrainerMst trainer " +
					" where user.userId = emp.orgUserMstByUserId " +
					" and user.userId = trainer.orgUserMstByUserId " +
         			" and user.activateFlag = emp.activateFlag " +
         			" and emp.cmnLanguageMst =  lang.langId " +
         			" and lang.langId = :langId "   
         			;
			
			session = sessionFactory.getCurrentSession();
			hqlQuery = session.createQuery(hqlQueryString);
	     	hqlQuery.setLong("langId", loginDetailsVO.getLang().getLangId());
			List<Object[]> resultList = hqlQuery.list();
			OrgEmpMst emp = null;
			OrgUserMst user = null;
			OrgTrainerMst trainer = null;
			TrainerDto trainerDto = null;
			if(resultList != null && !resultList.isEmpty())
			{
				lstTrainers = new ArrayList<TrainerDto>();
				for(Object[] objArray : resultList)
				{
					emp = (OrgEmpMst)objArray[0];
					user = (OrgUserMst)objArray[1];
					trainer = (OrgTrainerMst)objArray[2];
					trainerDto = new TrainerDto();
					
					trainerDto.setTrainerCode(trainer.getTrainerCode());
					trainerDto.setUserId(user.getUserId());
					trainerDto.setUserName(user.getUserName());
					trainerDto.setEmpGender(emp.getEmpGender());
					trainerDto.setEngEmpFname(emp.getEmpFname());
					trainerDto.setEngEmpMname(emp.getEmpMname());
					trainerDto.setEngEmpLname(emp.getEmpLname());
					trainerDto.setEmpDob(emp.getEmpDob());
					trainerDto.setEmpDoj(emp.getEmpDoj());
					trainerDto.setEmail(emp.getEmail());
					trainerDto.setRegMobileNo(user.getRegMobileNo());
					trainerDto.setActivateFlag(trainer.isActivateFlag());
					trainerDto.setOtpEnabled(user.isOtpEnabled());
					lstTrainers.add(trainerDto);
				}
			}
			else
			{
				// Do nothing return lstTrainers as null
			}
		}
		catch(Exception e)
		{
			throw e;
		}
		return lstTrainers;
	}
	//This method get list of trainers fetched from database - > End
	
	//This method save  OrgTrainerMst in database - > Start
	@Override
	public void save(OrgTrainerMst trainer) throws Exception
	{
		try
		{
			session = sessionFactory.getCurrentSession();
			session.save(trainer);
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	//This method save  OrgTrainerMst in database - > End
	
	//This method update  OrgTrainerMst - > Start
	@Override
	public void update(OrgTrainerMst trainer) throws Exception
	{
		try
		{
			session = sessionFactory.getCurrentSession();
			session.clear();
			session.update(trainer);
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	//This method update  OrgTrainerMst - > End
	
	//This method get trainer fetched from orgTrainerMst table(trainerCode) - > Start
	@Override
	public OrgTrainerMst getTrainerByCode(long trainerCode) throws Exception
	{
		OrgTrainerMst trainer = null;
		try
		{
			session = sessionFactory.getCurrentSession();
			Criteria c  = session.createCriteria(OrgTrainerMst.class);
			c.add(Restrictions.eq("trainerCode", trainerCode));
			trainer = (OrgTrainerMst)c.uniqueResult();
		}
		catch(Exception e)
		{
			throw e;
		}
		return trainer;
	}
	//This method get trainer fetched from orgTrainerMst table(trainerId) - > End
	
	
	//This method gives next trainerCode from OrgTrainerMst table - > Start
	@Override
	public Long getNextTrainerCode() throws Exception
	{
		Long trainerCode = null;
		try
		{
			session = sessionFactory.getCurrentSession();
			Criteria c  = session.createCriteria(OrgTrainerMst.class);
			c.setProjection(Projections.max("trainerCode"));
			trainerCode = (Long)c.uniqueResult();
			if(trainerCode == null)
			{
				trainerCode = CommonConstants.DEFAULT_TRAINER_CODE + 1L;
			}
			else
			{
				trainerCode = trainerCode + 1L;
			}
		}
		catch(Exception e)
		{
			throw e;
		}
	    return trainerCode;
	}
	//This method gives next trainerCode from OrgTrainerMst table - > End
	
	//This method get trainer fetched from orgTrainerMst table(userId) - > Start
	@Override
	public OrgTrainerMst getTrainerByUserId(long userId) throws Exception
	{
		OrgTrainerMst trainer = null;
		try
		{
			session = sessionFactory.getCurrentSession();
			Criteria c  = session.createCriteria(OrgTrainerMst.class);
			c.add(Restrictions.eq("orgUserMstByUserId.userId", userId));
			trainer = (OrgTrainerMst)c.uniqueResult();
		}
		catch(Exception e)
		{
			throw e;
		}
		return trainer;
	}
	//This method get trainer fetched from orgTrainerMst table(userId) - > End
	
	//This method get list of users fetched from database - > Start
	@Override
	public List<UserDto> getAllUsers(String username) throws Exception
	{
		List<UserDto> lstUserDto = null;
		try
		{
			session = sessionFactory.getCurrentSession();
			Criteria c  = session.createCriteria(OrgUserMst.class);
			c.add(Restrictions.ilike("userName", username , MatchMode.ANYWHERE));
			c.add(Restrictions.eq("activateFlag", true));
			List<OrgUserMst> lstUsers = c.list();
			if(lstUsers != null && !lstUsers.isEmpty())
			{
				lstUserDto = new ArrayList<UserDto>();
				for(OrgUserMst user : lstUsers)
				{
					UserDto userDto = new UserDto();
					BeanUtils.copyProperties(user, userDto);
					lstUserDto.add(userDto);
				}
			}
		}
		catch(Exception e)
		{
			throw e;
		}
		return lstUserDto;
	}
	//This method get list of users fetched from database - > End
}
