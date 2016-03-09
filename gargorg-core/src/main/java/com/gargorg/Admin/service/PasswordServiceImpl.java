/**
 * This Service will contains the functionality for loading logged in user information.
 */
package com.gargorg.Admin.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.gargorg.Admin.dao.CmnLookUpDao;
import com.gargorg.Admin.dao.PasswordDao;
import com.gargorg.Admin.dao.UserDetailsDao;
import com.gargorg.Admin.dto.ChangePasswordDto;
import com.gargorg.Admin.dto.CmnLookupMstDto;
import com.gargorg.Admin.dto.ForgotPasswordDto;
import com.gargorg.Masters.valueObject.OrgEmpMst;
import com.gargorg.Masters.valueObject.OrgUserMst;
import com.gargorg.common.Utils.CommonFunctions;
import com.gargorg.common.Utils.CommonUtility;


/**
 * @author piyush
 *
 */
@Service
@Transactional
public class PasswordServiceImpl implements PasswordService , MessageSourceAware 
{
	@Autowired
	private PasswordDao passwordDao;
	@Autowired
	private UserDetailsDao userDetailsDao;
	@Autowired
	private CommonUtility commonUtility;
	@Autowired
	private CmnLookUpDao cmnLookUpDao;
	@Autowired
	private SmsService smsService;
	@Value("${securityRequired}") 
	private String securityRequired;
	@Value("${dojRequired}") 
	private String dojRequired;
	@Value("${dobRequired}") 
	private String dobRequired;
	@Value("${otpRequired}") 
	private String otpRequired;
	@Value("${secQuesRequired}") 
	private String secQuesRequired;
	@Value("${securityQuestions}") 
	private String securityQuestions;
	@Value("${otpStartRange}") 
	private long otpStartRange;
	@Value("${otpEndRange}") 
	private long otpEndRange;
	@Value("${otpValidityPeriod}") 
	private int otpValidityPeriod;
	@Value("${passwordExpireDays}") 
	private int passwordExpireDays;
	
	protected MessageSourceAccessor messages;
	
