package com.gargorg.Masters.Validator;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.gargorg.Masters.dto.UserDto;
import com.gargorg.Masters.service.UserService;
import com.gargorg.Masters.valueObject.OrgUserMst;
import com.gargorg.common.constant.CommonConstants;

@Component
public class UserFormValidator implements Validator 
 {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserFormValidator.class);
	/**
	 * This Validator validates User Creation Form
	 */
	@Autowired  
    private UserService userService;
	@Autowired
	HttpServletRequest request;
	
	@Override
    public boolean supports(Class<?> clazz) {
        return UserDto.class.isAssignableFrom(clazz);
    }
	
	@Override
	public void validate(Object obj, Errors errors) {
		try
		{
			UserDto userDto = (UserDto) obj;
			String saveUserParam = request.getParameter("saveUser");
			if(saveUserParam != null && !saveUserParam.equals(CommonConstants.BLANK_STR))
			{
				if(userDto.getUserName() != null && !userDto.getUserName().equals(""))
				{
					OrgUserMst user = userService.getUserFromUsername(userDto.getUserName());
					if(user != null)
					{
						errors.rejectValue("userName", "UserDto.username.Exist");
					}
				}
				if(userDto.getLstRoleCodes() == null || userDto.getLstRoleCodes().isEmpty())
				{
					errors.rejectValue("lstRoleCodes", "UserDto.lstRoleCodes.NotEmpty");
				}
				if(userDto.getLstRoles() == null || userDto.getLstRoles().isEmpty())
				{
					errors.rejectValue("lstRoles", "UserDto.lstRoles.NotEmpty");
				}
			}
			
			if(userDto.getUserImage() != null && userDto.getUserImage().isEmpty())
			{
				if(saveUserParam != null && !saveUserParam.equals(CommonConstants.BLANK_STR))
				{
					errors.rejectValue("userImage", "UserDto.userImage.NotNull");
				}
			}
			else
			{
				if(userDto.getUserImage() != null && !(userDto.getUserImage().getContentType().equals("image/jpeg") || userDto.getUserImage().getContentType().equals("image/gif") || userDto.getUserImage().getContentType().equals("image/png")))
				{
					errors.rejectValue("userImage", "UserDto.userImage.Extension");
				}
				else if(userDto.getUserImage() != null && userDto.getUserImage().getSize() > 10240)
				{
					errors.rejectValue("userImage", "UserDto.userImage.Size");
				}
				else
				{
					String newEncodedImageString = new String(Base64.encodeBase64(userDto.getUserImage().getBytes()));
					if(newEncodedImageString.equals(userDto.getEncodedImageString()))
					{
						errors.rejectValue("userImage", "UserDto.userImage.SameImage");
					}
				}
			}
		}
		catch(Exception e)
    	{
    		LOGGER.error("Error description", e);
    	}
	}
}
