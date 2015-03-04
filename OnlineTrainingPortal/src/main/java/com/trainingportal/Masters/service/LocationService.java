/**
 * 
 */
package com.trainingportal.Masters.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.trainingportal.Masters.dto.LocationDto;


/**
 * @author piyush
 *
 */
@Service
public interface LocationService 
{
	public List<LocationDto> getLocationList() throws Exception;
	public LocationDto getLocationDtoFromCode(long locationCode) throws Exception;
	public void saveLocation(LocationDto locationDto) throws Exception;
	public boolean updateLocation(LocationDto locationDto) throws Exception;
	public boolean isLocationExist(LocationDto locationDto) throws Exception;
}
