/**
 * 
 */
package com.trainingportal.Masters.dao;

import java.util.List;

import com.gargorg.Masters.dto.UserDto;
import com.trainingportal.Masters.dto.TrainerDto;
import com.trainingportal.Masters.valueObject.OrgTrainerMst;

/**
 * @author piyush
 *
 */
public interface TrainerDao 
{
	public List<TrainerDto> getAllTrainers() throws Exception;
	public void save(OrgTrainerMst trainer) throws Exception;
	public void update(OrgTrainerMst trainer) throws Exception;
	public OrgTrainerMst getTrainerByCode(long trainerCode) throws Exception;
	public Long getNextTrainerCode() throws Exception;
	public OrgTrainerMst getTrainerByUserId(long userId) throws Exception;
	public List<UserDto> getAllUsers(String username) throws Exception;
}
