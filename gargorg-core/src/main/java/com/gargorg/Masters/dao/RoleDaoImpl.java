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
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gargorg.Admin.service.LoginDetailsVOProviderService;
import com.gargorg.Admin.valueObject.LoginDetailsVO;
import com.gargorg.Masters.dto.OrgRoleMstDto;
import com.gargorg.Masters.valueObject.OrgRoleDetailsRlt;
import com.gargorg.Masters.valueObject.OrgRoleMst;
import com.gargorg.common.constant.CommonConstants;


/**
 * @author piyush
 *
 */
@Repository
public class RoleDaoImpl implements RoleDao 
{
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private LoginDetailsVOProviderService loginDetailsVOProviderService;
	
	Session session = null;
	Query hqlQuery = null;
	String hqlQueryString = null;
	private static final Logger LOGGER = LoggerFactory.getLogger(RoleDaoImpl.class);
	
	//This method get list of roles fetched from roles table - > Start
	@Override
	public List<OrgRoleMstDto> getAllRoles() throws Exception
	{
		List<OrgRoleMstDto> lstRoleDto = null;
		try
		{
			LoginDetailsVO loginDetailsVO = loginDetailsVOProviderService.getLoginDetailsVO();	//Get LoginDetailsVO from Security Context holder
			
			hqlQueryString = " select role , roleDtls " +
					" from OrgRoleMst as role , OrgRoleDetailsRlt as roleDtls , CmnLanguageMst as lang " +
					" where role.roleCode = roleDtls.roleCode " +
	     			" and role.activateFlag = roleDtls.activateFlag " +
	     			" and roleDtls.cmnLanguageMst =  lang.langId " +
	     			" and role.roleCode != 101 " +			// Super admin role for proprietor
	     			" and lang.langId = :langId " +
	     			" order by role.roleCode ";
			
			session = sessionFactory.getCurrentSession();
			hqlQuery = session.createQuery(hqlQueryString);
			hqlQuery.setLong("langId", loginDetailsVO.getLang().getLangId());
			List<Object[]> resultList = hqlQuery.list();
			if(resultList != null && !resultList.isEmpty())
			{
				lstRoleDto = new ArrayList<OrgRoleMstDto>();
				OrgRoleMst orgRoleMst = null;
				OrgRoleDetailsRlt orgRoleDetailsRlt = null;
				OrgRoleMstDto orgRoleMstDto = null;
				for(Object[] objArray : resultList)
				{
					orgRoleMst = (OrgRoleMst)objArray[0]; 
					orgRoleDetailsRlt = (OrgRoleDetailsRlt)objArray[1];
					orgRoleMstDto = new OrgRoleMstDto();
					// Copy Properties -> Start
					BeanUtils.copyProperties(orgRoleDetailsRlt, orgRoleMstDto);
					BeanUtils.copyProperties(orgRoleMst, orgRoleMstDto);
					// Copy Properties -> End
					lstRoleDto.add(orgRoleMstDto);
				}
			}
			else
			{
				// Do nothing return lstRoleDto as null
			}
		}
		catch(Exception e)
		{
			throw e;
		}
		return lstRoleDto;
	}
	//This method get list of roles fetched from roles table - > End
	
