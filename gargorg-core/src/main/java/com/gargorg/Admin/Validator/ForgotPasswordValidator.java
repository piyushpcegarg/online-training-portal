package com.gargorg.Admin.Validator;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.gargorg.Admin.dto.ForgotPasswordDto;

@Component
public class ForgotPasswordValidator implements Validator 
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
        return ForgotPasswordDto.class.isAssignableFrom(clazz);
    }
	
	@Override
	public void validate(Object obj, Errors errors) {
		
		String datePattern = "\\d{2}/\\d{2}/\\d{4}";		// datePattern should be dd/mm/yyyy
		
		ForgotPasswordDto forgotPasswordDto = (ForgotPasswordDto) obj;
		if(forgotPasswordDto.getNewPassword() != null)
		{
	        if(forgotPasswordDto.getNewPassword().equalsIgnoreCase(forgotPasswordDto.getUsername())	 
	        		|| forgotPasswordDto.getNewPassword().equalsIgnoreCase(defaultPassword)) {
	        	errors.rejectValue("newPassword", "ChangePasswordDto.newPassword.notEqualTo");
	        }
		}
		if(forgotPasswordDto.getConfirmNewPassword() != null)
		{
	        if(!forgotPasswordDto.getConfirmNewPassword().equals(forgotPasswordDto.getNewPassword())) {	
	        	errors.rejectValue("confirmNewPassword", "ChangePasswordDto.confirmNewPassword.equalTo");
	        }
		}
		
		if(request.getParameter("txtUserDoj") != null)
		{
			if(forgotPasswordDto.getTxtUserDoj() == null || forgotPasswordDto.getTxtUserDoj().equals(""))
			{
				errors.rejectValue("txtUserDoj", "ChangePasswordDto.txtUserDoj.NotBlank");
			}else if(!forgotPasswordDto.getTxtUserDoj().matches(datePattern)){
				errors.rejectValue("txtUserDoj", "ChangePasswordDto.txtUserDoj.Pattern");
			}
		}
		
		if(request.getParameter("txtUserDob") != null)
		{
			if(forgotPasswordDto.getTxtUserDob() == null || forgotPasswordDto.getTxtUserDob().equals(""))
			{
				errors.rejectValue("txtUserDob", "ChangePasswordDto.txtUserDob.NotBlank");
			}else if(!forgotPasswordDto.getTxtUserDob().matches(datePattern)){
				errors.rejectValue("txtUserDob", "ChangePasswordDto.txtUserDob.Pattern");
			}
		}
		
		
		if(request.getParameter("userSecAnswer") != null)
		{
			if(forgotPasswordDto.getUserSecAnswer() == null || forgotPasswordDto.getUserSecAnswer().equals(""))
			{
				errors.rejectValue("userSecAnswer", "ChangePasswordDto.userSecAnswer.NotBlank");
			}else if(forgotPasswordDto.getUserSecAnswer().length() < 3 || forgotPasswordDto.getUserSecAnswer().length() > 20){
				errors.rejectValue("userSecAnswer", "ChangePasswordDto.userSecAnswer.Length");
			}
		}
		
		if(request.getParameter("userOtp") != null)
		{
			if(forgotPasswordDto.getUserOtp() == null || forgotPasswordDto.getUserOtp().equals(""))
			{
				errors.rejectValue("userOtp", "ChangePasswordDto.userOtp.NotBlank");
			}else if(forgotPasswordDto.getUserOtp().length() != 4){
				errors.rejectValue("userOtp", "ChangePasswordDto.userOtp.Length");
			}
		}
	}
}
