/**
 * 
 */
package com.trainingportal.Masters.dao;

import java.util.List;

import com.trainingportal.Masters.dto.SkillDto;
import com.trainingportal.Masters.valueObject.OrgSkillMst;

/**
 * @author piyush
 *
 */
public interface SkillDao 
{
	public List<SkillDto> getAllSkills() throws Exception;
	public void save(OrgSkillMst skill) throws Exception;
	public OrgSkillMst getSkillByCode(long skillCode) throws Exception;
	public void update(OrgSkillMst skill) throws Exception;
	public Long getNextSkillCode() throws Exception;
	public OrgSkillMst getSkillByName(String skillName) throws Exception;
}
