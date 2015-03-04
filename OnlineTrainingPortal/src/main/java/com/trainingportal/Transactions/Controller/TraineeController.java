/**
 * This will all the functionalities related to training.
 */
package com.trainingportal.Transactions.Controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
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

import com.gargorg.common.constant.CommonConstants;
import com.trainingportal.Masters.service.LocationService;
import com.trainingportal.Transactions.dto.TraineeDto;
import com.trainingportal.Transactions.dto.TrainingDto;
import com.trainingportal.Transactions.service.TraineeService;
import com.trainingportal.Transactions.service.TrainingService;

/**
 * @author piyush
 *
 */
@Controller
@RequestMapping("/trainee")
public class TraineeController 
{
	private static final Logger LOGGER = LoggerFactory.getLogger(TraineeController.class);
    
	@Autowired  
    private TraineeService traineeService;
	@Autowired  
    private TrainingService trainingService;
	@Autowired  
    private LocationService locationService;
	@Autowired
	HttpServletRequest request;

    /** 
     * Get called when Training main link is clicked from menus for loading all the trainings from database. 
     * displaytrainingTrainee.jsp page is opened , which shows all the trainings stored in the database with training name.
     * This page shows 3 buttons (Create , Update , Close)
     * <p>Expected HTTP GET and request '/trainee/allTrainings'.</p>
     */
    @RequestMapping(value = "/allTrainings", method = RequestMethod.GET)
    public String getAllTrainings(ModelMap model) throws Exception
    {
    	try
    	{
    		boolean attendanceTrainingList = false;
    		boolean feedbackTrainingList = false;
    		List<TrainingDto> trainingList = trainingService.getTrainingList(attendanceTrainingList , feedbackTrainingList);
    		model.addAttribute("trainingList", trainingList);
    		model.addAttribute("trainingDto", new TrainingDto());
    		return "displaytrainingTrainee";
    	}
    	catch(Exception e)
    	{
    		LOGGER.error("Error description", e);
			throw e;
    	}
    }
    
    @RequestMapping(params = "viewTrainingTraineePage", method = RequestMethod.POST)
    public String getViewTrainingPage(@RequestParam long trainingCode , ModelMap model) throws Exception
    {
    	try
    	{
    		TrainingDto trainingDto = trainingService.getTrainingDtoFromCode(trainingCode);
    		trainingDto.setLstTrainingType(trainingService.getTrainingType());
    		trainingDto.setLstLocations(locationService.getLocationList());
    		// determine Add Trainee button should be visible or not -> Start
			if(trainingDto.getTrainingStatus().equals(CommonConstants.TRAINING_SCHEDULED))
			{
				model.addAttribute("showAddTraineeButton", true);
			}
			else
			{
				model.addAttribute("showAddTraineeButton", false);
			}
    		// determine Add Trainee button should be visible or not -> End
    		model.addAttribute("trainingDto", trainingDto);
    		model.addAttribute("action", "view");
    		model.addAttribute("readOnly", true);
    		return "trainingTrainee";
    	}
    	catch(Exception e)
    	{
    		LOGGER.error("Error description", e);
			throw e;
    	}
    }
    
    @RequestMapping(params = "addTraineePage", method = RequestMethod.POST)
    public String getAddTraineePage() throws Exception
    {
    	try
    	{
    		return "";
    	}
    	catch(Exception e)
    	{
    		LOGGER.error("Error description", e);
			throw e;
    	}
    }
    
    @RequestMapping(params = "deleteTraineePage", method = RequestMethod.POST)
    public String getDeleteTraineePage(TrainingDto trainingDto , ModelMap model) throws Exception
    {
    	try
    	{
    		long trainingCode = trainingDto.getTrainingCode();
    		List<TraineeDto> lstTraineeDto = traineeService.getTraineeList(trainingCode);
    		model.addAttribute("lstTraineeDto", lstTraineeDto);
    		model.addAttribute("traineeDto", new TraineeDto());
    		return "deleteTrainee";
    	}
    	catch(Exception e)
    	{
    		LOGGER.error("Error description", e);
			throw e;
    	}
    }
    
    @RequestMapping(params = "deleteTrainee", method = RequestMethod.POST)
    public String getDeleteTraineePage(@Valid TraineeDto traineeDto ,BindingResult result, ModelMap model) throws Exception
    {
    	try
    	{
    		long trainingCode = traineeDto.getTrainingCode();
    		if(!result.hasErrors())
    		{
    			List<Long> lstTraineeId = traineeDto.getLstTraineeId();
    			boolean isTraineeDeleted = traineeService.deleteTrainee(trainingCode,lstTraineeId);
    			if(isTraineeDeleted)
    			{
	    			model.addAttribute("successMsg", "deleteTraineeSuccessMsg");
    			}
    		}
    		List<TraineeDto> lstTraineeDto = traineeService.getTraineeList(trainingCode);
    		model.addAttribute("lstTraineeDto", lstTraineeDto);
    		model.addAttribute("traineeDto", new TraineeDto());
    		return "deleteTrainee";
    	}
    	catch(Exception e)
    	{
    		LOGGER.error("Error description", e);
			throw e;
    	}
    }
}