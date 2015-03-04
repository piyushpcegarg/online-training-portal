/**
 * 
 */
package com.trainingportal.Transactions.dao;

import java.util.List;

import com.trainingportal.Transactions.dto.TraineeDto;



/**
 * @author piyush
 *
 */
public interface TraineeDao 
{
	public List<TraineeDto> getAllTrainees(long trainingCode) throws Exception;
	public boolean deleteTrainee(long trainingCode, List<Long> lstTraineeId) throws Exception;
}
