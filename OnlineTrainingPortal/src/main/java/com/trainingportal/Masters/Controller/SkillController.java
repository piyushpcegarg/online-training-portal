/**
 * This will all the functionalities related to skill.
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

import com.trainingportal.Masters.dto.SkillDto;
import com.trainingportal.Masters.service.SkillService;

/**
 * @author piyush
 *
 */
@Controller
@RequestMapping("/skill")
public class SkillController 
{
	private static final Logger LOGGER = LoggerFactory.getLogger(SkillController.class);
    
	@Autowired  
    private SkillService skillService;  
      
    /** 
     * Get called when Skill main link is clicked from menus for loading all the skills from database. 
     * displayskill.jsp page is opened , which shows all the skills stored in the database with skill name.
     * This page shows 3 buttons (Create , Update , Close)
     * <p>Expected HTTP GET and request '/skill/allSkills'.</p>
     */
    @RequestMapping(value = "/allSkills", method = RequestMethod.GET)
    public String getAllSkills(ModelMap model) throws Exception
    {
    	try
    	{
    		List<SkillDto> skillList = skillService.getSkillList();
    		model.addAttribute("skillList", skillList);
    		model.addAttribute("skillDto", new SkillDto());
    		return "displayskill";
    	}
    	catch(Exception e)
    	{
    		LOGGER.error("Error description", e);
			throw e;
    	}
    }
    
    @RequestMapping(params = "createSkillPage", method = RequestMethod.POST)
    public String getCreateSkillPage(ModelMap model) throws Exception
    {
    	try
    	{
    		SkillDto skillDto = new SkillDto();
    		model.addAttribute("action", "create");
    		model.addAttribute("skillDto", skillDto);
    		return "skill";
    	}
    	catch(Exception e)
    	{
    		LOGGER.error("Error description", e);
			throw e;
    	}
    }
    
    @RequestMapping(params = "updateSkillPage", method = RequestMethod.POST)
    public String getUpdateSkillPage(@RequestParam long skillCode , ModelMap model) throws Exception
    {
    	try
    	{
    		SkillDto skillDto = skillService.getSkillDtoFromCode(skillCode);
    		model.addAttribute("skillDto", skillDto);
    		model.addAttribute("action", "update");
    		model.addAttribute("readOnly", false);
    		return "skill";
    	}
    	catch(Exception e)
    	{
    		LOGGER.error("Error description", e);
			throw e;
    	}
    }
    
    @RequestMapping(params = "viewSkillPage", method = RequestMethod.POST)
    public String getViewSkillPage(@RequestParam long skillCode , ModelMap model) throws Exception
    {
    	try
    	{
    		SkillDto skillDto = skillService.getSkillDtoFromCode(skillCode);
    		model.addAttribute("skillDto", skillDto);
    		model.addAttribute("action", "view");
    		model.addAttribute("readOnly", true);
    		return "skill";
    	}
    	catch(Exception e)
    	{
    		LOGGER.error("Error description", e);
			throw e;
    	}
    }
    
    @RequestMapping(params = "saveSkill", method = RequestMethod.POST)
    public String saveSkill(@Valid SkillDto skillDto ,BindingResult result, ModelMap model) throws Exception
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
    			boolean isSkillExist = skillService.isSkillExist(skillDto);
    			if(isSkillExist)
    			{
    				model.addAttribute("errorMessages", "skillAlreadyExist");
    				model.addAttribute("action", "create");
        			model.addAttribute("readOnly", false);
    			}
    			else
    			{
	    			//Save skillDto in the database -> Start
    				skillDto.setActivateFlag(true);			// At time of save skill will be active
	    			skillService.saveSkill(skillDto);
	    			model.addAttribute("successMsg", "createSkillSuccessMsg");
	    			model.addAttribute("action", "view");
	    			model.addAttribute("readOnly", true);
	    			//Save skillDto in the database -> End
    			}
    		}
    	}
    	catch(Exception e)
    	{
    		LOGGER.error("Error description", e);
			throw e;
    	}
    	return "skill";
    }
    
    @RequestMapping(params = "updateSkill", method = RequestMethod.POST)
    public String updateSkill(@Valid SkillDto skillDto ,BindingResult result, ModelMap model) throws Exception
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
    			//update skillDto in the database -> Start
    			boolean isSkillUpdated = skillService.updateSkill(skillDto);
    			if(isSkillUpdated)
    			{
	    			model.addAttribute("successMsg", "updateSkillSuccessMsg");
	    			model.addAttribute("action", "view");
	    			model.addAttribute("readOnly", true);
    			}
    			else
    			{
    				model.addAttribute("errorMessages", "noChange");
    				model.addAttribute("action", "update");
        			model.addAttribute("readOnly", false);
    			}
    			//update skillDto in the database -> End
    		}
    	}
    	catch(Exception e)
    	{
    		LOGGER.error("Error description", e);
			throw e;
    	}
    	return "skill";
    }
}
