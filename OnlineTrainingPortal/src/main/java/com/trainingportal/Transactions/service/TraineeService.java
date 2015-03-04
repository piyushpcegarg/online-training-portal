/**
 * 
 */
package com.trainingportal.Transactions.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.trainingportal.Transactions.dto.TraineeDto;


/**
 * @author piyush
 *
 */
@Service
public interface TraineeService
{
	public List<TraineeDto> getTraineeList(long trainingCode) throws Exception;
	public boolean deleteTrainee(long trainingCode,List<Long> lstTraineeId) throws Exception;
}
