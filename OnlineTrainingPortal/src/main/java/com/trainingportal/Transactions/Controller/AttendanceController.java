/**
 * This will all the functionalities related to training.
 */
package com.trainingportal.Transactions.Controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.gargorg.common.constant.CommonConstants;
import com.trainingportal.Masters.service.LocationService;
import com.trainingportal.Transactions.dto.TrainingDto;
import com.trainingportal.Transactions.service.AttendanceService;
import com.trainingportal.Transactions.service.TrainingService;

/**
 * @author piyush
 *
 */
@Controller
@RequestMapping("/attendance")
public class AttendanceController 
{
	private static final Logger LOGGER = LoggerFactory.getLogger(AttendanceController.class);
    
	@Autowired  
    private AttendanceService attendanceService;
	@Autowired  
    private TrainingService trainingService;
	@Autowired  
    private LocationService locationService;
	@Autowired
	HttpServletRequest request;

    /** 
     * Get called when Training main link is clicked from menus for loading all the attendances from database. 
     * displayattendance.jsp page is opened , which shows all the attendances stored in the database with attendance name.
     * This page shows 3 buttons (Create , Update , Close)
     * <p>Expected HTTP GET and request '/attendance/allTrainings'.</p>
     */
    @RequestMapping(value = "/allTrainings", method = RequestMethod.GET)
    public String getAllTrainings(ModelMap model) throws Exception
    {
    	try
    	{
    		boolean attendanceTrainingList = true;
    		boolean feedbackTrainingList = false;
    		List<TrainingDto> trainingList = trainingService.getTrainingList(attendanceTrainingList , feedbackTrainingList);
    		model.addAttribute("trainingList", trainingList);
    		model.addAttribute("trainingDto", new TrainingDto());
    		return "displaytrainingAttendance";
    	}
    	catch(Exception e)
    	{
    		LOGGER.error("Error description", e);
			throw e;
    	}
    }
    
    @RequestMapping(params = "viewTrainingAttendancePage", method = RequestMethod.POST)
    public String getViewTrainingPage(@RequestParam long trainingCode , ModelMap model) throws Exception
    {
    	try
    	{
    		TrainingDto trainingDto = trainingService.getTrainingDtoFromCode(trainingCode);
    		trainingDto.setLstTrainingType(trainingService.getTrainingType());
    		trainingDto.setLstLocations(locationService.getLocationList());
    		// determine takeAttendance button should be visible or not -> Start
			if(trainingDto.getTrainingStatus().equals(CommonConstants.TRAINING_SCHEDULED) || trainingDto.getAttendanceStatus() == CommonConstants.ATTENDANCE_SUBMITTED)
			{
				model.addAttribute("showTakeAttendanceButton", false);
			}
			else
			{
				model.addAttribute("showTakeAttendanceButton", true);
			}
    		// determine takeAttendance button should be visible or not -> End
    		model.addAttribute("trainingDto", trainingDto);
    		model.addAttribute("action", "view");
    		model.addAttribute("readOnly", true);
    		return "trainingAttendance";
    	}
    	catch(Exception e)
    	{
    		LOGGER.error("Error description", e);
			throw e;
    	}
    }
    
    /*
    @RequestMapping(params = "saveAttendance", method = RequestMethod.POST)
    public String saveAttendance(@Valid AttendanceDto trainingDto ,BindingResult result,ModelMap model) throws Exception
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
    			//Save trainingDto in the database -> Start
    			trainingDto.setActivateFlag(true);			// At time of save training will be active
    			long trainingCode = trainingService.saveAttendance(trainingDto);
    			model.addAttribute("successMsg", "createAttendanceSuccessMsg");
    			model.addAttribute("action", "view");
    			model.addAttribute("readOnly", true);
    			trainingDto = trainingService.getAttendanceDtoFromCode(trainingCode);
    			model.addAttribute("trainingDto", trainingDto);
    			//Save trainingDto in the database -> End
    		}
    	}
    	catch(Exception e)
    	{
    		LOGGER.error("Error description", e);
			throw e;
    	}
    	return "training";
    }*/
}