/**
 * This Service will contain all the functionality related to trainee.
 * Like add , update and delete
 */
package com.trainingportal.Transactions.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trainingportal.Transactions.dao.TraineeDao;
import com.trainingportal.Transactions.dto.TraineeDto;


/**
 * @author piyush
 *
 */
@Service
@Transactional
public class TraineeServiceImpl implements TraineeService
{
	@Autowired
	private TraineeDao traineeDAO;
	
	//Method to Get all Trainees from database -> Start
	@Override
	public List<TraineeDto> getTraineeList(long trainingCode) throws Exception
	{
		List<TraineeDto> lstTraineeDto = null;
		try
		{
			lstTraineeDto = traineeDAO.getAllTrainees(trainingCode);
		}
		catch(Exception e)
		{
			throw e;
		}
		return lstTraineeDto;
	}
	//Method to Get all Trainees from database -> End
	
	//Method to delete Trainees from training in the database -> Start
	@Override
	public boolean deleteTrainee(long trainingCode,List<Long> lstTraineeId) throws Exception
	{
		boolean isTraineesDeleted = false;
		try
		{
			isTraineesDeleted = traineeDAO.deleteTrainee(trainingCode,lstTraineeId);
		}
		catch(Exception e)
		{
			throw e;
		}
		return isTraineesDeleted;
	}
	//Method to delete Trainees from training in the database -> End
}
