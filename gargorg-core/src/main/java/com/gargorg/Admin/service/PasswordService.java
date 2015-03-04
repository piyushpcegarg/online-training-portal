/**
 * This Service will contains the functionality for password Management related activity.
 */
package com.gargorg.Admin.service;

import java.util.Map;

import com.gargorg.Admin.dto.ChangePasswordDto;
import com.gargorg.Admin.dto.ForgotPasswordDto;


/**
 * @author piyush
 *
 */
public interface PasswordService 
{
	public ChangePasswordDto getSecurityQuestionAndOtp(String username , String locale) throws Exception;
	public boolean generateOtpForUser(String username) throws Exception;
	public Map<String,Object> changePassword(ChangePasswordDto changePasswordDto) throws Exception;
	public Map<String,Object> forgotPassword(ForgotPasswordDto forgotPasswordDto) throws Exception;
}

