/**
 * This Service will contain all the functionality related to location.
 * Like add , update and delete
 */
package com.trainingportal.Masters.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gargorg.Admin.service.LoginDetailsVOProviderService;
import com.gargorg.Admin.valueObject.LoginDetailsVO;
import com.gargorg.Masters.valueObject.OrgUserMst;
import com.gargorg.common.Utils.CommonUtility;
import com.trainingportal.Masters.dao.LocationDao;
import com.trainingportal.Masters.dto.LocationDto;
import com.trainingportal.Masters.valueObject.CmnLocationMst;


/**
 * @author piyush
 *
 */
@Service
@Transactional
public class LocationServiceImpl implements LocationService 
{
	@Autowired
	private LocationDao locationDAO;
	@Autowired
	private LoginDetailsVOProviderService loginDetailsVOProviderService;
	@Autowired
	private CommonUtility commonUtility;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LocationServiceImpl.class);
	
	//Method to Get all Locations from database -> Start
	@Override
	public List<LocationDto> getLocationList() throws Exception
	{
		try
		{
			return locationDAO.getAllLocations();
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	//Method to Get all Locations from database -> End
	
	//Method to Get LocationDto from database according to Location Code-> Start
	@Override
	public LocationDto getLocationDtoFromCode(long locationCode) throws Exception
	{
		LocationDto locationDto = null;
		try
		{
			
			CmnLocationMst location = locationDAO.getLocationByCode(locationCode);
			locationDto = new LocationDto();
			BeanUtils.copyProperties(location , locationDto);
		}
		catch(Exception e)
		{
			throw e;
		}
		return locationDto;
	}
	//Method to Get LocationDto from database according to Location Code-> End
	
	//Method to save location in the database -> Start
	@Override
	public void saveLocation(LocationDto locationDto) throws Exception
	{
		try
		{
			//Convert locationDto into  CmnLocationMst -> Start
			LoginDetailsVO loginDetailsVO = loginDetailsVOProviderService.getLoginDetailsVO();	//Get LoginDetailsVO from Security Context holder
			CmnLocationMst location = null;
			OrgUserMst createdUser =  loginDetailsVO.getUser();
			Date currDate = commonUtility.getCurrentDateFromDB();
			long locationCode = locationDAO.getNextLocationCode();
			boolean activateFlag = locationDto.isActivateFlag();
			
			//Create CmnLocationMst VO -> Start
			location = new CmnLocationMst();
			location.setLocationCode(locationCode);
			location.setLocationName(locationDto.getLocationName());
			location.setActivateFlag(activateFlag);
			location.setOrgUserMstByCreatedUserId(createdUser);
			location.setCreatedDate(currDate);
			//Create CmnLocationMst VO -> End
			
			//Convert locationDto into  CmnLocationMst -> End
			locationDAO.save(location);
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	//Method to save location in the database -> End
	
	//Method to update location in the database -> Start
	@Override
	public boolean updateLocation(LocationDto locationDto) throws Exception
	{
		boolean isValuesChanged = false;			// To determine whether user changed the values or not
		try
		{
			//Convert locationDto into  CmnLocationMst  -> Start
			LoginDetailsVO loginDetailsVO = loginDetailsVOProviderService.getLoginDetailsVO();	//Get LoginDetailsVO from Security Context holder
			long locationCode = locationDto.getLocationCode();
			OrgUserMst updatedUser =  loginDetailsVO.getUser();
			Date updatedDate = commonUtility.getCurrentDateFromDB();
			//Convert locationDto into  CmnLocationMst  -> End
			
			CmnLocationMst orgLocationMstPersistent = locationDAO.getLocationByCode(locationCode);
			//Compare lstCmnLocationDetailsRlt and lstCmnLocationMstPersistent whether user changed something or not - > Start
			LocationDto locationFromScreen = null;
			CmnLocationMst locationFromDb = null;
						
			locationFromScreen = locationDto;
			locationFromDb = orgLocationMstPersistent;
			
			if(	!locationFromScreen.getLocationName().equals(locationFromDb.getLocationName()) || 						// LocationName
							locationFromScreen.isActivateFlag() != locationFromDb.isActivateFlag()	||				// ActivateFlag
			  					isValuesChanged
			  )
			{
				isValuesChanged = true;
			}
			//Compare lstCmnLocationMst and lstLocations(Persistent) whether user changed something or not - > End
			if(isValuesChanged)
			{
				BeanUtils.copyProperties(locationDto , orgLocationMstPersistent);
				// Copy other properties fetched from database to make these objects persistent - > STart
				orgLocationMstPersistent.setOrgUserMstByUpdatedUserId(updatedUser);
				orgLocationMstPersistent.setUpdatedDate(updatedDate);
				// Copy other properties fetched from database to make these objects persistent - > End
				
				locationDAO.update(orgLocationMstPersistent);
			}
		}
		catch(Exception e)
		{
			throw e;
		}
		return isValuesChanged;
	}
	//Method to update location in the database -> End
	
	//Method to check Location availability from database -> Start
	@Override
	public boolean isLocationExist(LocationDto locationDto) throws Exception
	{
		boolean isLocationExist = false;
		try
		{
			// Check Location Name in English -> Start
			if(locationDAO.getLocationByName(locationDto.getLocationName()) != null)
			{
				isLocationExist = true;
			}
			// Check Location Name in English -> End
		}
		catch(Exception e)
		{
			throw e;
		}
		return isLocationExist;
	}
	//Method to check Location availability from database -> End
}
