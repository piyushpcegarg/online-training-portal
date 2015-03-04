/**
 * 
 */
package com.gargorg.Masters.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gargorg.Admin.service.LoginDetailsVOProviderService;
import com.gargorg.Admin.valueObject.LoginDetailsVO;
import com.gargorg.Masters.dto.UserDto;
import com.gargorg.Masters.valueObject.OrgEmpMst;
import com.gargorg.Masters.valueObject.OrgUserImageMst;
import com.gargorg.Masters.valueObject.OrgUserMst;
import com.gargorg.Masters.valueObject.OrgUserRoleRlt;


/**
 * @author piyush
 *
 */
@Repository
public class UserDaoImpl implements UserDao 
{
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private LoginDetailsVOProviderService loginDetailsVOProviderService;
	
	Session session = null;
	Query hqlQuery = null;
	String hqlQueryString = null;
	private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoImpl.class);
	
	//This method get list of users fetched from database - > Start
	@Override
	public List<UserDto> getAllUsers() throws Exception
	{
		List<UserDto> lstUsers = null;
		try
		{
			LoginDetailsVO loginDetailsVO = loginDetailsVOProviderService.getLoginDetailsVO();	//Get LoginDetailsVO from Security Context holder
			
			hqlQueryString = " select emp , user " +
					" from OrgUserMst as user , OrgEmpMst as emp , CmnLanguageMst as lang " +
					" where user.userId = emp.orgUserMstByUserId " +
         			" and user.activateFlag = emp.activateFlag " +
         			" and emp.cmnLanguageMst =  lang.langId " +
         			" and lang.langId = :langId " +   
         			" and user.userId != 1 "				// Super Admin User cannot be edited.
         			;
			
			session = sessionFactory.getCurrentSession();
			hqlQuery = session.createQuery(hqlQueryString);
	     	hqlQuery.setLong("langId", loginDetailsVO.getLang().getLangId());
			List<Object[]> resultList = hqlQuery.list();
			OrgEmpMst emp = null;
			OrgUserMst user = null;
			UserDto userDto = null;
			if(resultList != null && !resultList.isEmpty())
			{
				lstUsers = new ArrayList<UserDto>();
				for(Object[] objArray : resultList)
				{
					emp = (OrgEmpMst)objArray[0];
					user = (OrgUserMst)objArray[1];
					userDto = new UserDto();
					userDto.setUserId(user.getUserId());
					userDto.setEngEmpFname(emp.getEmpFname());
					userDto.setEngEmpLname(emp.getEmpLname());
					userDto.setUserName(user.getUserName());
					userDto.setEmail(emp.getEmail());
					userDto.setRegMobileNo(user.getRegMobileNo());
					userDto.setActivateFlag(user.isActivateFlag());
					lstUsers.add(userDto);
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
		return lstUsers;
	}
	//This method get list of users fetched from database - > End
	
	//This method save  OrgUserMst , List<OrgEmpMst> , OrgUserImageMst , List<OrgUserRoleRlt> in database - > Start
	@Override
	public void save(OrgUserMst user , List<OrgEmpMst> lstOrgEmpMst , OrgUserImageMst userImage , List<OrgUserRoleRlt> lstOrgUserRoleRlt) throws Exception
	{
		try
		{
			session = sessionFactory.getCurrentSession();
			session.save(user);
			for(OrgEmpMst orgEmpMst : lstOrgEmpMst)
			{
				session.save(orgEmpMst);
			}
			session.save(userImage);
			for(OrgUserRoleRlt orgUserRoleRlt : lstOrgUserRoleRlt)
			{
				session.save(orgUserRoleRlt);
			}
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	//This method save  OrgUserMst , List<OrgEmpMst> , OrgUserImageMst , List<OrgUserRoleRlt> in database - > End
	
	//This method update  OrgUserMst , List<OrgEmpMst> , OrgUserImageMst in database - > Start
	@Override
	public void update(OrgUserMst user , List<OrgEmpMst> lstOrgEmpMst , OrgUserImageMst userImage) throws Exception
	{
		try
		{
			session = sessionFactory.getCurrentSession();
			session.update(user);
			for(OrgEmpMst orgEmpMst : lstOrgEmpMst)
			{
				session.update(orgEmpMst);
			}
			session.update(userImage);
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	//This method update  OrgUserMst , List<OrgEmpMst> , OrgUserImageMst in database - > End
	
	//This method get user fetched from orgUserMst table(userId) - > Start
	@Override
	public OrgUserMst getUserById(long userId) throws Exception
	{
		OrgUserMst user = null;
		try
		{
			session = sessionFactory.getCurrentSession();
			Criteria c  = session.createCriteria(OrgUserMst.class);
			c.add(Restrictions.eq("userId", userId));
			user = (OrgUserMst)c.uniqueResult();
		}
		catch(Exception e)
		{
			throw e;
		}
		return user;
	}
	//This method get user fetched from orgUserMst table(userId) - > End
	
	//This method get list of Employees fetched from OrgEmpMst table(userId) - > Start
	@Override
	public List<OrgEmpMst> getEmployeeById(long userId) throws Exception
	{
		List<OrgEmpMst> lstEmployees = null;
		try
		{
			session = sessionFactory.getCurrentSession();
			Criteria c  = session.createCriteria(OrgEmpMst.class);
			c.add(Restrictions.eq("orgUserMstByUserId.userId", userId));
			c.addOrder(Order.asc("cmnLanguageMst.langId"));
			lstEmployees = c.list();		
		}
		catch(Exception e)
		{
			throw e;
		}
		return lstEmployees;
	}
	//This method get list of Employees fetched from OrgEmpMst table(userId) - > End
	
	//This method get user fetched from orgUserMst table(userId) - > Start
	@Override
	public OrgUserImageMst getUserImageById(long userId) throws Exception
	{
		OrgUserImageMst userImage = null;
		try
		{
			session = sessionFactory.getCurrentSession();
			Criteria c  = session.createCriteria(OrgUserImageMst.class);
			c.add(Restrictions.eq("orgUserMstByUserId.userId", userId));
			userImage = (OrgUserImageMst)c.uniqueResult();
		}
		catch(Exception e)
		{
			throw e;
		}
		return userImage;
	}
	//This method get user fetched from orgUserMst table(userId) - > End
	
	//This method get list of Role attached to user fetched from OrgUserRoleRlt table(userId) - > Start
	@Override
	public List<OrgUserRoleRlt> getUserRoleById(long userId) throws Exception
	{
		List<OrgUserRoleRlt> lstUserRoleRlt = null;
		try
		{
			session = sessionFactory.getCurrentSession();
			LoginDetailsVO loginDetailsVO = loginDetailsVOProviderService.getLoginDetailsVO();	//Get LoginDetailsVO from Security Context holder
			
			Criteria c  = session.createCriteria(OrgUserRoleRlt.class);
			c.add(Restrictions.eq("orgUserMstByUserId.userId", userId));
			c.add(Restrictions.eq("activateFlag", loginDetailsVO.getUser().isActivateFlag()));
			
			lstUserRoleRlt = c.list();		
		}
		catch(Exception e)
		{
			throw e;
		}
		return lstUserRoleRlt;
	}
	//This method get list of Role attached to user fetched from OrgUserRoleRlt table(userId) - > End
	
	//This method get user fetched from orgUserMst table(mobileNo) - > Start
	@Override
	public OrgUserMst getUserByMobileno(String mobileNo) throws Exception
	{
		OrgUserMst user = null;
		try
		{
			session = sessionFactory.getCurrentSession();
			Criteria c  = session.createCriteria(OrgUserMst.class);
			c.add(Restrictions.eq("regMobileNo", Long.valueOf(mobileNo)));
			user = (OrgUserMst)c.uniqueResult();
		}
		catch(Exception e)
		{
			throw e;
		}
		return user;
	}
	//This method get user fetched from orgUserMst table(mobileNo) - > End
	
}
