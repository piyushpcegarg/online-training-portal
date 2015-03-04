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
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gargorg.Admin.service.LoginDetailsVOProviderService;
import com.gargorg.common.constant.CommonConstants;
import com.trainingportal.Masters.dto.SkillDto;
import com.trainingportal.Masters.valueObject.OrgSkillMst;


/**
 * @author piyush
 *
 */
@Repository
public class SkillDaoImpl implements SkillDao 
{
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private LoginDetailsVOProviderService loginDetailsVOProviderService;
	
	Session session = null;
	Query hqlQuery = null;
	String hqlQueryString = null;
	private static final Logger LOGGER = LoggerFactory.getLogger(SkillDaoImpl.class);
	
	//This method get list of skills fetched from skills table - > Start
	@Override
	public List<SkillDto> getAllSkills() throws Exception
	{
		List<SkillDto> lstSkillDto = null;
		try
		{
			List<OrgSkillMst> lstOrgSkillMst = null;
			SkillDto skillDto = null;
			session = sessionFactory.getCurrentSession();
			
			Criteria c  = session.createCriteria(OrgSkillMst.class);
			lstOrgSkillMst = c.list();
			
			lstSkillDto = new ArrayList<SkillDto>();
			for(OrgSkillMst skill : lstOrgSkillMst)
			{
				skillDto = new SkillDto();
				// Copy Properties -> Start
				BeanUtils.copyProperties(skill , skillDto);
				// Copy Properties -> End
				lstSkillDto.add(skillDto);
			}
		}
		catch(Exception e)
		{
			throw e;
		}
		return lstSkillDto;
	}
	//This method get list of skills fetched from skills table - > End
	
	//This method get skill fetched from skills table(skillCode) - > Start
	@Override
	public OrgSkillMst getSkillByCode(long skillCode) throws Exception
	{
		OrgSkillMst skill = null;
		try
		{
			session = sessionFactory.getCurrentSession();
			Criteria c  = session.createCriteria(OrgSkillMst.class);
			c.add(Restrictions.eq("skillCode", skillCode));
			skill = (OrgSkillMst)c.uniqueResult();
		}
		catch(Exception e)
		{
			throw e;
		}
		return skill;
	}
	//This method get list of skills fetched from skills table(skillCode) - > End
	
	//This method save list of skills in skills table - > Start
	@Override
	public void save(OrgSkillMst skill) throws Exception
	{
		try
		{
			session = sessionFactory.getCurrentSession();
			session.save(skill);
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	//This method save list of skills in skills table - > End
	
	//This method update list of skills in skills table - > Start
	@Override
	public void update(OrgSkillMst skill) throws Exception
	{
		try
		{
			session = sessionFactory.getCurrentSession();
			session.update(skill);
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	//This method update list of skills in skills table - > End
	
	//This method gives next skillCode from skills table - > Start
	@Override
	public Long getNextSkillCode() throws Exception
	{
		Long skillCode = null;
		try
		{
			session = sessionFactory.getCurrentSession();
			Criteria c  = session.createCriteria(OrgSkillMst.class);
			c.setProjection(Projections.max("skillCode"));
			skillCode = (Long)c.uniqueResult();
			if(skillCode == null)
			{
				skillCode = CommonConstants.DEFAULT_SKILL_CODE + 1L;
			}
			else
			{
				skillCode = skillCode + 1L;
			}
		}
		catch(Exception e)
		{
			throw e;
		}
	    return skillCode;
	}
	//This method gives next skillCode from skills table - > End
	
	//This method get Skill fetched from OrgSkillMst table - > Start
	@Override
	public OrgSkillMst getSkillByName(String skillName) throws Exception
	{
		OrgSkillMst skill = null;
		try
		{
			session = sessionFactory.getCurrentSession();
			
			Criteria c  = session.createCriteria(OrgSkillMst.class);
			c.add(Restrictions.eq("skillName", skillName).ignoreCase());
			
			skill = (OrgSkillMst)c.uniqueResult();
		}
		catch(Exception e)
		{
			throw e;
		}
		return skill;
	}
	//This method get Skill fetched from OrgSkillMst table - > End
}
