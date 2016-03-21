/**
 * 
 */
package com.gargorg.common.Utils;

import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.gargorg.common.constant.CommonConstants;

/**
 * @author piyush
 *	This class contains all the functions which are used through out the project. 
 */
public class CommonFunctions 
{
	private static final Logger LOGGER = LoggerFactory.getLogger(CommonFunctions.class);
	
	public static Date addMinutesInDate(Date date , int minutes)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, minutes);
		date = cal.getTime();
		return date;
	}
	
	public static Date addDaysInDate(Date date , int days)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days);
		date = cal.getTime();
		return date;
	}
	
	public static long getLangIdByLocale(String locale)
	{
		long langId = CommonConstants.ENGLISH;  //Default language Id for English Locale (en)
		if(locale != null && locale.equals("hi"))
		{
			langId = CommonConstants.HINDI;
		}
		return langId;
	}
	
	/** Using Calendar - THE CORRECT WAY**/  
	public static long daysBetween(Date startDate, Date endDate) 
	{  
	  //assert: startDate should not be after endDate
		Assert.isTrue(!startDate.after(endDate));
		Calendar startDateCal = Calendar.getInstance();
		startDateCal.setTime(startDate);
        Calendar endDateCal = Calendar.getInstance();
        endDateCal.setTime(endDate);
	  Calendar date = (Calendar) startDateCal.clone();  
	  long daysBetween = 0;  
	  while (date.before(endDateCal)) 
	  {  
	    date.add(Calendar.DAY_OF_MONTH, 1);  
	    daysBetween++;  
	  }  
	  return daysBetween;  
	}
	
    public static String determineTrainingStatus(Date trainingStartTime , Date trainingEndTime , Date trainingDate , Date currDate)	{
    	String trainingStatus = null;
    	Date trainingStartDate = mergeDateTime(trainingDate, trainingStartTime);
    	Date trainingEndDate = mergeDateTime(trainingDate, trainingEndTime);
    	if(currDate.before(trainingStartDate))
    	{
    		trainingStatus = CommonConstants.TRAINING_SCHEDULED;
    	}
    	else if(currDate.after(trainingStartDate) && currDate.before(trainingEndDate))
    	{
    		trainingStatus = CommonConstants.TRAINING_STARTED;
    	}
    	else if(currDate.after(trainingEndDate))
    	{
    		trainingStatus = CommonConstants.TRAINING_COMPLETED;
    	}
    	else
    	{
    		trainingStatus = "Not Defined";
    	}
    	return trainingStatus;
    }
    
    private static Date mergeDateTime(Date date, Date time) {
    		Calendar calendarA = Calendar.getInstance();
    		calendarA.setTime(date);

    		Calendar calendarB = Calendar.getInstance();
    		calendarB.setTime(time);

    		calendarA.set(Calendar.HOUR_OF_DAY, calendarB.get(Calendar.HOUR_OF_DAY));
    		calendarA.set(Calendar.MINUTE, calendarB.get(Calendar.MINUTE));
    		calendarA.set(Calendar.SECOND, calendarB.get(Calendar.SECOND));
    		calendarA.set(Calendar.MILLISECOND, calendarB.get(Calendar.MILLISECOND));

    		Date result = calendarA.getTime();
    		return result;
    }
}
