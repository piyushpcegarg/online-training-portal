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

import com.gargorg.Admin.dto.OrgElementMstDto;
import com.gargorg.Admin.service.LoginDetailsVOProviderService;
import com.gargorg.Admin.valueObject.LoginDetailsVO;
import com.gargorg.Masters.valueObject.OrgElementDetailsRlt;
import com.gargorg.Masters.valueObject.OrgElementMst;
import com.gargorg.Masters.valueObject.OrgRoleElementRlt;
import com.gargorg.common.constant.CommonConstants;


/**
 * @author piyush
 *
 */
@Repository
public class ElementDaoImpl implements ElementDao 
{
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private LoginDetailsVOProviderService loginDetailsVOProviderService;
	
	Session session = null;
	Query hqlQuery = null;
	String hqlQueryString = null;
	private static final Logger LOGGER = LoggerFactory.getLogger(ElementDaoImpl.class);
	
	//This method get list of elements fetched from elements table - > Start
	@Override
	public List<OrgElementMstDto> getAllElements() throws Exception
	{
		List<OrgElementMstDto> lstElementDto = null;
		try
		{
			LoginDetailsVO loginDetailsVO = loginDetailsVOProviderService.getLoginDetailsVO();	//Get LoginDetailsVO from Security Context holder
		
			hqlQueryString = " select element , elementDtls " +
					" from OrgElementMst as element , OrgElementDetailsRlt as elementDtls , CmnLanguageMst as lang " +
					" where element.elementCode = elementDtls.elementCode " +
	     			" and element.activateFlag = elementDtls.activateFlag " +
	     			" and elementDtls.cmnLanguageMst =  lang.langId " +
	     			" and lang.langId = :langId " +
	     			" order by element.elementCode ";
			
			session = sessionFactory.getCurrentSession();
			hqlQuery = session.createQuery(hqlQueryString);
			hqlQuery.setLong("langId", loginDetailsVO.getLang().getLangId());
			List<Object[]> resultList = hqlQuery.list();
			if(resultList != null && !resultList.isEmpty())
			{
				lstElementDto = new ArrayList<OrgElementMstDto>();
				OrgElementMst orgElementMst = null;
				OrgElementDetailsRlt orgElementDetailsRlt = null;
				OrgElementMstDto orgElementMstDto = null;
				for(Object[] objArray : resultList)
				{
					orgElementMst = (OrgElementMst)objArray[0]; 
					orgElementDetailsRlt = (OrgElementDetailsRlt)objArray[1];
					orgElementMstDto = new OrgElementMstDto();
					// Copy Properties -> Start
					BeanUtils.copyProperties(orgElementDetailsRlt, orgElementMstDto);
					BeanUtils.copyProperties(orgElementMst, orgElementMstDto);
					// Copy Properties -> End
					lstElementDto.add(orgElementMstDto);
				}
			}
			else
			{
				// Do nothing return lstElementDto as null
			}
	}
	catch(Exception e)
	{
		throw e;
	}
		return lstElementDto;
	}
	//This method get list of elements fetched from elements table - > End
	
