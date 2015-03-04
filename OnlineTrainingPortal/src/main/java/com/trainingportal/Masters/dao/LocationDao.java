/**
 * 
 */
package com.trainingportal.Masters.dao;

import java.util.List;

import com.trainingportal.Masters.dto.LocationDto;
import com.trainingportal.Masters.valueObject.CmnLocationMst;

/**
 * @author piyush
 *
 */
public interface LocationDao 
{
	public List<LocationDto> getAllLocations() throws Exception;
	public void save(CmnLocationMst location) throws Exception;
	public CmnLocationMst getLocationByCode(long locationCode) throws Exception;
	public void update(CmnLocationMst location) throws Exception;
	public Long getNextLocationCode() throws Exception;
	public CmnLocationMst getLocationByName(String locationName) throws Exception;
}
