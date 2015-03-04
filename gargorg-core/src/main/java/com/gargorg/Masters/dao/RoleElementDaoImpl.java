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
import com.gargorg.Masters.valueObject.OrgRoleElementRlt;


/**
 * @author piyush
 *
 */
@Repository
public class RoleElementDaoImpl implements RoleElementDao 
{
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private LoginDetailsVOProviderService loginDetailsVOProviderService;
	
	Session session = null;
	private static final Logger LOGGER = LoggerFactory.getLogger(RoleElementDaoImpl.class);
	
	//This method get list of roleElements fetched from roleElements table - > Start
	@Override
	public List<OrgRoleElementRlt> getAllRoleElements(long roleCode) throws Exception
	{
		List<OrgRoleElementRlt> lstRoleElements = null;
		try
		{
			session = sessionFactory.getCurrentSession();
			
			Criteria c  = session.createCriteria(OrgRoleElementRlt.class);
			c.add(Restrictions.eq("roleCode", roleCode));
			c.add(Restrictions.eq("activateFlag", true));
			c.addOrder(Order.asc("elementCode"));
			
			lstRoleElements = c.list();		
		}
		catch(Exception e)
		{
			throw e;
		}
		return lstRoleElements;
	}
	//This method get list of roleElements fetched from roleElements table - > End
	
	//This method delete list of roleElements and insert list of roleElements mapped to roleCode - > Start
	@Override
	public void updateAllRoleElements(List<OrgRoleElementRlt> lstRoleElementRltPersistent , List<OrgRoleElementRlt> lstOrgRoleElementRlt) throws Exception
	{
		try
		{
			session = sessionFactory.getCurrentSession();
			for(OrgRoleElementRlt orgRoleElementRlt : lstRoleElementRltPersistent)
			{
				session.delete(orgRoleElementRlt);
			}
			session.flush();
			for(OrgRoleElementRlt orgRoleElementRlt : lstOrgRoleElementRlt)
			{
				session.save(orgRoleElementRlt);
			}
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	//This method delete list of roleElements and insert list of roleElements mapped to roleCode - > End
}