	//This method get list of all elements which can be mapped to any role fetched from elements table - > Start
	@Override
	public List<OrgElementMstDto> getAllEditableElements() throws Exception
	{
		List<OrgElementMstDto> lstElementDto = null;
		try
		{
			LoginDetailsVO loginDetailsVO = loginDetailsVOProviderService.getLoginDetailsVO();	//Get LoginDetailsVO from Security Context holder
			
			hqlQueryString = " select element , elementDtls " +
					" from OrgElementMst as element , OrgElementDetailsRlt as elementDtls , CmnLanguageMst as lang " +
					" where element.elementCode = elementDtls.elementCode " +
	     			" and element.activateFlag = elementDtls.activateFlag " +
	     			" and elementDtls.cmnLanguageMst =  lang.langId " +
	     			" and element.editable =  :editable " +
	     			" and lang.langId = :langId " +
	     			" order by element.elementCode ";
			
			session = sessionFactory.getCurrentSession();
			hqlQuery = session.createQuery(hqlQueryString);
			hqlQuery.setBoolean("editable", true);
			hqlQuery.setLong("langId", loginDetailsVO.getLang().getLangId());
			List<Object[]> resultList = hqlQuery.list();
			if(resultList != null && !resultList.isEmpty())
			{
				lstElementDto = new ArrayList<OrgElementMstDto>();
				OrgElementMst orgElementMst = null;
				OrgElementDetailsRlt orgElementDetailsRlt = null;
				OrgElementMstDto orgElementMstDto = null;
				for(Object[] objArray : resultList)
				{
					orgElementMst = (OrgElementMst)objArray[0]; 
					orgElementDetailsRlt = (OrgElementDetailsRlt)objArray[1];
					orgElementMstDto = new OrgElementMstDto();
					// Copy Properties -> Start
					BeanUtils.copyProperties(orgElementDetailsRlt, orgElementMstDto);
					BeanUtils.copyProperties(orgElementMst, orgElementMstDto);
					// Copy Properties -> End
					lstElementDto.add(orgElementMstDto);
				}
			}
			else
			{
				// Do nothing return lstElementDto as null
			}
		}
		catch(Exception e)
		{
			throw e;
		}
		return lstElementDto;
	}
	//This method get list of all elements which can be mapped to any role fetched from elements table - > End
		
	
	//This method get list of elements fetched from elements table(elementCode) - > Start
	@Override
	public List<OrgElementMstDto> getElementByCode(long elementCode) throws Exception
	{
		List<OrgElementMstDto> lstElementDto = null;
		try
		{
			hqlQueryString = " select element , elementDtls " +
					" from OrgElementMst as element , OrgElementDetailsRlt as elementDtls " +
					" where element.elementCode = elementDtls.elementCode " +
	     			" and element.activateFlag = elementDtls.activateFlag " +
	     			" and element.elementCode = :elementCode " +			
	     			" order by elementDtls.cmnLanguageMst.langId ";
			
			session = sessionFactory.getCurrentSession();
			hqlQuery = session.createQuery(hqlQueryString);
			hqlQuery.setLong("elementCode", elementCode);
			List<Object[]> resultList = hqlQuery.list();
			if(resultList != null && !resultList.isEmpty())
			{
				lstElementDto = new ArrayList<OrgElementMstDto>();
				OrgElementMst orgElementMst = null;
				OrgElementDetailsRlt orgElementDetailsRlt = null;
				OrgElementMstDto orgElementMstDto = null;
				for(Object[] objArray : resultList)
				{
					orgElementMst = (OrgElementMst)objArray[0]; 
					orgElementDetailsRlt = (OrgElementDetailsRlt)objArray[1];
					orgElementMstDto = new OrgElementMstDto();
					// Copy Properties -> Start
					BeanUtils.copyProperties(orgElementDetailsRlt, orgElementMstDto);
					BeanUtils.copyProperties(orgElementMst, orgElementMstDto);
					// Copy Properties -> End
					lstElementDto.add(orgElementMstDto);
				}
			}
			else
			{
				// Do nothing return lstElementDto as null
			}
		}
		catch(Exception e)
		{
			throw e;
		}
		return lstElementDto;
	}
	//This method get list of elements fetched from elements table(elementCode) - > End
	
	//This method save list of elements in elements table - > Start
	@Override
	public void save(OrgElementMst element , List<OrgElementDetailsRlt> lstOrgElementDetailsRlt , OrgRoleElementRlt orgRoleElementRlt) throws Exception
	{
		try
		{
			session = sessionFactory.getCurrentSession();
			session.save(element);
			for(OrgElementDetailsRlt orgElementDetailsRlt : lstOrgElementDetailsRlt)
			{
				session.save(orgElementDetailsRlt);
			}
			session.save(orgRoleElementRlt);
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	//This method save list of elements in elements table - > End
	
	//This method update list of elements in elements table - > Start
	@Override
	public void update(OrgElementMst element , List<OrgElementDetailsRlt> lstOrgElementDetailsRlt) throws Exception
	{
		try
		{
			session = sessionFactory.getCurrentSession();
			session.clear();
			session.update(element);
			for(OrgElementDetailsRlt orgElementDetailsRlt : lstOrgElementDetailsRlt)
			{
				session.update(orgElementDetailsRlt);
			}
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	//This method update list of elements in elements table - > End
	
	//This method gives next elementCode from elements table - > Start
	@Override
	public Long getNextElementCode() throws Exception
	{
		Long elementCode = null;
		try
		{
			session = sessionFactory.getCurrentSession();
			Criteria c  = session.createCriteria(OrgElementMst.class);
			c.setProjection(Projections.max("elementCode"));
			elementCode = (Long)c.uniqueResult();
			if(elementCode == null)
			{
				elementCode = CommonConstants.DEFAULT_ROLE_CODE + 1L;
			}
			else
			{
				elementCode = elementCode + 1L;
			}
		}
		catch(Exception e)
		{
			throw e;
		}
	    return elementCode;
	}
	//This method gives next elementCode from elements table - > End
	
	//This method get element fetched from elements table - > Start
	@Override
	public OrgElementMst getElementByName(String elementName) throws Exception
	{
		OrgElementMst orgElementMst = null;
		try
		{
			session = sessionFactory.getCurrentSession();
			
			Criteria c  = session.createCriteria(OrgElementDetailsRlt.class);
			c.add(Restrictions.eq("elementName", elementName).ignoreCase());
			
			orgElementMst = (OrgElementMst)c.uniqueResult();
		}
		catch(Exception e)
		{
			throw e;
		}
		return orgElementMst;
	}
	//This method get element fetched from elements table - > End
}
