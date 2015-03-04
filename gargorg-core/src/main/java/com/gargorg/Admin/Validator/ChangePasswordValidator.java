package com.gargorg.Admin.Validator;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.gargorg.Admin.dto.ChangePasswordDto;

@Component
public class ChangePasswordValidator implements Validator 
 {
	/**
	 * This Validator validates just Change Password instances
	 */
	@Autowired
	HttpServletRequest request;
	
	@Value("${defaultPassword}") 
	private String defaultPassword;

	@Override
    public boolean supports(Class<?> clazz) {
        return ChangePasswordDto.class.isAssignableFrom(clazz);
    }
	
	@Override
	public void validate(Object obj, Errors errors) {
		
		String datePattern = "\\d{2}/\\d{2}/\\d{4}";		// datePattern should be dd/mm/yyyy
		
		ChangePasswordDto changePasswordDto = (ChangePasswordDto) obj;
		if(changePasswordDto.getOldPassword() != null)
		{
	        if(changePasswordDto.getOldPassword().equalsIgnoreCase(changePasswordDto.getUsername())) {	
	        	errors.rejectValue("oldPassword", "ChangePasswordDto.oldPassword.notEqualTo");
	        }
		}
		if(changePasswordDto.getNewPassword() != null)
		{
	        if(changePasswordDto.getNewPassword().equalsIgnoreCase(changePasswordDto.getUsername())	 
	        		|| changePasswordDto.getNewPassword().equalsIgnoreCase(changePasswordDto.getOldPassword()) 
	        		|| changePasswordDto.getNewPassword().equalsIgnoreCase(defaultPassword)) {
	        	errors.rejectValue("newPassword", "ChangePasswordDto.newPassword.notEqualTo");
	        }
		}
		if(changePasswordDto.getConfirmNewPassword() != null)
		{
	        if(!changePasswordDto.getConfirmNewPassword().equals(changePasswordDto.getNewPassword())) {	
	        	errors.rejectValue("confirmNewPassword", "ChangePasswordDto.confirmNewPassword.equalTo");
	        }
		}
		
		if(request.getParameter("txtUserDoj") != null)
		{
			if(changePasswordDto.getTxtUserDoj() == null || changePasswordDto.getTxtUserDoj().equals(""))
			{
				errors.rejectValue("txtUserDoj", "ChangePasswordDto.txtUserDoj.NotBlank");
			}else if(!changePasswordDto.getTxtUserDoj().matches(datePattern)){
				errors.rejectValue("txtUserDoj", "ChangePasswordDto.txtUserDoj.Pattern");
			}
		}
		
		if(request.getParameter("txtUserDob") != null)
		{
			if(changePasswordDto.getTxtUserDob() == null || changePasswordDto.getTxtUserDob().equals(""))
			{
				errors.rejectValue("txtUserDob", "ChangePasswordDto.txtUserDob.NotBlank");
			}else if(!changePasswordDto.getTxtUserDob().matches(datePattern)){
				errors.rejectValue("txtUserDob", "ChangePasswordDto.txtUserDob.Pattern");
			}
		}
		
		
		if(request.getParameter("userSecAnswer") != null)
		{
			if(changePasswordDto.getUserSecAnswer() == null || changePasswordDto.getUserSecAnswer().equals(""))
			{
				errors.rejectValue("userSecAnswer", "ChangePasswordDto.userSecAnswer.NotBlank");
			}else if(changePasswordDto.getUserSecAnswer().length() < 3 || changePasswordDto.getUserSecAnswer().length() > 20){
				errors.rejectValue("userSecAnswer", "ChangePasswordDto.userSecAnswer.Length");
			}
		}
		
		if(request.getParameter("userOtp") != null)
		{
			if(changePasswordDto.getUserOtp() == null || changePasswordDto.getUserOtp().equals(""))
			{
				errors.rejectValue("userOtp", "ChangePasswordDto.userOtp.NotBlank");
			}else if(changePasswordDto.getUserOtp().length() != 4){
				errors.rejectValue("userOtp", "ChangePasswordDto.userOtp.Length");
			}
		}
	}
}
