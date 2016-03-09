/**
 * 
 */
package com.gargorg.Admin.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gargorg.Admin.service.LoginDetailsVOProviderService;

/**
 * @author piyush
 *
 */
@Transactional
@Repository
public class AccessControlDaoImpl implements AccessControlDao 
{
	private static final Logger LOGGER = LoggerFactory.getLogger(AccessControlDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private LoginDetailsVOProviderService loginDetailsVOProviderService;
	@Value("${commonElements}") 
	private String commonElements;
	
	Session session = null;
	Query hqlQuery = null;
	String hqlQueryString = null;
	
	@Override
	@Cacheable("opgCache")
	public List<String> getAllowedRoles(String requestUrl) throws Exception
	{
		List<String> allowedRoles = new ArrayList<String>();
		try
		{
			//Check whether requestUrl belongs to common elements -> Start
			boolean isCommonElement = false;
			String[] arrCommonElements = commonElements.split(",");
			for(int loopCount = 0 ; loopCount < arrCommonElements.length ; loopCount++)
			{
				if(requestUrl.equals(arrCommonElements[loopCount]))
				{
					isCommonElement = true;
				}
			}
			//Check whether requestUrl belongs to common elements -> End
			// Note : If requestUrl is common element then fetch all configured roles
			if(isCommonElement)
			{
				hqlQueryString = " select distinct roleDtls.roleName " +
						" from OrgRoleMst as role , OrgRoleDetailsRlt as roleDtls " +
						" where " +
						" role.roleCode = roleDtls.roleCode " +
						" and role.activateFlag = roleDtls.activateFlag " +
	         			" and role.activateFlag =  :activateFlag ";
			}
			else
			{
				hqlQueryString = " select distinct roleDtls.roleName " +
						" from OrgElementMst as ele , OrgRoleElementRlt as roleEle , OrgRoleMst as role , OrgRoleDetailsRlt as roleDtls  " +
						" where ele.elementCode = roleEle.elementCode " +
						" and roleEle.roleCode = role.roleCode " +
						" and role.roleCode = roleDtls.roleCode " +
	         			" and ele.activateFlag = roleEle.activateFlag " +
	         			" and ele.activateFlag =  role.activateFlag " +
	         			" and ele.activateFlag =  roleDtls.activateFlag " +
	         			" and ele.activateFlag =  :activateFlag " +
	         			" and ele.elementUrl like :requestUrl ";
			}
			
			session = sessionFactory.getCurrentSession();
			hqlQuery = session.createQuery(hqlQueryString);
			hqlQuery.setBoolean("activateFlag", true);
			if(!isCommonElement)
			{
				hqlQuery.setString("requestUrl", requestUrl+"%");
			}
			List<String> resultList = hqlQuery.list();
			if(resultList != null && !resultList.isEmpty())
			{
				allowedRoles = resultList;
			}
			else
			{
				allowedRoles.add("NO_ROLE_SPECIFIED");			// If no role is tagged with element then compare logged in user role with "NO_ROLE_SPECIFIED"
			}
		}
		catch(Exception e)
		{
			throw e;
		}
		return allowedRoles;
	}
}
