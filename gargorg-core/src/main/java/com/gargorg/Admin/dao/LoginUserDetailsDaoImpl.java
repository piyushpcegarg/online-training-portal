/**
 * 
 */
package com.gargorg.Admin.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.gargorg.Admin.dto.OrgElementMstDto;
import com.gargorg.Masters.valueObject.OrgElementDetailsRlt;
import com.gargorg.Masters.valueObject.OrgElementMst;
import com.gargorg.common.constant.CommonConstants;


/**
 * @author piyush
 *
 */
@Repository
public class LoginUserDetailsDaoImpl implements LoginUserDetailsDao 
{
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginUserDetailsDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private CmnLookUpDao cmnLookUpDao;
	
	Session session = null;
	Query hqlQuery = null;
	String hqlQueryString = null;
	
	@Override
	@Cacheable("opgCache")
	public List<OrgElementMstDto> getMappedUserElements(Boolean activateFlag , String langShortName , List<Long> roleCodes) throws Exception
	{
		List<OrgElementMstDto> moduleScreenList = null;
		try
		{
			hqlQueryString = " select ele , eleDtls " +
					" from OrgRoleMst as role , OrgRoleElementRlt as roleEle , " +
					" OrgElementMst as ele ,  OrgElementDetailsRlt as eleDtls ,  CmnLanguageMst as lang " +
					" where " + 
					" role.roleCode =  roleEle.roleCode " +
					" and roleEle.elementCode =  ele.elementCode " +
					" and ele.elementCode =  eleDtls.elementCode " +
         			" and role.activateFlag =  roleEle.activateFlag " +
         			" and role.activateFlag =  ele.activateFlag " +
         			" and role.activateFlag =  eleDtls.activateFlag " +
         			" and role.activateFlag =  lang.activateFlag " +
         			" and role.activateFlag =  :activateFlag " +
         			" and eleDtls.cmnLanguageMst =  lang.langId " +
         			" and lang.langShortName = :langShortName " +
         			" and role.roleCode in (:roleCodes )";
			
			session = sessionFactory.getCurrentSession();
			hqlQuery = session.createQuery(hqlQueryString);
			
			hqlQuery.setBoolean("activateFlag", activateFlag);
	     	hqlQuery.setString("langShortName", langShortName);
	     	hqlQuery.setParameterList("roleCodes", roleCodes);
			List<Object[]> resultList = hqlQuery.list();
			Object[] resultObject = null;
			OrgElementMst orgElementMst = null;
			OrgElementDetailsRlt orgElementDetailsRlt = null;
			OrgElementMstDto elementDto = null;
			
			List<OrgElementMstDto> userElementList = new ArrayList<OrgElementMstDto>();
			for(Iterator<Object[]> resultListIterator = resultList.listIterator() ; resultListIterator.hasNext() ; )
			{
				resultObject = resultListIterator.next();
				orgElementMst = (OrgElementMst)resultObject[0];
				orgElementDetailsRlt = (OrgElementDetailsRlt)resultObject[1];
				elementDto = new OrgElementMstDto();
				// Copy Properties -> Start
				BeanUtils.copyProperties(orgElementDetailsRlt, elementDto);
				BeanUtils.copyProperties(orgElementMst, elementDto);
				// Copy Properties -> End
				userElementList.add(elementDto);
			}
			
			moduleScreenList = new ArrayList<OrgElementMstDto>();
			// Sorting userElementList according to element order - > Start
			if(userElementList != null && !userElementList.isEmpty())
			{
				
				moduleScreenList = new ArrayList<OrgElementMstDto>();
				List<OrgElementMstDto> moduleList = new ArrayList<OrgElementMstDto>();
				int count = 0;
				for (int i = 0; i < userElementList.size(); ++i)
				{
					OrgElementMstDto moduleElementObj = userElementList.get(i);
					if (moduleElementObj.getElementType() != CommonConstants.MODULE)	// Compare module elements
						continue;
					moduleList.add(moduleElementObj);
				}
	
			    int moduleListSize = moduleList.size();
			    Collections.sort(moduleList);
			    for (int i = 0; i < moduleListSize ; i++)
			    {
			        moduleScreenList.add(count, moduleList.get(i));
			        count++;
			    }
	
			    for (int moduleCounter = 0; moduleCounter < moduleListSize ; moduleCounter++)
			    {
			    	OrgElementMstDto moduleElement = moduleList.get(moduleCounter);
			        List<OrgElementMstDto> screenElementList = new ArrayList<OrgElementMstDto>();
		
			        for (int counter = 0; counter < userElementList.size(); counter++)
			        {
			        	OrgElementMstDto screenElement = userElementList.get(counter);
			        	if ((screenElement.getElementType() != CommonConstants.SCREEN) || (screenElement.getElementParentCode() != moduleElement.getElementCode()))		// Compare screen elements
			        		continue;
			        	screenElementList.add(screenElement);
			        }
		
			        Collections.sort(screenElementList);
			        for (int j = 0; j < screenElementList.size(); j++)
			        {
			        	moduleScreenList.add(count, screenElementList.get(j));
			        	count++;
			        }
			    }
			}
			// Sorting userElementList according to element order - > End
		}
		catch(Exception e)
		{
			throw e;
		}
		return moduleScreenList;
	}
}
