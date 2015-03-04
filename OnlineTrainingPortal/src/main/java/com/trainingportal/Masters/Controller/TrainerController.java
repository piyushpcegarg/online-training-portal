/**
 * This will all the functionalities related to trainer.
 */
package com.trainingportal.Masters.Controller;


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

import com.gargorg.Masters.dto.UserDto;
import com.trainingportal.Masters.dto.TrainerDto;
import com.trainingportal.Masters.service.TrainerService;
import com.trainingportal.Masters.valueObject.OrgTrainerMst;

/**
 * @author piyush
 *
 */
@Controller
@RequestMapping("/trainer")
public class TrainerController 
{
	private static final Logger LOGGER = LoggerFactory.getLogger(TrainerController.class);
    
	@Autowired  
    private TrainerService trainerService;
	@Autowired
	HttpServletRequest request;

    /** 
     * Get called when Trainer main link is clicked from menus for loading all the trainers from database. 
     * displaytrainer.jsp page is opened , which shows all the trainers stored in the database with trainer name.
     * This page shows 3 buttons (Create , Update , Close)
     * <p>Expected HTTP GET and request '/trainer/allTrainers'.</p>
     */
    @RequestMapping(value = "/allTrainers", method = RequestMethod.GET)
    public String getAllTrainers(ModelMap model) throws Exception
    {
    	try
    	{
    		List<TrainerDto> trainerList = trainerService.getTrainerList();
    		model.addAttribute("trainerList", trainerList);
    		model.addAttribute("trainerDto", new TrainerDto());
    		return "displaytrainer";
    	}
    	catch(Exception e)
    	{
    		LOGGER.error("Error description", e);
			throw e;
    	}
    }
    
    @RequestMapping(params = "createTrainerPage", method = RequestMethod.POST)
    public String getCreateTrainerPage(ModelMap model) throws Exception
    {
    	try
    	{
    		TrainerDto trainerDto = new TrainerDto();
    		model.addAttribute("action", "create");
    		model.addAttribute("trainerDto", trainerDto);
    		return "trainer";
    	}
    	catch(Exception e)
    	{
    		LOGGER.error("Error description", e);
			throw e;
    	}
    }
    
    @RequestMapping(params = "searchUser", method = RequestMethod.POST)
    public String getTrainerDtoFromName(ModelMap model) throws Exception
    {
    	try
    	{
    		String username = request.getParameter("userName");
    		TrainerDto trainerDto = trainerService.getTrainerDtoFromName(username);
    		model.addAttribute("action", "create");
    		model.addAttribute("readOnly", true);
    		// If user is inactive then show error message to client -> Start
    		if(trainerDto == null)
    		{
    			trainerDto = new TrainerDto();
    			trainerDto.setUserName(username);
    			model.addAttribute("errorMessages", "userNotExist");
    		}
    		else
    		{
    			OrgTrainerMst orgTrainerMst = trainerService.getTrainerByUserId(trainerDto.getUserId());
    			if(orgTrainerMst != null)
    			{
    				model.addAttribute("errorMessages", "userAlreadyTrainer");
    				model.addAttribute("displayUserForm", false);
    			}
    			else
    			{
    				model.addAttribute("displayUserForm", true);
    			}
    		}
    		// If user is inactive then show error message to client -> End
    		model.addAttribute("trainerDto", trainerDto);
    		return "trainer";
    	}
    	catch(Exception e)
    	{
    		LOGGER.error("Error description", e);
			throw e;
    	}
    }
    
    @RequestMapping(params = "updateTrainerPage", method = RequestMethod.POST)
    public String getUpdateTrainerPage(@RequestParam long trainerCode , ModelMap model) throws Exception
    {
    	try
    	{
    		TrainerDto trainerDto = trainerService.getTrainerDtoFromCode(trainerCode);
    		model.addAttribute("trainerDto", trainerDto);
    		model.addAttribute("action", "update");
    		model.addAttribute("readOnly", true);
    		model.addAttribute("displayUserForm", true);
    		return "trainer";
    	}
    	catch(Exception e)
    	{
    		LOGGER.error("Error description", e);
			throw e;
    	}
    }
    
    @RequestMapping(params = "viewTrainerPage", method = RequestMethod.POST)
    public String getViewTrainerPage(@RequestParam long trainerCode , ModelMap model) throws Exception
    {
    	try
    	{
    		TrainerDto trainerDto = trainerService.getTrainerDtoFromCode(trainerCode);
    		model.addAttribute("trainerDto", trainerDto);
    		model.addAttribute("action", "view");
    		model.addAttribute("readOnly", true);
    		model.addAttribute("displayUserForm", true);
    		return "trainer";
    	}
    	catch(Exception e)
    	{
    		LOGGER.error("Error description", e);
			throw e;
    	}
    }
    
    @RequestMapping(params = "saveTrainer", method = RequestMethod.POST)
    public String saveTrainer(@Valid TrainerDto trainerDto ,BindingResult result,ModelMap model) throws Exception
    {
    	try
    	{
    		if(result.hasErrors())
			{
    			model.addAttribute("action", "create");
    			model.addAttribute("readOnly", true);
			} 
    		else
    		{
    			//Save trainerDto in the database -> Start
    			trainerDto.setActivateFlag(true);			// At time of save trainer will be active
    			long trainerCode = trainerService.saveTrainer(trainerDto);
    			model.addAttribute("displayUserForm", true);
    			model.addAttribute("successMsg", "createTrainerSuccessMsg");
    			model.addAttribute("action", "view");
    			model.addAttribute("readOnly", true);
    			trainerDto = trainerService.getTrainerDtoFromCode(trainerCode);
    			model.addAttribute("trainerDto", trainerDto);
    			//Save trainerDto in the database -> End
    		}
    	}
    	catch(Exception e)
    	{
    		LOGGER.error("Error description", e);
			throw e;
    	}
    	return "trainer";
    }
    
    @RequestMapping(params = "updateTrainer", method = RequestMethod.POST)
    public String updateTrainer(@Valid TrainerDto trainerDto ,BindingResult result, ModelMap model) throws Exception
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
    			//update trainerDto in the database -> Start
    			boolean isTrainerUpdated = trainerService.updateTrainer(trainerDto);
    			if(isTrainerUpdated)
    			{
	    			model.addAttribute("successMsg", "updateTrainerSuccessMsg");
	    			model.addAttribute("action", "view");
	    			model.addAttribute("readOnly", true);
    			}
    			else
    			{
    				model.addAttribute("errorMessages", "noChange");
    				model.addAttribute("action", "update");
        			model.addAttribute("readOnly", true);
    			}
    			//update trainerDto in the database -> End
    			trainerDto = trainerService.getTrainerDtoFromCode(trainerDto.getTrainerCode());
    			model.addAttribute("displayUserForm", true);
    			model.addAttribute("trainerDto", trainerDto);
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
    @RequestMapping(value = "/allUsers", method = RequestMethod.GET)
	public @ResponseBody List<UserDto> getAllUsers(@RequestParam String username) throws Exception
	{
    	try
    	{
    		return trainerService.getUserList(username);
    	}
    	catch(Exception e)
    	{
    		LOGGER.error("Error description", e);
			throw e;
    	}
 
	}
}