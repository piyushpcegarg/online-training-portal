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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gargorg.Admin.dto.CmnLookupMstDto;
import com.gargorg.Masters.valueObject.CmnLookupDetailsRlt;
import com.gargorg.Masters.valueObject.CmnLookupMst;

/**
 * @author piyush
 *
 */
@Repository
public class CmnLookUpDaoImpl implements CmnLookUpDao 
{
	private static final Logger LOGGER = LoggerFactory.getLogger(CmnLookUpDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	Session session = null;
	Query hqlQuery = null;
	String hqlQueryString = null;
	
	@Override
	public List<CmnLookupMstDto> getLookUpByParentLookUpCode(long parentLookupCode , long langId) throws Exception
	{
		List<CmnLookupMstDto> lstCmnLookupMstDto= null;
		try
		{
			hqlQueryString = " select lookUp , lookUpDtls " +
					" from CmnLookupMst as lookUp , CmnLookupDetailsRlt as lookUpDtls , CmnLanguageMst as lang " +
					" where lookUp.lookupCode = lookUpDtls.lookupCode " +
	     			" and lookUp.activateFlag = lookUpDtls.activateFlag " +
	     			" and lookUpDtls.cmnLanguageMst =  lang.langId " +
	     			" and lang.langId = :langId " +
	     			" and lookUp.parentLookupCode = :parentLookupCode ";
			
			session = sessionFactory.getCurrentSession();
			hqlQuery = session.createQuery(hqlQueryString);
			hqlQuery.setLong("langId", langId);
			hqlQuery.setLong("parentLookupCode", parentLookupCode);
			List<Object[]> resultList = hqlQuery.list();
			if(resultList != null && !resultList.isEmpty())
			{
				lstCmnLookupMstDto = new ArrayList<CmnLookupMstDto>();
				CmnLookupMst cmnLookupMst = null;
				CmnLookupDetailsRlt cmnLookupDetailsRlt = null;
				CmnLookupMstDto cmnLookupMstDto = null;
				for(Object[] objArray : resultList)
				{
					cmnLookupMst = (CmnLookupMst)objArray[0]; 
					cmnLookupDetailsRlt = (CmnLookupDetailsRlt)objArray[1];
					cmnLookupMstDto = new CmnLookupMstDto();
					// Copy Properties -> Start
					BeanUtils.copyProperties(cmnLookupDetailsRlt, cmnLookupMstDto);
					BeanUtils.copyProperties(cmnLookupMst, cmnLookupMstDto);
					// Copy Properties -> End
					lstCmnLookupMstDto.add(cmnLookupMstDto);
				}
			}
			else
			{
				// Do nothing return lstCmnLookupMstDto as null
			}
		}
		catch(Exception e)
		{
			throw e;
		}
		
		return lstCmnLookupMstDto;
	}
	@Override
	public CmnLookupMstDto getLookUpByLookUpCode(long lookupCode , long langId) throws Exception
	{
		CmnLookupMstDto cmnLookupMstDto= null;
		try
		{
			hqlQueryString = " select lookUp , lookUpDtls " +
					" from CmnLookupMst as lookUp , CmnLookupDetailsRlt as lookUpDtls , CmnLanguageMst as lang " +
					" where lookUp.lookupCode = lookUpDtls.lookupCode " +
	     			" and lookUp.activateFlag = lookUpDtls.activateFlag " +
	     			" and lookUpDtls.cmnLanguageMst =  lang.langId " +
	     			" and lang.langId = :langId " +
	     			" and lookUp.lookupCode = :lookupCode ";
			
			session = sessionFactory.getCurrentSession();
			hqlQuery = session.createQuery(hqlQueryString);
			hqlQuery.setLong("langId", langId);
			hqlQuery.setLong("lookupCode", lookupCode);
			List<Object[]> resultList = hqlQuery.list();
			
			if(resultList != null && resultList.size() == 1)
			{
				Object[] objArray = resultList.get(0);
				CmnLookupMst cmnLookupMst = (CmnLookupMst)objArray[0]; 
				CmnLookupDetailsRlt cmnLookupDetailsRlt = (CmnLookupDetailsRlt)objArray[1];
				cmnLookupMstDto = new CmnLookupMstDto();
				// Copy Properties -> Start
				BeanUtils.copyProperties(cmnLookupDetailsRlt, cmnLookupMstDto);
				BeanUtils.copyProperties(cmnLookupMst, cmnLookupMstDto);
				// Copy Properties -> End
			}
			else if(resultList.size() > 1)
			{
				throw new Exception("More than one CmnLookUpMst configured against lookupCode :"+lookupCode+" and langId :"+langId);
			}
			else
			{
				// Do nothing return cmnLookupMstDto as null
			}
		}
		catch(Exception e)
		{
			throw e;
		}
		return cmnLookupMstDto;
	}
	@Override
	public CmnLookupMstDto getLookUpByLookUpName(String lookupName , long langId) throws Exception
	{
		CmnLookupMstDto cmnLookupMstDto= null;
		try
		{
			hqlQueryString = " select lookUp , lookUpDtls " +
					" from CmnLookupMst as lookUp , CmnLookupDetailsRlt as lookUpDtls , CmnLanguageMst as lang " +
					" where lookUp.lookupCode = lookUpDtls.lookupCode " +
	     			" and lookUp.activateFlag = lookUpDtls.activateFlag " +
	     			" and lookUpDtls.cmnLanguageMst =  lang.langId " +
	     			" and lang.langId = :langId " +
	     			" and lookUp.lookupName = :lookupName ";
			
			session = sessionFactory.getCurrentSession();
			hqlQuery = session.createQuery(hqlQueryString);
			hqlQuery.setLong("langId", langId);
			hqlQuery.setString("lookupName", lookupName);
			List<Object[]> resultList = hqlQuery.list();
			
			if(resultList != null && resultList.size() == 1)
			{
				Object[] objArray = resultList.get(0);
				CmnLookupMst cmnLookupMst = (CmnLookupMst)objArray[0]; 
				CmnLookupDetailsRlt cmnLookupDetailsRlt = (CmnLookupDetailsRlt)objArray[1];
				cmnLookupMstDto = new CmnLookupMstDto();
				// Copy Properties -> Start
				BeanUtils.copyProperties(cmnLookupDetailsRlt, cmnLookupMstDto);
				BeanUtils.copyProperties(cmnLookupMst, cmnLookupMstDto);
				// Copy Properties -> End
			}
			else if(resultList.size() > 1)
			{
				throw new Exception("More than one CmnLookUpMst configured against lookupName :"+lookupName+" and langId :"+langId);
			}
			else
			{
				// Do nothing return cmnLookupMstDto as null
			}
		}
		catch(Exception e)
		{
			throw e;
		}
		return cmnLookupMstDto;
	}
}