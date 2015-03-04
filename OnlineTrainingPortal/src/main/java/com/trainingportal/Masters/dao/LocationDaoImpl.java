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
import com.trainingportal.Masters.dto.LocationDto;
import com.trainingportal.Masters.valueObject.CmnLocationMst;


/**
 * @author piyush
 *
 */
@Repository
public class LocationDaoImpl implements LocationDao 
{
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private LoginDetailsVOProviderService loginDetailsVOProviderService;
	
	Session session = null;
	Query hqlQuery = null;
	String hqlQueryString = null;
	private static final Logger LOGGER = LoggerFactory.getLogger(LocationDaoImpl.class);
	
	//This method get list of locations fetched from locations table - > Start
	@Override
	public List<LocationDto> getAllLocations() throws Exception
	{
		List<LocationDto> lstLocationDto = null;
		try
		{
			List<CmnLocationMst> lstCmnLocationMst = null;
			LocationDto locationDto = null;
			session = sessionFactory.getCurrentSession();
			
			Criteria c  = session.createCriteria(CmnLocationMst.class);
			lstCmnLocationMst = c.list();
			
			lstLocationDto = new ArrayList<LocationDto>();
			for(CmnLocationMst location : lstCmnLocationMst)
			{
				locationDto = new LocationDto();
				// Copy Properties -> Start
				BeanUtils.copyProperties(location , locationDto);
				// Copy Properties -> End
				lstLocationDto.add(locationDto);
			}
		}
		catch(Exception e)
		{
			throw e;
		}
		return lstLocationDto;
	}
	//This method get list of locations fetched from locations table - > End
	
	//This method get location fetched from locations table(locationCode) - > Start
	@Override
	public CmnLocationMst getLocationByCode(long locationCode) throws Exception
	{
		CmnLocationMst location = null;
		try
		{
			session = sessionFactory.getCurrentSession();
			Criteria c  = session.createCriteria(CmnLocationMst.class);
			c.add(Restrictions.eq("locationCode", locationCode));
			location = (CmnLocationMst)c.uniqueResult();
		}
		catch(Exception e)
		{
			throw e;
		}
		return location;
	}
	//This method get list of locations fetched from locations table(locationCode) - > End
	
	//This method save list of locations in locations table - > Start
	@Override
	public void save(CmnLocationMst location) throws Exception
	{
		try
		{
			session = sessionFactory.getCurrentSession();
			session.save(location);
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	//This method save list of locations in locations table - > End
	
	//This method update list of locations in locations table - > Start
	@Override
	public void update(CmnLocationMst location) throws Exception
	{
		try
		{
			session = sessionFactory.getCurrentSession();
			session.update(location);
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	//This method update list of locations in locations table - > End
	
	//This method gives next locationCode from locations table - > Start
	@Override
	public Long getNextLocationCode() throws Exception
	{
		Long locationCode = null;
		try
		{
			session = sessionFactory.getCurrentSession();
			Criteria c  = session.createCriteria(CmnLocationMst.class);
			c.setProjection(Projections.max("locationCode"));
			locationCode = (Long)c.uniqueResult();
			if(locationCode == null)
			{
				locationCode = CommonConstants.DEFAULT_LOCATION_CODE + 1L;
			}
			else
			{
				locationCode = locationCode + 1L;
			}
		}
		catch(Exception e)
		{
			throw e;
		}
	    return locationCode;
	}
	//This method gives next locationCode from locations table - > End
	
	//This method get Location fetched from CmnLocationMst table - > Start
	@Override
	public CmnLocationMst getLocationByName(String locationName) throws Exception
	{
		CmnLocationMst location = null;
		try
		{
			session = sessionFactory.getCurrentSession();
			
			Criteria c  = session.createCriteria(CmnLocationMst.class);
			c.add(Restrictions.eq("locationName", locationName).ignoreCase());
			
			location = (CmnLocationMst)c.uniqueResult();
		}
		catch(Exception e)
		{
			throw e;
		}
		return location;
	}
	//This method get Location fetched from CmnLocationMst table - > End
}
