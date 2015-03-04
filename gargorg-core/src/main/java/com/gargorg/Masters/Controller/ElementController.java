/**
 * This will all the functionalities related to element.
 */
package com.gargorg.Masters.Controller;


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

import com.gargorg.Admin.dto.OrgElementMstDto;
import com.gargorg.Masters.dto.ElementDto;
import com.gargorg.Masters.service.ElementService;

/**
 * @author piyush
 *
 */
@Controller
@RequestMapping("/element")
public class ElementController 
{
	private static final Logger LOGGER = LoggerFactory.getLogger(ElementController.class);
    
	@Autowired  
    private ElementService elementService;  
      
    /** 
     * Get called when Element main link is clicked from menus for loading all the elements from database. 
     * displayelement.jsp page is opened , which shows all the elements stored in the database with element name.
     * This page shows 3 buttons (Create , Update , Close)
     * <p>Expected HTTP GET and request '/element/allElements'.</p>
     */
	// The following method called when user click on navigation menus
    @RequestMapping(value = "/allElements", method = RequestMethod.GET)
    public String getAllElements(ModelMap model) throws Exception
    {
    	try
    	{
    		List<OrgElementMstDto> elementList = elementService.getElementList();
    		model.addAttribute("elementList", elementList);
    		model.addAttribute("orgElementMstDto", new OrgElementMstDto());
    		
    		return "displayelement";
    	}
    	catch(Exception e)
    	{
    		LOGGER.error("Error description", e);
			throw e;
    	}
    }
    
    @RequestMapping(params = "createElementPage", method = RequestMethod.POST)
    public String getCreateElementPage(ModelMap model) throws Exception
    {
    	try
    	{
    		ElementDto elementDto = new ElementDto();
    		elementDto.setEditable(true);				// By default element should be editable
    		elementDto.setLstOrgElementMstDto(elementService.getElementList());		//Get All elements to fill in the parent element dropdown
    		model.addAttribute("action", "create");
    		model.addAttribute("elementDto", elementDto);
    		return "element";
    	}
    	catch(Exception e)
    	{
    		LOGGER.error("Error description", e);
			throw e;
    	}
    }
    
    @RequestMapping(params = "updateElementPage", method = RequestMethod.POST)
    public String getUpdateElementPage(@RequestParam long elementCode , ModelMap model) throws Exception
    {
    	try
    	{
    		ElementDto elementDto = elementService.getElementDtoFromCode(elementCode);
    		model.addAttribute("elementDto", elementDto);
    		model.addAttribute("action", "update");
    		model.addAttribute("readOnly", false);
    		return "element";
    	}
    	catch(Exception e)
    	{
    		LOGGER.error("Error description", e);
			throw e;
    	}
    }
    
    @RequestMapping(params = "viewElementPage", method = RequestMethod.POST)
    public String getViewElementPage(@RequestParam long elementCode , ModelMap model) throws Exception
    {
    	try
    	{
    		ElementDto elementDto = elementService.getElementDtoFromCode(elementCode);
    		model.addAttribute("elementDto", elementDto);
    		model.addAttribute("action", "view");
    		model.addAttribute("readOnly", true);
    		return "element";
    	}
    	catch(Exception e)
    	{
    		LOGGER.error("Error description", e);
			throw e;
    	}
    }
    
    @RequestMapping(params = "saveElement", method = RequestMethod.POST)
    public String saveElement(@Valid ElementDto elementDto ,BindingResult result, ModelMap model) throws Exception
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
    			boolean isElementExist = elementService.isElementExist(elementDto);
    			if(isElementExist)
    			{
    				model.addAttribute("errorMessages", "elementAlreadyExist");
    				model.addAttribute("action", "create");
        			model.addAttribute("readOnly", false);
    			}
    			else
    			{
    				//Save elementDto in the database -> Start
	    			elementDto.setActivateFlag(true);			// At time of save element will be active
	    			elementService.saveElement(elementDto);
	    			model.addAttribute("successMsg", "createElementSuccessMsg");
	    			model.addAttribute("action", "view");
	    			model.addAttribute("readOnly", true);
	    			//Save elementDto in the database -> End
    			}
    		}
    	}
    	catch(Exception e)
    	{
    		LOGGER.error("Error description", e);
			throw e;
    	}
    	return "element";
    }
    
    @RequestMapping(params = "updateElement", method = RequestMethod.POST)
    public String updateElement(@Valid ElementDto elementDto ,BindingResult result, ModelMap model) throws Exception
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
    			//update elementDto in the database -> Start
    			boolean isElementUpdated = elementService.updateElement(elementDto);
    			if(isElementUpdated)
    			{
	    			model.addAttribute("successMsg", "updateElementSuccessMsg");
	    			model.addAttribute("action", "view");
	    			model.addAttribute("readOnly", true);
    			}
    			else
    			{
    				model.addAttribute("errorMessages", "noChange");
    				model.addAttribute("action", "update");
        			model.addAttribute("readOnly", false);
    			}
    			//update elementDto in the database -> End
    		}
    	}
    	catch(Exception e)
    	{
    		LOGGER.error("Error description", e);
			throw e;
    	}
    	return "element";
    }
    
}
