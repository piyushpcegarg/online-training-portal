/**
 * 
 */
package com.trainingportal.Masters.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.trainingportal.Masters.dto.SkillDto;


/**
 * @author piyush
 *
 */
@Service
public interface SkillService 
{
	public List<SkillDto> getSkillList() throws Exception;
	public SkillDto getSkillDtoFromCode(long skillCode) throws Exception;
	public void saveSkill(SkillDto skillDto) throws Exception;
	public boolean updateSkill(SkillDto skillDto) throws Exception;
	public boolean isSkillExist(SkillDto skillDto) throws Exception;
}
