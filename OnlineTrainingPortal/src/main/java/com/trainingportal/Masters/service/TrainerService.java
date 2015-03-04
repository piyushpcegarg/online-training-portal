/**
 * 
 */
package com.trainingportal.Masters.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gargorg.Masters.dto.UserDto;
import com.trainingportal.Masters.dto.TrainerDto;
import com.trainingportal.Masters.valueObject.OrgTrainerMst;


/**
 * @author piyush
 *
 */
@Service
public interface TrainerService
{
	public List<TrainerDto> getTrainerList() throws Exception;
	public TrainerDto getTrainerDtoFromName(String username) throws Exception;
	public long saveTrainer(TrainerDto trainerDto) throws Exception;
	public boolean updateTrainer(TrainerDto trainerDto) throws Exception;
	public TrainerDto getTrainerDtoFromCode(long trainerCode) throws Exception;
	public OrgTrainerMst getTrainerByUserId(long userId) throws Exception;
	public List<UserDto> getUserList(String username) throws Exception;
}
