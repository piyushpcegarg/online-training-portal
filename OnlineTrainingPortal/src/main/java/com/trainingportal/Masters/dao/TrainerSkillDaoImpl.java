/**
 * 
 */
package com.trainingportal.Masters.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gargorg.Admin.service.LoginDetailsVOProviderService;
import com.trainingportal.Masters.valueObject.OrgTrainerSkillMpg;


/**
 * @author piyush
 *
 */
@Repository
public class TrainerSkillDaoImpl implements TrainerSkillDao 
{
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private LoginDetailsVOProviderService loginDetailsVOProviderService;
	
	Session session = null;
	private static final Logger LOGGER = LoggerFactory.getLogger(TrainerSkillDaoImpl.class);
	
	//This method get list of trainerSkills fetched from trainerSkills table - > Start
	@Override
	public List<OrgTrainerSkillMpg> getAllTrainerSkills(long trainerCode) throws Exception
	{
		List<OrgTrainerSkillMpg> lstTrainerSkills = null;
		try
		{
			session = sessionFactory.getCurrentSession();
			
			Criteria c  = session.createCriteria(OrgTrainerSkillMpg.class);
			c.add(Restrictions.eq("trainerCode", trainerCode));
			c.add(Restrictions.eq("activateFlag", true));
			c.addOrder(Order.asc("skillCode"));
			
			lstTrainerSkills = c.list();		
		}
		catch(Exception e)
		{
			throw e;
		}
		return lstTrainerSkills;
	}
	//This method get list of trainerSkills fetched from trainerSkills table - > End
	
	//This method delete list of trainerSkills and insert list of trainerSkills mapped to trainerCode - > Start
	@Override
	public void updateAllTrainerSkills(List<OrgTrainerSkillMpg> lstTrainerSkillMpgPersistent , List<OrgTrainerSkillMpg> lstOrgTrainerSkillMpg) throws Exception
	{
		try
		{
			session = sessionFactory.getCurrentSession();
			for(OrgTrainerSkillMpg orgTrainerSkillMpg : lstTrainerSkillMpgPersistent)
			{
				session.delete(orgTrainerSkillMpg);
			}
			session.flush();
			for(OrgTrainerSkillMpg orgTrainerSkillMpg : lstOrgTrainerSkillMpg)
			{
				session.save(orgTrainerSkillMpg);
			}
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	//This method delete list of trainerSkills and insert list of trainerSkills mapped to trainerCode - > End
}
