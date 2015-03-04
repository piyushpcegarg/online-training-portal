/**
 * 
 */
package com.trainingportal.Transactions.dao;

import java.util.Date;
import java.util.List;

import com.trainingportal.Masters.dto.TrainerDto;
import com.trainingportal.Transactions.dto.TrainingDto;
import com.trainingportal.Transactions.valueObject.OrgTrainingTxn;


/**
 * @author piyush
 *
 */
public interface TrainingDao 
{
	public List<TrainingDto> getAllTrainings() throws Exception;
	public List<TrainingDto> getAllTrainingsForAttendance(long trainerCode) throws Exception;
	public List<TrainingDto> getAllTrainingsForFeedback() throws Exception;
	public List<TrainerDto> getAllTrainers(String trainername) throws Exception;
	public TrainingDto getTrainingByCode(long trainingCode) throws Exception;
	public OrgTrainingTxn getTrainingTxnByCode(long trainingCode) throws Exception;
	public void save(OrgTrainingTxn training) throws Exception;
	public void update(OrgTrainingTxn training) throws Exception;
	public Long getNextTrainingCode() throws Exception;
	public Long getMaxTrainingSerialNo(Date trainingDate) throws Exception;
}
