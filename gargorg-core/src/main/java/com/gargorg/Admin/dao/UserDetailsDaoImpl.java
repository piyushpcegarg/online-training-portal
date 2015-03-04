/**
 * 
 */
package com.gargorg.Admin.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Repository;

import com.gargorg.Admin.valueObject.LoginDetailsVO;
import com.gargorg.Masters.valueObject.CmnLanguageMst;
import com.gargorg.Masters.valueObject.OrgEmpMst;
import com.gargorg.Masters.valueObject.OrgRoleDetailsRlt;
import com.gargorg.Masters.valueObject.OrgRoleMst;
import com.gargorg.Masters.valueObject.OrgUserImageMst;
import com.gargorg.Masters.valueObject.OrgUserMst;
import com.gargorg.Masters.valueObject.OrgUserRoleRlt;

/**
 * @author piyush
 *
 */
@Repository
public class UserDetailsDaoImpl implements UserDetailsDao 
{
	private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	Session session = null;
	Query hqlQuery = null;
	String hqlQueryString = null;
	
	@Override
	public OrgUserMst findByUsername(String username)
	{
		OrgUserMst user = null;
		try
		{
			session = sessionFactory.getCurrentSession();
			Criteria objCrt = session.createCriteria(OrgUserMst.class);
		    objCrt.add(Restrictions.eq("userName", username).ignoreCase());
		    
		    List<OrgUserMst> userList = objCrt.list();
		    
		    if(userList != null & userList.size() > 0)
		    {
		    	user = userList.get(0);
		    }
		}
		catch(Exception e)
		{
			LOGGER.error("Error description", e);
		}
		return user;
	}
	@Override
	public OrgEmpMst getEmployeeDetails(String username , String locale) throws Exception
	{
		OrgEmpMst employee = null;
		try
		{
			hqlQueryString = " select emp " +
					" from OrgUserMst as user , OrgEmpMst as emp , CmnLanguageMst as lang " +
					" where user.userId = emp.orgUserMstByUserId " +
         			" and user.activateFlag = emp.activateFlag " +
         			" and user.activateFlag =  lang.activateFlag " +
         			" and user.activateFlag =  :activateFlag " +
         			" and emp.cmnLanguageMst =  lang.langId " +
         			" and lang.langShortName = :langShortName " +
         			" and user.userName = :username ";
			
			session = sessionFactory.getCurrentSession();
			hqlQuery = session.createQuery(hqlQueryString);
			hqlQuery.setBoolean("activateFlag", true);
	     	hqlQuery.setString("langShortName", locale);
	     	hqlQuery.setString("username", username);
			List<Object> resultList = hqlQuery.list();
			if(resultList != null && resultList.size() == 1)
			{
				employee = (OrgEmpMst)resultList.get(0);
			}
			else if(resultList.size() > 1)
			{
				throw new Exception("More than one Employees configured against username :"+username+" and locale :"+locale);
			}
			else
			{
				// Do nothing return OrgEmpMst as null
			}
		}
		catch(Exception e)
		{
			throw e;
		}
		return employee;
	}
	@Override
	public void updateUser(OrgUserMst user) throws Exception
	{
		try
		{
			session = sessionFactory.getCurrentSession();
			session.update(user);
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	
	@Override
	@Cacheable("opgCache")
	public LoginDetailsVO fillLoginDetailsVO(Boolean activateFlag , String langShortName , Long userId) throws Exception
	{
		LoginDetailsVO loginDetailsVO = null;
		try
		{
			hqlQueryString = " select user , emp , lang , userRole , role , roleDtls , userImage " +
					" from OrgUserMst as user , OrgEmpMst as emp , CmnLanguageMst as lang , " +
					" OrgUserRoleRlt as userRole , OrgRoleMst as role ,OrgRoleDetailsRlt as roleDtls , OrgUserImageMst as userImage " +
					" where user.userId = emp.orgUserMstByUserId " +
					" and user.userId =  userRole.orgUserMstByUserId " +
					" and user.userId =  userImage.orgUserMstByUserId " +
					" and userRole.roleCode =  role.roleCode " +
					" and role.roleCode =  roleDtls.roleCode " +
	     			" and user.activateFlag = emp.activateFlag " +
	     			" and user.activateFlag =  lang.activateFlag " +
	     			" and user.activateFlag =  userRole.activateFlag " +
	     			" and user.activateFlag =  role.activateFlag " +
	     			" and user.activateFlag =  roleDtls.activateFlag " +
	     			" and user.activateFlag =  userImage.activateFlag " +
	     			" and user.activateFlag =  :activateFlag " +
	     			" and emp.cmnLanguageMst =  lang.langId " +
	     			" and roleDtls.cmnLanguageMst =  lang.langId " +
	     			" and lang.langShortName = :langShortName " +
	     			" and user.userId = :userId ";
			
			session = sessionFactory.getCurrentSession();
			hqlQuery = session.createQuery(hqlQueryString);
			hqlQuery.setBoolean("activateFlag", activateFlag);
	     	hqlQuery.setString("langShortName", langShortName);
	     	hqlQuery.setLong("userId", userId);
	     	List<Object[]> resultList = hqlQuery.list();
			
			Object[] objectArray = null;
			OrgUserMst userMst = null;
			OrgEmpMst employee = null;
			CmnLanguageMst lang = null;
			OrgUserRoleRlt userRole = null;
			OrgRoleMst role = null;
			OrgRoleDetailsRlt roleDtls = null;
			OrgUserImageMst userImage = null;
			List<OrgUserRoleRlt> userRoles = null;
			List<OrgRoleMst> roles = null;
			List<OrgRoleDetailsRlt> roleDetails = null;
			Boolean firstTimeExecution = true;
			for(Iterator<Object[]> resultListIterator = resultList.listIterator() ; resultListIterator.hasNext() ; )
			{
				objectArray = resultListIterator.next();
				if(firstTimeExecution)
				{
					userMst = (OrgUserMst)objectArray[0];
					employee = (OrgEmpMst)objectArray[1];
					lang = (CmnLanguageMst)objectArray[2];
					userRoles = new ArrayList<OrgUserRoleRlt>();
					userRole = (OrgUserRoleRlt)objectArray[3];
					userImage = (OrgUserImageMst)objectArray[6];
					// Convert byte array to encoded string to show image on JSP -> Start
					userImage.setEncodedImageString(new String(Base64.encodeBase64(userImage.getImage())));
					// Convert byte array to encoded string to show image on JSP -> End
					userRoles.add(userRole);
					roles = new ArrayList<OrgRoleMst>();
					role = (OrgRoleMst)objectArray[4];
					roles.add(role);
					
					roleDetails = new ArrayList<OrgRoleDetailsRlt>(); 
					roleDtls = (OrgRoleDetailsRlt)objectArray[5];
					roleDetails.add(roleDtls);
					firstTimeExecution = false;
				}
				else
				{
					userRole = (OrgUserRoleRlt)objectArray[3];
					userRoles.add(userRole);
					role = (OrgRoleMst)objectArray[4];
					roleDtls = (OrgRoleDetailsRlt)objectArray[5];
					roles.add(role);
					roleDetails.add(roleDtls);
				}
			}
			// Set GrantedAuthority -> Start
			Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
			if(roleDtls != null)
			{
				for(OrgRoleDetailsRlt orgRoleDetailsRlt : roleDetails)
				{
					authorities.add(new SimpleGrantedAuthority(orgRoleDetailsRlt.getRoleName()));
				}
			}
			// Set GrantedAuthority -> End
			// Fill valueObject of loginDetailsVO - > Start
			loginDetailsVO = new LoginDetailsVO(userMst , employee , lang , userRoles , roles ,authorities , userImage);
			// Fill value Object of loginDetailsVO - > End
		}
		catch(Exception e)
		{
			throw e;
		}
		return loginDetailsVO;
	}
}
