/**
 * 
 */
package com.trainingportal.Masters.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.trainingportal.Masters.dto.TrainerDto;
import com.trainingportal.Masters.dto.TrainerSkillDto;


/**
 * @author piyush
 *
 */
@Service
public interface TrainerSkillService 
{
	public List<TrainerSkillDto> getTrainerSkillList(long trainerCode) throws Exception;
	public boolean updateTrainerSkillList(long trainerCode , List<Long> lstSkillCodes) throws Exception;
	public TrainerDto getTrainerDtoFromCode(long trainerCode) throws Exception;
}
