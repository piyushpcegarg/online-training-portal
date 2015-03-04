/**
 * This will all the functionalities related to location.
 */
package com.trainingportal.Masters.Controller;


import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.trainingportal.Masters.dto.LocationDto;
import com.trainingportal.Masters.service.LocationService;

/**
 * @author piyush
 *
 */
@Controller
@RequestMapping("/location")
public class LocationController 
{
	private static final Logger LOGGER = LoggerFactory.getLogger(LocationController.class);
    
	@Autowired  
    private LocationService locationService;  
      
    /** 
     * Get called when Location main link is clicked from menus for loading all the locations from database. 
     * displaylocation.jsp page is opened , which shows all the locations stored in the database with location name.
     * This page shows 3 buttons (Create , Update , Close)
     * <p>Expected HTTP GET and request '/location/allLocations'.</p>
     */
    @RequestMapping(value = "/allLocations", method = RequestMethod.GET)
    public String getAllLocations(ModelMap model) throws Exception
    {
    	try
    	{
    		List<LocationDto> locationList = locationService.getLocationList();
    		model.addAttribute("locationList", locationList);
    		model.addAttribute("locationDto", new LocationDto());
    		return "displaylocation";
    	}
    	catch(Exception e)
    	{
    		LOGGER.error("Error description", e);
			throw e;
    	}
    }
    
    @RequestMapping(params = "createLocationPage", method = RequestMethod.POST)
    public String getCreateLocationPage(ModelMap model) throws Exception
    {
    	try
    	{
    		LocationDto locationDto = new LocationDto();
    		model.addAttribute("action", "create");
    		model.addAttribute("locationDto", locationDto);
    		return "location";
    	}
    	catch(Exception e)
    	{
    		LOGGER.error("Error description", e);
			throw e;
    	}
    }
    
    @RequestMapping(params = "updateLocationPage", method = RequestMethod.POST)
    public String getUpdateLocationPage(@RequestParam long locationCode , ModelMap model) throws Exception
    {
    	try
    	{
    		LocationDto locationDto = locationService.getLocationDtoFromCode(locationCode);
    		model.addAttribute("locationDto", locationDto);
    		model.addAttribute("action", "update");
    		model.addAttribute("readOnly", false);
    		return "location";
    	}
    	catch(Exception e)
    	{
    		LOGGER.error("Error description", e);
			throw e;
    	}
    }
    
    @RequestMapping(params = "viewLocationPage", method = RequestMethod.POST)
    public String getViewLocationPage(@RequestParam long locationCode , ModelMap model) throws Exception
    {
    	try
    	{
    		LocationDto locationDto = locationService.getLocationDtoFromCode(locationCode);
    		model.addAttribute("locationDto", locationDto);
    		model.addAttribute("action", "view");
    		model.addAttribute("readOnly", true);
    		return "location";
    	}
    	catch(Exception e)
    	{
    		LOGGER.error("Error description", e);
			throw e;
    	}
    }
    
    @RequestMapping(params = "saveLocation", method = RequestMethod.POST)
    public String saveLocation(@Valid LocationDto locationDto ,BindingResult result, ModelMap model) throws Exception
    {
    	try
    	{
    		if(result.hasErrors()) 
			{
    			model.addAttribute("action", "create");
    			model.addAttribute("readOnly", false);
			} 
    		else
    		{
    			boolean isLocationExist = locationService.isLocationExist(locationDto);
    			if(isLocationExist)
    			{
    				model.addAttribute("errorMessages", "locationAlreadyExist");
    				model.addAttribute("action", "create");
        			model.addAttribute("readOnly", false);
    			}
    			else
    			{
	    			//Save locationDto in the database -> Start
    				locationDto.setActivateFlag(true);			// At time of save location will be active
	    			locationService.saveLocation(locationDto);
	    			model.addAttribute("successMsg", "createLocationSuccessMsg");
	    			model.addAttribute("action", "view");
	    			model.addAttribute("readOnly", true);
	    			//Save locationDto in the database -> End
    			}
    		}
    	}
    	catch(Exception e)
    	{
    		LOGGER.error("Error description", e);
			throw e;
    	}
    	return "location";
    }
    
    @RequestMapping(params = "updateLocation", method = RequestMethod.POST)
    public String updateLocation(@Valid LocationDto locationDto ,BindingResult result, ModelMap model) throws Exception
    {
    	try
    	{
    		if(result.hasErrors()) 
			{
    			model.addAttribute("action", "update");
    			model.addAttribute("readOnly", false);
			} 
    		else
    		{
    			//update locationDto in the database -> Start
    			boolean isLocationUpdated = locationService.updateLocation(locationDto);
    			if(isLocationUpdated)
    			{
	    			model.addAttribute("successMsg", "updateLocationSuccessMsg");
	    			model.addAttribute("action", "view");
	    			model.addAttribute("readOnly", true);
    			}
    			else
    			{
    				model.addAttribute("errorMessages", "noChange");
    				model.addAttribute("action", "update");
        			model.addAttribute("readOnly", false);
    			}
    			//update locationDto in the database -> End
    		}
    	}
    	catch(Exception e)
    	{
    		LOGGER.error("Error description", e);
			throw e;
    	}
    	return "location";
    }
}