	//This method get list of roles fetched from roles table(roleCode) - > Start
	@Override
	public List<OrgRoleMstDto> getRoleByCode(long roleCode) throws Exception
	{
		List<OrgRoleMstDto> lstRoleDto = null;
		try
		{
			hqlQueryString = " select role , roleDtls " +
					" from OrgRoleMst as role , OrgRoleDetailsRlt as roleDtls " +
					" where role.roleCode = roleDtls.roleCode " +
	     			" and role.activateFlag = roleDtls.activateFlag " +
	     			" and role.roleCode = :roleCode " +			
	     			" order by roleDtls.cmnLanguageMst.langId ";
			
			session = sessionFactory.getCurrentSession();
			hqlQuery = session.createQuery(hqlQueryString);
			hqlQuery.setLong("roleCode", roleCode);
			List<Object[]> resultList = hqlQuery.list();
			if(resultList != null && !resultList.isEmpty())
			{
				lstRoleDto = new ArrayList<OrgRoleMstDto>();
				OrgRoleMst orgRoleMst = null;
				OrgRoleDetailsRlt orgRoleDetailsRlt = null;
				OrgRoleMstDto orgRoleMstDto = null;
				for(Object[] objArray : resultList)
				{
					orgRoleMst = (OrgRoleMst)objArray[0]; 
					orgRoleDetailsRlt = (OrgRoleDetailsRlt)objArray[1];
					orgRoleMstDto = new OrgRoleMstDto();
					// Copy Properties -> Start
					BeanUtils.copyProperties(orgRoleDetailsRlt, orgRoleMstDto);
					BeanUtils.copyProperties(orgRoleMst, orgRoleMstDto);
					// Copy Properties -> End
					lstRoleDto.add(orgRoleMstDto);
				}
			}
			else
			{
				// Do nothing return lstRoleDto as null
			}
		}
		catch(Exception e)
		{
			throw e;
		}
		return lstRoleDto;
	}
	//This method get list of roles fetched from roles table(roleCode) - > End
	
	//This method save list of roles in roles table - > Start
	@Override
	public void save(OrgRoleMst role , List<OrgRoleDetailsRlt> lstOrgRoleDetailsRlt) throws Exception
	{
		try
		{
			session = sessionFactory.getCurrentSession();
			session.save(role);
			for(OrgRoleDetailsRlt orgRoleDetailsRlt : lstOrgRoleDetailsRlt)
			{
				session.save(orgRoleDetailsRlt);
			}
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	//This method save list of roles in roles table - > End
	
	//This method update list of roles in roles table - > Start
	@Override
	public void update(OrgRoleMst role , List<OrgRoleDetailsRlt> lstOrgRoleDetailsRlt) throws Exception
	{
		try
		{
			session = sessionFactory.getCurrentSession();
			session.clear();
			session.update(role);
			for(OrgRoleDetailsRlt orgRoleDetailsRlt : lstOrgRoleDetailsRlt)
			{
				session.update(orgRoleDetailsRlt);
			}
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	//This method update list of roles in roles table - > End
	
	//This method gives next roleCode from roles table - > Start
	@Override
	public Long getNextRoleCode() throws Exception
	{
		Long roleCode = null;
		try
		{
			session = sessionFactory.getCurrentSession();
			Criteria c  = session.createCriteria(OrgRoleMst.class);
			c.setProjection(Projections.max("roleCode"));
			roleCode = (Long)c.uniqueResult();
			if(roleCode == null)
			{
				roleCode = CommonConstants.DEFAULT_ROLE_CODE + 1L;
			}
			else
			{
				roleCode = roleCode + 1L;
			}
		}
		catch(Exception e)
		{
			throw e;
		}
	    return roleCode;
	}
	//This method gives next roleCode from roles table - > End
	
	//This method get Role fetched from OrgRoleDetailsRlt table - > Start
	@Override
	public OrgRoleDetailsRlt getRoleByName(String roleName) throws Exception
	{
		OrgRoleDetailsRlt orgRoleDetailsRlt = null;
		try
		{
			session = sessionFactory.getCurrentSession();
			
			Criteria c  = session.createCriteria(OrgRoleDetailsRlt.class);
			c.add(Restrictions.eq("roleName", roleName).ignoreCase());
			
			orgRoleDetailsRlt = (OrgRoleDetailsRlt)c.uniqueResult();
		}
		catch(Exception e)
		{
			throw e;
		}
		return orgRoleDetailsRlt;
	}
	//This method get Role fetched from OrgRoleDetailsRlt table - > End
}
