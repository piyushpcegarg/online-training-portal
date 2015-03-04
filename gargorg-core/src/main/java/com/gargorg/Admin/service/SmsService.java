/* This file is responsible for sending SMS to the mobile */
package com.gargorg.Admin.service;

import java.util.List;


/**
 * @author piyush
 *
 */
public interface SmsService 
{
	public void sendSms(List<Long> lstMobileNo , String message) throws Exception;
}

