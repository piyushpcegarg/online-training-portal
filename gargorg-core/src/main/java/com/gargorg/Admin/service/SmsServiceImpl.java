/**
 *  This file is responsible for sending SMS to the mobile
 */
package com.gargorg.Admin.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
/**
 * @author piyush
 *
 */
@Service
public class SmsServiceImpl implements SmsService
{
	@Value("${emailId}") 
	private String emailId;
	@Value("${password}")
	private String password;
	@Value("${senderId}")
	private String senderId;
	@Value("${serviceName}")
	private String serviceName;
	@Value("${url}")
	private String url;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SmsServiceImpl.class);
	// HTTP POST request
	@Override
	public void sendSms(List<Long> lstMobileNo , String message) throws Exception 
	{
		try
		{
			Assert.notNull(lstMobileNo, "List of mobile no cannot be null.");
			Assert.notEmpty(lstMobileNo, "List of mobile no cannot be empty.");
			Assert.hasLength(message, "message cannot be null or empty");
			Assert.doesNotContain(message, " ", "message cannot contain whitespaces, replace these with + symbol.");
			// Create comman separated moile no string from lstMobileNo -> Start
			StringBuilder strBuilderMobileNo = new StringBuilder();
			int lstMobileNoSize = lstMobileNo.size();
			for(int loopCount = 0 ; loopCount < lstMobileNoSize ; loopCount++)
			{
				if(loopCount != (lstMobileNoSize - 1))
				{
					strBuilderMobileNo.append("91").append(lstMobileNo.get(loopCount).toString()).append(",");
				}
				else
				{
					strBuilderMobileNo.append("91").append(lstMobileNo.get(loopCount).toString());
				}
			}
			// Create comman separated moile no string from lstMobileNo -> End
			
			String urlParameters = "EmailID="+emailId+"&Password="+password+"&MobileNo="+strBuilderMobileNo.toString()+"&SenderID="+senderId+"&Message="+message+"&ServiceName="+serviceName;
			URL obj = new URL(url+urlParameters);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	 
			//add reuqest header
			con.setRequestMethod("POST");
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
	 
	 
			// Send post request
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();
	 
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
	 
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
	 
			//print result and log it
			System.out.println(response.toString());
			LOGGER.info("Response received for sms:"+response.toString());
		}
		catch(Exception e)
		{
			LOGGER.error("Error description", e);
		}
	}
}
