/**
 * 
 */
package com.trainingportal.Transactions.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gargorg.Admin.dto.CmnLookupMstDto;
import com.trainingportal.Masters.dto.TrainerDto;
import com.trainingportal.Transactions.dto.TrainingDto;


/**
 * @author piyush
 *
 */
@Service
public interface TrainingService
{
	public List<TrainingDto> getTrainingList(boolean attendanceTrainingList , boolean feedbackTrainingList) throws Exception;
	public List<CmnLookupMstDto> getTrainingType() throws Exception;
	public TrainingDto getTrainingDtoFromCode(long trainingCode) throws Exception;
	public long saveTraining(TrainingDto trainingDto) throws Exception;
	public boolean updateTraining(TrainingDto trainingDto) throws Exception;
	public List<TrainerDto> getTrainerList(String trainername) throws Exception;
}
