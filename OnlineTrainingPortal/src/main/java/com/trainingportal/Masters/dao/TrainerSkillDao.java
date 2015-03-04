/**
 * 
 */
package com.trainingportal.Masters.dao;

import java.util.List;

import com.trainingportal.Masters.valueObject.OrgTrainerSkillMpg;

/**
 * @author piyush
 *
 */
public interface TrainerSkillDao 
{
	public List<OrgTrainerSkillMpg> getAllTrainerSkills(long trainerCode) throws Exception;
	public void updateAllTrainerSkills(List<OrgTrainerSkillMpg> lstTrainerSkillMpgPersistent , List<OrgTrainerSkillMpg> lstOrgTrainerSkillMpg) throws Exception;
}
