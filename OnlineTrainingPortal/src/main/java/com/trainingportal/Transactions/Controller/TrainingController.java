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
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.trainingportal.Masters.dto.TrainerDto;
import com.trainingportal.Masters.service.LocationService;
import com.trainingportal.Transactions.dto.TrainingDto;
import com.trainingportal.Transactions.service.TrainingService;

/**
 * @author piyush
 *
 */
@Controller
@RequestMapping("/training")
public class TrainingController 
{
	private static final Logger LOGGER = LoggerFactory.getLogger(TrainingController.class);
    
	@Autowired  
    private TrainingService trainingService;
	@Autowired  
    private LocationService locationService;
	@Autowired
	HttpServletRequest request;

    /** 
     * Get called when Training main link is clicked from menus for loading all the trainings from database. 
     * displaytraining.jsp page is opened , which shows all the trainings stored in the database with training name.
     * This page shows 3 buttons (Create , Update , Close)
     * <p>Expected HTTP GET and request '/training/allTrainings'.</p>
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
    		return "displaytraining";
    	}
    	catch(Exception e)
    	{
    		LOGGER.error("Error description", e);
			throw e;
    	}
    }
    
    @RequestMapping(params = "createTrainingPage", method = RequestMethod.POST)
    public String getCreateTrainingPage(ModelMap model) throws Exception
    {
    	try
    	{
    		TrainingDto trainingDto = new TrainingDto();
    		trainingDto.setLstTrainingType(trainingService.getTrainingType());
    		trainingDto.setLstLocations(locationService.getLocationList());
    		model.addAttribute("action", "create");
    		model.addAttribute("trainingDto", trainingDto);
    		return "training";
    	}
    	catch(Exception e)
    	{
    		LOGGER.error("Error description", e);
			throw e;
    	}
    }
    
    @RequestMapping(params = "updateTrainingPage", method = RequestMethod.POST)
    public String getUpdateTrainingPage(@RequestParam long trainingCode , ModelMap model) throws Exception
    {
    	try
    	{
    		TrainingDto trainingDto = trainingService.getTrainingDtoFromCode(trainingCode);
    		trainingDto.setLstTrainingType(trainingService.getTrainingType());
    		trainingDto.setLstLocations(locationService.getLocationList());
    		model.addAttribute("trainingDto", trainingDto);
    		model.addAttribute("action", "update");
    		model.addAttribute("readOnly", false);
    		return "training";
    	}
    	catch(Exception e)
    	{
    		LOGGER.error("Error description", e);
			throw e;
    	}
    }
    
    @RequestMapping(params = "viewTrainingPage", method = RequestMethod.POST)
    public String getViewTrainingPage(@RequestParam long trainingCode , ModelMap model) throws Exception
    {
    	try
    	{
    		TrainingDto trainingDto = trainingService.getTrainingDtoFromCode(trainingCode);
    		trainingDto.setLstTrainingType(trainingService.getTrainingType());
    		trainingDto.setLstLocations(locationService.getLocationList());
    		model.addAttribute("trainingDto", trainingDto);
    		model.addAttribute("action", "view");
    		model.addAttribute("readOnly", true);
    		return "training";
    	}
    	catch(Exception e)
    	{
    		LOGGER.error("Error description", e);
			throw e;
    	}
    }
    
    @RequestMapping(params = "saveTraining", method = RequestMethod.POST)
    public String saveTraining(@Valid TrainingDto trainingDto ,BindingResult result,ModelMap model) throws Exception
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
    			long trainingCode = trainingService.saveTraining(trainingDto);
    			model.addAttribute("successMsg", "createTrainingSuccessMsg");
    			model.addAttribute("action", "view");
    			model.addAttribute("readOnly", true);
    			trainingDto = trainingService.getTrainingDtoFromCode(trainingCode);
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
    }
    
    @RequestMapping(params = "updateTraining", method = RequestMethod.POST)
    public String updateTraining(@Valid TrainingDto trainingDto ,BindingResult result, ModelMap model) throws Exception
    {
    	try
    	{
    		if(result.hasErrors()) 
			{
    			model.addAttribute("action", "update");
    			model.addAttribute("readOnly", true);
			} 
    		else
    		{
    			//update trainingDto in the database -> Start
    			boolean isTrainingUpdated = trainingService.updateTraining(trainingDto);
    			if(isTrainingUpdated)
    			{
	    			model.addAttribute("successMsg", "updateTrainingSuccessMsg");
	    			model.addAttribute("action", "view");
	    			model.addAttribute("readOnly", true);
    			}
    			else
    			{
    				model.addAttribute("errorMessages", "noChange");
    				model.addAttribute("action", "update");
        			model.addAttribute("readOnly", true);
    			}
    			//update trainingDto in the database -> End
    			trainingDto = trainingService.getTrainingDtoFromCode(trainingDto.getTrainingCode());
    			model.addAttribute("trainingDto", trainingDto);
    		}
    	}
    	catch(Exception e)
    	{
    		LOGGER.error("Error description", e);
			throw e;
    	}
    	return "trainer";
    }
    
    @Cacheable("opgCache")
    @RequestMapping(value = "/allTrainers", method = RequestMethod.GET)
	public @ResponseBody List<TrainerDto> getAllTrainers(@RequestParam String trainername) throws Exception
	{
    	try
    	{
    		return trainingService.getTrainerList(trainername);
    	}
    	catch(Exception e)
    	{
    		LOGGER.error("Error description", e);
			throw e;
    	}
 
	}
    
}