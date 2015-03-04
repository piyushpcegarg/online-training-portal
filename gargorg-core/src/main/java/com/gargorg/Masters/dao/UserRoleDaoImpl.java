/**
 * 
 */
package com.gargorg.Masters.dao;

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
import com.gargorg.Masters.valueObject.OrgUserRoleRlt;


/**
 * @author piyush
 *
 */
@Repository
public class UserRoleDaoImpl implements UserRoleDao 
{
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private LoginDetailsVOProviderService loginDetailsVOProviderService;
	
	Session session = null;
	private static final Logger LOGGER = LoggerFactory.getLogger(UserRoleDaoImpl.class);
	
	//This method get list of userRoles fetched from userRoles table - > Start
	@Override
	public List<OrgUserRoleRlt> getAllUserRoles(long userId) throws Exception
	{
		List<OrgUserRoleRlt> lstUserRoles = null;
		try
		{
			session = sessionFactory.getCurrentSession();
			
			Criteria c  = session.createCriteria(OrgUserRoleRlt.class);
			c.add(Restrictions.eq("orgUserMstByUserId.userId", userId));
			c.add(Restrictions.eq("activateFlag", true));
			c.addOrder(Order.asc("roleCode"));
			
			lstUserRoles = c.list();		
		}
		catch(Exception e)
		{
			throw e;
		}
		return lstUserRoles;
	}
	//This method get list of userRoles fetched from userRoles table - > End
	
	//This method delete list of userRoles and insert list of userRoles mapped to roleCode - > Start
	@Override
	public void updateAllUserRoles(List<OrgUserRoleRlt> lstUserRoleRltPersistent , List<OrgUserRoleRlt> lstOrgUserRoleRlt) throws Exception
	{
		try
		{
			session = sessionFactory.getCurrentSession();
			for(OrgUserRoleRlt orgUserRoleRlt : lstUserRoleRltPersistent)
			{
				session.delete(orgUserRoleRlt);
			}
			session.flush();
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
	//This method delete list of userRoles and insert list of userRoles mapped to roleCode - > End
}
