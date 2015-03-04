/**
 * 
 */
package com.trainingportal.Transactions.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gargorg.Admin.service.LoginDetailsVOProviderService;
import com.gargorg.Admin.valueObject.LoginDetailsVO;
import com.gargorg.Masters.valueObject.OrgEmpMst;
import com.gargorg.Masters.valueObject.OrgUserMst;
import com.gargorg.common.Utils.CommonUtility;
import com.trainingportal.Transactions.dto.TraineeDto;
import com.trainingportal.Transactions.valueObject.OrgTrainingTraineeMpg;
import com.trainingportal.Transactions.valueObject.OrgTrainingTxn;


/**
 * @author piyush
 *
 */
@Repository
public class TraineeDaoImpl implements TraineeDao 
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
	private static final Logger LOGGER = LoggerFactory.getLogger(TraineeDaoImpl.class);
	
	//This method get list of trainees fetched from database - > Start
	@Override
	public List<TraineeDto> getAllTrainees(long trainingCode) throws Exception
	{
		List<TraineeDto> lstTrainees = null;
		try
		{
			LoginDetailsVO loginDetailsVO = loginDetailsVOProviderService.getLoginDetailsVO();	//Get LoginDetailsVO from Security Context holder
			
			hqlQueryString = " select traineeMpg , training , emp , user " +
					" from OrgTrainingTraineeMpg as traineeMpg , OrgTrainingTxn as training , OrgUserMst as user , OrgEmpMst as emp , CmnLanguageMst as lang " +
					" where traineeMpg.trainingCode = training.trainingCode " +
					" and traineeMpg.orgUserMstByTraineeId = user.userId " +
					" and user.userId = emp.orgUserMstByUserId " +
         			" and user.activateFlag = emp.activateFlag " +
         			" and emp.cmnLanguageMst =  lang.langId " +
         			" and lang.langId = :langId " +   
         			" and traineeMpg.trainingCode = :trainingCode "
         			;
			
			session = sessionFactory.getCurrentSession();
			hqlQuery = session.createQuery(hqlQueryString);
	     	hqlQuery.setLong("langId", loginDetailsVO.getLang().getLangId());
	     	hqlQuery.setLong("trainingCode",trainingCode);
			List<Object[]> resultList = hqlQuery.list();
			OrgTrainingTraineeMpg traineeMpg = null;
			OrgTrainingTxn training = null;
			OrgEmpMst emp = null;
			OrgUserMst user = null;
			TraineeDto traineeDto = null;
			if(resultList != null && !resultList.isEmpty())
			{
				lstTrainees = new ArrayList<TraineeDto>();
				for(Object[] objArray : resultList)
				{
					traineeMpg = (OrgTrainingTraineeMpg)objArray[0];
					training = (OrgTrainingTxn)objArray[1];
					emp = (OrgEmpMst)objArray[2];
					user = (OrgUserMst)objArray[3];
					traineeDto = new TraineeDto();
					traineeDto.setTrainingCode(training.getTrainerCode());
					traineeDto.setTrainingName(training.getTrainingName());
					traineeDto.setCourseName(training.getCourseName());
					traineeDto.setTrainingDate(training.getTrainingDate());
					traineeDto.setUserId(user.getUserId());
					traineeDto.setEmpFname(emp.getEmpFname());
					traineeDto.setEmpLname(emp.getEmpLname());
					traineeDto.setUserName(user.getUserName());
					traineeDto.setRegMobileNo(user.getRegMobileNo());
					traineeDto.setActivateFlag(traineeMpg.isActivateFlag());
					lstTrainees.add(traineeDto);
				}
			}
			else
			{
				// Do nothing return lstTrainees as null
			}
		}
		catch(Exception e)
		{
			throw e;
		}
		return lstTrainees;
	}
	//This method get list of trainees fetched from database - > End
	
	//This method delete selected trainees from the training(OrgTrainingTraineeMpg) in database - > Start
	@Override
	public boolean deleteTrainee(long trainingCode, List<Long> lstTraineeId) throws Exception
	{
		try
		{
			session = sessionFactory.getCurrentSession();
			Criteria c  = session.createCriteria(OrgTrainingTraineeMpg.class);
			c.add(Restrictions.eq("trainingCode", trainingCode));
			c.add(Restrictions.in("orgUserMstByTraineeId", lstTraineeId));
			List<OrgTrainingTraineeMpg> lstTrainingTraineeMpg = c.list();
			for(OrgTrainingTraineeMpg orgTrainingTraineeMpg : lstTrainingTraineeMpg)
			{
				session.delete(orgTrainingTraineeMpg);
			}
			return true;
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	//This method delete selected trainees from the training(OrgTrainingTraineeMpg) in database - > End
}