	public PasswordServiceImpl()
	{
		this.messages = SpringSecurityMessageSource.getAccessor();
	}
	
	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messages = new MessageSourceAccessor(messageSource);
	}
	

	private static final Logger LOGGER = LoggerFactory.getLogger(PasswordServiceImpl.class);
	
	//Method to get Security Questions and Generate OTP for change password action from database -> Start
    @Override
	public ChangePasswordDto getSecurityQuestionAndOtp(String username , String locale) throws Exception
    {
    	ChangePasswordDto changePasswordDto = new ChangePasswordDto();
    	Map<String,Object> resultMap = new HashMap<String,Object>();
    	try
    	{
	    	if(locale != null && locale.equals(""))
	    	{
	    		locale = "en";
	    	}
	    	long langId = passwordDao.getLanguageByLocale(locale);
	    	CmnLookupMstDto securityRequiredLookUp = cmnLookUpDao.getLookUpByLookUpName(securityRequired, langId);
	    	if(securityRequiredLookUp != null && securityRequiredLookUp.isActivateFlag())
	    	{
	    		List<CmnLookupMstDto> lstSecurityRequiredLookUp = cmnLookUpDao.getLookUpByParentLookUpCode(securityRequiredLookUp.getLookupCode() , langId);
	    		for(CmnLookupMstDto cmnLookupMstDto :lstSecurityRequiredLookUp)
	    		{
	    			resultMap.put(cmnLookupMstDto.getLookupName() , cmnLookupMstDto.isActivateFlag() );
	    		}
	    	}
	    	if(resultMap.get(secQuesRequired) != null && (Boolean)resultMap.get(secQuesRequired))
	    	{
	    		CmnLookupMstDto securityQuestionsLookUp = cmnLookUpDao.getLookUpByLookUpName(securityQuestions, langId);
	    		List<CmnLookupMstDto> lstSecurityQuestionsLookUp = cmnLookUpDao.getLookUpByParentLookUpCode(securityQuestionsLookUp.getLookupCode() , langId);
	    		resultMap.put("lstSecurityQuestions" , lstSecurityQuestionsLookUp );
	    	}
	    	if(resultMap.get(otpRequired) != null && (Boolean)resultMap.get(otpRequired))
	    	{
	    		boolean isOtpGenerated = generateOtpForUser(username);
	    		resultMap.put(otpRequired, isOtpGenerated);	
	    	}
	    	// Generate ChangePasswordDto form bean and set necessary values -> Start
	    	for (Map.Entry<String,Object> entry : resultMap.entrySet())
			{
	    		if(entry.getKey().equals(dojRequired)){
	    			changePasswordDto.setDojRequired((Boolean)entry.getValue());
	    		}else if(entry.getKey().equals(dobRequired)){
	    			changePasswordDto.setDobRequired((Boolean)entry.getValue());
	    		}else if(entry.getKey().equals(secQuesRequired)){
	    			changePasswordDto.setSecQuesRequired((Boolean)entry.getValue());
	    			if((Boolean)resultMap.get(secQuesRequired))
	    			{
	    				changePasswordDto.setLstSecurityQuestionsLookUp((List<CmnLookupMstDto>)resultMap.get("lstSecurityQuestions"));
	    			}
	    		}else if(entry.getKey().equals(otpRequired)){
	    			changePasswordDto.setOtpRequired((Boolean)entry.getValue());
	    		}
			}
	    	// Generate ChangePasswordDto form bean and set necessary values -> End
    	}
    	catch(Exception e)
    	{
    		throw e;
    	}
    	return changePasswordDto;
    }
	//Method to get Security Questions and Generate OTP for change password action from database -> End
    
    //Method to generate otp for User with given username - > Start
    @Override
	public boolean generateOtpForUser(String username) throws Exception
    {
    	boolean isOtpGenerated = false;
    	try
    	{
    		OrgUserMst orgUserMst = userDetailsDao.findByUsername(username);
	    	if(orgUserMst != null && orgUserMst.isOtpEnabled())
	    	{
	    		long generatedOtp = generateOtp();
	    		orgUserMst.setOtp(generatedOtp);
	    		Date currDate = commonUtility.getCurrentDateFromDB();
	    		orgUserMst.setOtpValidity(CommonFunctions.addMinutesInDate(currDate, otpValidityPeriod));
	    		userDetailsDao.updateUser(orgUserMst);
	    		// Call send sms service for sending OTP -> Start
	    		List<Long> lstMobileNo = new ArrayList<Long>();
	    		lstMobileNo.add(orgUserMst.getRegMobileNo());
	    		String message = "OTP+for+your+request+is+"+generatedOtp+".Please+enter+this+OTP+to+verify+your+identity+and+proceed.";
	    		smsService.sendSms(lstMobileNo, message);
	    		// Call send sms service for sending OTP -> End
	    		isOtpGenerated = true;
	    	}
    	}
    	catch(Exception e)
    	{
    		throw e;
    	}
    	return isOtpGenerated;
    }
    //Method to generate otp for User with given username - > End
    
    public long generateOtp()
    {
        //get the range, casting to long to avoid overflow problems
        long range = otpEndRange - otpStartRange + 1;
        // compute a fraction of the range, 0 <= frac < range
        Random random = new Random();
        long fraction = (long)(range * random.nextDouble());
        long otp =  fraction + otpStartRange;
        System.out.println(otp);
        return otp;
    }
    
    @Override
	public Map<String,Object> changePassword(ChangePasswordDto changePasswordDto) throws Exception
    {
    	// Determine locale selected by User -> Start
        Locale locale = StringUtils.parseLocaleString(changePasswordDto.getLocale().toLowerCase());
        // Determine locale selected by User -> End
    	boolean isPasswordChangedSuccessfully = false;
    	Map<String,Object> resultMap = new HashMap<String, Object>();
    	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    	try
    	{
    		OrgUserMst orgUserMst = userDetailsDao.findByUsername(changePasswordDto.getUsername());
    		OrgEmpMst orgEmpMst = userDetailsDao.getEmployeeDetails(changePasswordDto.getUsername(), changePasswordDto.getLocale());
    		Date currDate = commonUtility.getCurrentDateFromDB();
    		StringBuilder errorMessages = new StringBuilder();
    		// Validate form data with the data retrieved from database -> Start
    		if(changePasswordDto.getOldPassword() != null)
    		{
    			if(!CommonFunctions.compareEncodedPassword(changePasswordDto.getOldPassword() , orgUserMst.getPassword()))
    			{
    				errorMessages.append(messages.getMessage("oldPassword_wrong","You entered wrong old password.",locale));
    			}
    		}
    		if(changePasswordDto.getTxtUserDoj() != null)
    		{
    			Date userDoj = formatter.parse(changePasswordDto.getTxtUserDoj());
    			if(userDoj.compareTo(orgEmpMst.getEmpDoj()) != 0)
    			{
    				errorMessages.append(messages.getMessage("userDoj_wrong","You entered wrong Date of Joining.",locale));
    			}
    		}
    		if(changePasswordDto.getTxtUserDob() != null)
    		{
    			Date userDob = formatter.parse(changePasswordDto.getTxtUserDob());
    			if(userDob.compareTo(orgEmpMst.getEmpDob()) != 0)
    			{
    				errorMessages.append(messages.getMessage("userDob_wrong","You entered wrong Date of Birth.",locale));
    			}
    		}
    		if(changePasswordDto.getSecretQueCode() != -1)
    		{
    			//if code matched then compare answer
    			if(changePasswordDto.getSecretQueCode() != orgUserMst.getSecretQueCode())
    			{
    				errorMessages.append(messages.getMessage("secretQueCode_wrong","You selected wrong Security Question.",locale));
    			}
    			else
    			{
    				String decryptedSecurityAnswer = CommonFunctions.getStandardPBEDecryptedString(orgUserMst.getSecretAnswer());
    				if(!changePasswordDto.getUserSecAnswer().equals(decryptedSecurityAnswer))
        			{
    					errorMessages.append(messages.getMessage("userSecAnswer_wrong","You entered wrong Security Answer.",locale));
        			}
    			}
    		}
    		if(changePasswordDto.getUserOtp() != null)
    		{
    			if(orgUserMst.getOtp() != null && ( orgUserMst.getOtp().longValue() != Long.valueOf(changePasswordDto.getUserOtp()).longValue()))
				{
    				errorMessages.append(messages.getMessage("userOtp_wrong","You entered wrong One Time Password.",locale));
				}
    			else if(orgUserMst.getOtpValidity() != null && currDate.after(orgUserMst.getOtpValidity()))
    			{
    				errorMessages.append(messages.getMessage("userOtp_expire","Your OTP is expired.",locale));
    			}
    		}
    		// Validate form data with the data retrieved from database -> End
    		// If form data has no problem then update the password in encrypted form -> Start
    		if(errorMessages.length() == 0)
    		{
    			orgUserMst.setPassword(CommonFunctions.getEncodedPassword(changePasswordDto.getNewPassword()));
    			orgUserMst.setPwdchangedDate(CommonFunctions.addDaysInDate(currDate, passwordExpireDays));
    			orgUserMst.setOrgUserMstByUpdatedUserId(orgUserMst);
    			orgUserMst.setUpdatedDate(currDate);
    			if(orgUserMst.getFirstlogin().equals("Y"))
				{
    				orgUserMst.setFirstlogin("N");
				}
    			userDetailsDao.updateUser(orgUserMst);
    			isPasswordChangedSuccessfully = true;
    		}
    		// If form data has no problem then update the password in encrypted form -> End
    		else
    		{
    			resultMap.put("errorMessages",errorMessages.toString());
    		}
    		resultMap.put("isPasswordChangedSuccessfully", isPasswordChangedSuccessfully);
    	}
    	catch(Exception e)
    	{
    		throw e;
    	}
    	return resultMap;
    }
    
    @Override
	public Map<String,Object> forgotPassword(ForgotPasswordDto forgotPasswordDto) throws Exception
    {
    	// Determine locale selected by User -> Start
        Locale locale = StringUtils.parseLocaleString(forgotPasswordDto.getLocale().toLowerCase());
        // Determine locale selected by User -> End
    	boolean isPasswordChangedSuccessfully = false;
    	Map<String,Object> resultMap = new HashMap<String, Object>();
    	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    	try
    	{
    		OrgUserMst orgUserMst = userDetailsDao.findByUsername(forgotPasswordDto.getUsername());
    		OrgEmpMst orgEmpMst = userDetailsDao.getEmployeeDetails(forgotPasswordDto.getUsername(), forgotPasswordDto.getLocale());
    		Date currDate = commonUtility.getCurrentDateFromDB();
    		StringBuilder errorMessages = new StringBuilder();
    		// Validate form data with the data retrieved from database -> Start
    		if(forgotPasswordDto.getTxtUserDoj() != null)
    		{
    			Date userDoj = formatter.parse(forgotPasswordDto.getTxtUserDoj());
    			if(userDoj.compareTo(orgEmpMst.getEmpDoj()) != 0)
    			{
    				errorMessages.append(messages.getMessage("userDoj_wrong","You entered wrong Date of Joining.",locale));
    			}
    		}
    		if(forgotPasswordDto.getTxtUserDob() != null)
    		{
    			Date userDob = formatter.parse(forgotPasswordDto.getTxtUserDob());
    			if(userDob.compareTo(orgEmpMst.getEmpDob()) != 0)
    			{
    				errorMessages.append(messages.getMessage("userDob_wrong","You entered wrong Date of Birth.",locale));
    			}
    		}
    		if(forgotPasswordDto.getSecretQueCode() != -1)
    		{
    			//if code matched then compare answer
    			if(forgotPasswordDto.getSecretQueCode() != orgUserMst.getSecretQueCode())
    			{
    				errorMessages.append(messages.getMessage("secretQueCode_wrong","You selected wrong Security Question.",locale));
    			}
    			else
    			{
    				String decryptedSecurityAnswer = CommonFunctions.getStandardPBEDecryptedString(orgUserMst.getSecretAnswer());
    				if(!forgotPasswordDto.getUserSecAnswer().equals(decryptedSecurityAnswer))
        			{
    					errorMessages.append(messages.getMessage("userSecAnswer_wrong","You entered wrong Security Answer.",locale));
        			}
    			}
    		}
    		if(forgotPasswordDto.getUserOtp() != null)
    		{
    			if(orgUserMst.getOtp() != null && ( orgUserMst.getOtp().longValue() != Long.valueOf(forgotPasswordDto.getUserOtp()).longValue()))
				{
    				errorMessages.append(messages.getMessage("userOtp_wrong","You entered wrong One Time Password.",locale));
				}
    			else if(orgUserMst.getOtpValidity() != null && currDate.after(orgUserMst.getOtpValidity()))
    			{
    				errorMessages.append(messages.getMessage("userOtp_expire","Your OTP is expired.",locale));
    			}
    		}
    		// Validate form data with the data retrieved from database -> End
    		// If form data has no problem then update the password in encrypted form -> Start
    		if(errorMessages.length() == 0)
    		{
    			orgUserMst.setPassword(CommonFunctions.getEncodedPassword(forgotPasswordDto.getNewPassword()));
    			orgUserMst.setPwdchangedDate(CommonFunctions.addDaysInDate(currDate, passwordExpireDays));
    			orgUserMst.setOrgUserMstByUpdatedUserId(orgUserMst);
    			orgUserMst.setUpdatedDate(currDate);
    			if(orgUserMst.getFirstlogin().equals("Y"))
				{
    				orgUserMst.setFirstlogin("N");
				}
    			userDetailsDao.updateUser(orgUserMst);
    			isPasswordChangedSuccessfully = true;
    		}
    		// If form data has no problem then update the password in encrypted form -> End
    		else
    		{
    			resultMap.put("errorMessages",errorMessages.toString());
    		}
    		resultMap.put("isPasswordChangedSuccessfully", isPasswordChangedSuccessfully);
    	}
    	catch(Exception e)
    	{
    		throw e;
    	}
    	return resultMap;
    }
}


