/**
 * This will all the functionalities related to trainerSkill.
 */
package com.trainingportal.Masters.Controller;


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

import com.gargorg.Admin.service.LoginDetailsVOProviderService;
import com.trainingportal.Masters.dto.TrainerDto;
import com.trainingportal.Masters.dto.TrainerSkillDto;
import com.trainingportal.Masters.service.TrainerService;
import com.trainingportal.Masters.service.TrainerSkillService;

/**
 * @author piyush
 *
 */
@Controller
@RequestMapping("/trainerSkill")
public class TrainerSkillController 
{
	private static final Logger LOGGER = LoggerFactory.getLogger(TrainerSkillController.class);
    
	@Autowired  
    private TrainerSkillService trainerSkillService;  
	@Autowired  
    private TrainerService trainerService;  
	@Autowired
	private LoginDetailsVOProviderService loginDetailsVOProviderService;
	@Autowired
	private HttpServletRequest reqest;
      
    /** 
     * Get called when TrainerSkill main link is clicked from menus for loading all the trainerSkills from database. 
     * displayTrainerSkill.jsp page is opened , which shows all the trainerSkills stored in the database with trainerSkill name.
     * This page shows 3 buttons (Create , Update , Close)
     * <p>Expected HTTP GET and request '/trainerSkill/allTrainerSkills'.</p>
     */
	
	@RequestMapping(value = "/allTrainerSkills", method = RequestMethod.GET)
    public String getAllRoles(ModelMap model) throws Exception
    {
    	try
    	{
    		List<TrainerDto> trainerList = trainerService.getTrainerList();
    		model.addAttribute("trainerList", trainerList);
    		model.addAttribute("trainerDto", new TrainerDto());
    		return "displayTrainerSkill";
    	}
    	catch(Exception e)
    	{
    		LOGGER.error("Error description", e);
			throw e;
    	}
    }
	
	@RequestMapping(params = "viewTrainerSkillPage", method = RequestMethod.POST)
    public String getViewTrainerSkillPage(@RequestParam long trainerCode , ModelMap model) throws Exception
    {
    	try
    	{
			List<TrainerSkillDto> lstTrainerSkillDto = trainerSkillService.getTrainerSkillList(trainerCode);
			// To fetch Role Name with the help of trainerCode -> Start
			TrainerDto trainerDto = trainerSkillService.getTrainerDtoFromCode(trainerCode);
			// To fetch Role Name with the help of trainerCode -> End
			model.addAttribute("lstTrainerSkillDto", lstTrainerSkillDto);
			model.addAttribute("trainerDto", trainerDto);
			model.addAttribute("trainerName", trainerDto.getUserName());
			model.addAttribute("action", "view");
    		model.addAttribute("readOnly", true);
    		return "trainerSkill";
    	}
    	catch(Exception e)
    	{
    		LOGGER.error("Error description", e);
			throw e;
    	}
    }
	
	@RequestMapping(params = "updateTrainerSkillPage", method = RequestMethod.POST)
    public String getUpdateTrainerSkillPage(@RequestParam long trainerCode , ModelMap model) throws Exception
    {
    	try
    	{
			List<TrainerSkillDto> lstTrainerSkillDto = trainerSkillService.getTrainerSkillList(trainerCode);
			// To fetch Role Name with the help of trainerCode -> Start
			TrainerDto trainerDto = trainerSkillService.getTrainerDtoFromCode(trainerCode);
			// To fetch Role Name with the help of trainerCode -> End
			model.addAttribute("lstTrainerSkillDto", lstTrainerSkillDto);
			model.addAttribute("trainerDto", trainerDto);
			model.addAttribute("trainerName", trainerDto.getUserName());
			model.addAttribute("action", "update");
    		model.addAttribute("readOnly", false);
    		return "trainerSkill";
    	}
    	catch(Exception e)
    	{
    		LOGGER.error("Error description", e);
			throw e;
    	}
    }
	
    @RequestMapping(params = "updateTrainerSkill", method = RequestMethod.POST)
    public String updateTrainerSkillsByRoleCode(TrainerDto trainerDto,ModelMap model) throws Exception
    {
    	try
    	{
    		long trainerCode = trainerDto.getTrainerCode();
    		String trainerName = trainerDto.getUserName();
    		List<Long> lstSkillCodes = trainerDto.getLstSkillCodes();
    		
    		//update Trainer Skill Mapping in the database -> Start
			boolean isTrainerSkillUpdated = trainerSkillService.updateTrainerSkillList(trainerCode , lstSkillCodes);
			if(isTrainerSkillUpdated)
			{
    			model.addAttribute("successMsg", "updateTrainerSkillSuccessMsg");
    			model.addAttribute("action", "view");
    			model.addAttribute("readOnly", true);
			}
			else
			{
				model.addAttribute("errorMessages", "noChange");
				model.addAttribute("action", "update");
    			model.addAttribute("readOnly", false);
			}
			//update Trainer Skill Mapping in the database -> End
			List<TrainerSkillDto> lstTrainerSkillDto = trainerSkillService.getTrainerSkillList(trainerCode);
			trainerDto = trainerSkillService.getTrainerDtoFromCode(trainerCode); 
			model.addAttribute("lstTrainerSkillDto", lstTrainerSkillDto);
			model.addAttribute("trainerDto", trainerDto);
			model.addAttribute("trainerName", trainerName);
    		return "trainerSkill";
    	}
    	catch(Exception e)
    	{
    		LOGGER.error("Error description", e);
			throw e;
    	}
    }
    
    
}
