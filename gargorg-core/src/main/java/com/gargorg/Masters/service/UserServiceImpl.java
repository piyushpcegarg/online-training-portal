/**
 * This Service will contain all the functionality related to user.
 * Like add , update and delete
 */
package com.gargorg.Masters.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gargorg.Admin.dao.CmnLookUpDao;
import com.gargorg.Admin.dao.UserDetailsDao;
import com.gargorg.Admin.dto.CmnLookupMstDto;
import com.gargorg.Admin.service.LoginDetailsVOProviderService;
import com.gargorg.Admin.valueObject.LoginDetailsVO;
import com.gargorg.Masters.dao.RoleDao;
import com.gargorg.Masters.dao.UserDao;
import com.gargorg.Masters.dto.OrgRoleMstDto;
import com.gargorg.Masters.dto.UserDto;
import com.gargorg.Masters.valueObject.OrgEmpMst;
import com.gargorg.Masters.valueObject.OrgUserImageMst;
import com.gargorg.Masters.valueObject.OrgUserMst;
import com.gargorg.Masters.valueObject.OrgUserRoleRlt;
import com.gargorg.common.Utils.CommonFunctions;
import com.gargorg.common.Utils.CommonUtility;
import com.gargorg.common.constant.CommonConstants;


/**
 * @author piyush
 *
 */
@Service
@Transactional
public class UserServiceImpl implements UserService
{
	@Autowired
	private UserDao userDAO;
	@Autowired
	private UserDetailsDao userDetailsDao;
	@Autowired
	private RoleDao roleDAO;
	@Autowired
	private CmnLookUpDao cmnLookUpDao;
	@Autowired
	private LoginDetailsVOProviderService loginDetailsVOProviderService;
	@Autowired
	private CommonUtility commonUtility;
	@Value("${securityQuestions}") 
	private String securityQuestions;
	@Value("${defaultPassword}") 
	private String defaultPassword;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
	
	//Method to Get all Security Questions from database -> Start
	@Override
	public List<CmnLookupMstDto> getSecurityQuestions() throws Exception
	{
		try
		{
			LoginDetailsVO loginDetailsVO = loginDetailsVOProviderService.getLoginDetailsVO();	//Get LoginDetailsVO from Security Context holder
			long langId = loginDetailsVO.getLang().getLangId();
			CmnLookupMstDto securityQuestionsLookUp = cmnLookUpDao.getLookUpByLookUpName(securityQuestions, langId);
    		List<CmnLookupMstDto> lstSecurityQuestionsLookUp = cmnLookUpDao.getLookUpByParentLookUpCode(securityQuestionsLookUp.getLookupCode() , langId);
    		return lstSecurityQuestionsLookUp;
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	//Method to Get all Security Questions from database -> End
	
	//Method to Get all Roles from database -> Start
	@Override
	public List<OrgRoleMstDto> getRoles() throws Exception
	{
		try
		{
			return roleDAO.getAllRoles();
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	//Method to Get all Roles from database -> End
	
	//Method to Get all Users from database -> Start
	@Override
	public List<UserDto> getUserList() throws Exception
	{
		try
		{
			return userDAO.getAllUsers();
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	//Method to Get all Users from database -> End
	
	//Method to save user in the database -> Start
	@Override
	public void saveUser(UserDto userDto) throws Exception
	{
		try
		{
			//Convert userDto into OrgUserMst , List<OrgEmpMst> , OrgUserImageMst , List<OrgUserRoleRlt> -> Start
			LoginDetailsVO loginDetailsVO = loginDetailsVOProviderService.getLoginDetailsVO();	//Get LoginDetailsVO from Security Context holder
			OrgUserMst createdUser =  loginDetailsVO.getUser();
			Date currDate = commonUtility.getCurrentDateFromDB();
			boolean activateFlag = userDto.isActivateFlag();
			
			OrgUserMst user = new OrgUserMst();
			user.setUserName(userDto.getUserName());
			user.setPassword(commonUtility.getEncodedPassword(defaultPassword));
			user.setActivateFlag(activateFlag);
			user.setOrgUserMstByCreatedUserId(createdUser);
			user.setCreatedDate(currDate);
			user.setFirstlogin("Y");
			user.setPwdchangedDate(CommonFunctions.addDaysInDate(currDate, 30));
			user.setSecretQueCode(userDto.getSecretQueCode());
			user.setSecretAnswer(commonUtility.getStandardPBEEncryptedString(userDto.getSecretAnswer()));
			user.setInvalidLoginCnt(0);
			user.setOtpEnabled(userDto.isOtpEnabled());
			user.setRegMobileNo(userDto.getRegMobileNo());
			
			List<OrgEmpMst> lstOrgEmpMst = new ArrayList<OrgEmpMst>();
			OrgEmpMst emp = null;
			emp = new OrgEmpMst();	// For English Language
			emp.setEmpGender(userDto.getEmpGender());
			emp.setEmpFname(userDto.getEngEmpFname());
			emp.setEmpMname(userDto.getEngEmpMname());
			emp.setEmpLname(userDto.getEngEmpLname());
			emp.setEmpDob(userDto.getEmpDob());
			emp.setEmpDoj(userDto.getEmpDoj());
			emp.setCmnLanguageMst(commonUtility.getCmnLanguageMstFromLangId(CommonConstants.ENGLISH));
			emp.setOrgUserMstByUserId(user);
			emp.setActivateFlag(activateFlag);
			emp.setEmail(userDto.getEmail());
			emp.setOrgUserMstByCreatedUserId(createdUser);
			emp.setCreatedDate(currDate);
			lstOrgEmpMst.add(emp);
			
			emp = new OrgEmpMst();	// For Hindi Language
			emp.setEmpGender(userDto.getEmpGender());
			emp.setEmpFname(userDto.getHinEmpFname());
			emp.setEmpMname(userDto.getHinEmpMname());
			emp.setEmpLname(userDto.getHinEmpLname());
			emp.setEmpDob(userDto.getEmpDob());
			emp.setEmpDoj(userDto.getEmpDoj());
			emp.setCmnLanguageMst(commonUtility.getCmnLanguageMstFromLangId(CommonConstants.HINDI));
			emp.setOrgUserMstByUserId(user);
			emp.setActivateFlag(activateFlag);
			emp.setEmail(userDto.getEmail());
			emp.setOrgUserMstByCreatedUserId(createdUser);
			emp.setCreatedDate(currDate);
			lstOrgEmpMst.add(emp);
			
			OrgUserImageMst userImage = new OrgUserImageMst();
			userImage.setOrgUserMstByUserId(user);
			userImage.setImage(userDto.getUserImage().getBytes());
			userImage.setActivateFlag(activateFlag);
			userImage.setOrgUserMstByCreatedUserId(createdUser);
			userImage.setCreatedDate(currDate);
			
			List<OrgUserRoleRlt> lstOrgUserRoleRlt = new ArrayList<OrgUserRoleRlt>();
			OrgUserRoleRlt userRole = null;
			for(Long roleCode : userDto.getLstRoleCodes())
			{
				userRole = new OrgUserRoleRlt();
				userRole.setOrgUserMstByUserId(user);
				userRole.setRoleCode(roleCode);
				userRole.setActivateFlag(activateFlag);
				userRole.setOrgUserMstByCreatedUserId(createdUser);
				userRole.setCreatedDate(currDate);
				lstOrgUserRoleRlt.add(userRole);
			}
			
			//Convert userDto into OrgUserMst , List<OrgEmpMst> , OrgUserImageMst , List<OrgUserRoleRlt> -> End
			userDAO.save(user , lstOrgEmpMst , userImage , lstOrgUserRoleRlt);
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	//Method to save role in the database -> End
	
	//Method to Get UserDto from database according to User Id -> Start
	@Override
	public UserDto getUserDtoFromId(long userId) throws Exception
	{
		UserDto userDto = null;
		try
		{
			//Convert OrgUserMst , List<OrgEmpMst> , OrgUserImageMst , List<OrgUserRoleRlt> into userDto -> Start
			OrgUserMst user = userDAO.getUserById(userId);
			List<OrgEmpMst> lstEmployees = userDAO.getEmployeeById(userId);
			OrgUserImageMst userImage = userDAO.getUserImageById(userId);
			List<OrgUserRoleRlt> lstUserRoleRlt  = userDAO.getUserRoleById(userId);
			userDto = new UserDto();
			// Fill userDto form bean -> Start
			userDto.setUserId(userId);
			userDto.setUserName(user.getUserName());
			userDto.setSecretQueCode(user.getSecretQueCode());
			userDto.setSecretAnswer(commonUtility.getStandardPBEDecryptedString(user.getSecretAnswer()));
			userDto.setRegMobileNo(user.getRegMobileNo());
			userDto.setActivateFlag(user.isActivateFlag());
			userDto.setOtpEnabled(user.isOtpEnabled());
			
			// Convert byte array to encoded string to show image on JSP -> Start
			userDto.setEncodedImageString(new String(Base64.encodeBase64(userImage.getImage())));
			// Convert byte array to encoded string to show image on JSP -> End
			
			userDto.setEmpGender(lstEmployees.get(0).getEmpGender());
			userDto.setEmpDob(lstEmployees.get(0).getEmpDob());		
			userDto.setEmpDoj(lstEmployees.get(0).getEmpDoj());
			userDto.setEmail(lstEmployees.get(0).getEmail());
			
			userDto.setEngEmpFname(lstEmployees.get(0).getEmpFname());
			userDto.setEngEmpMname(lstEmployees.get(0).getEmpMname());
			userDto.setEngEmpLname(lstEmployees.get(0).getEmpLname());
			
			userDto.setHinEmpFname(lstEmployees.get(1).getEmpFname());
			userDto.setHinEmpMname(lstEmployees.get(1).getEmpMname());
			userDto.setHinEmpLname(lstEmployees.get(1).getEmpLname());
			
			if(lstUserRoleRlt != null && !lstUserRoleRlt.isEmpty())
			{
				List<Long> lstRoleCodes = new ArrayList<Long>();
				for(OrgUserRoleRlt userRoleRlt : lstUserRoleRlt)
				{
					lstRoleCodes.add(userRoleRlt.getRoleCode());
				}
				userDto.setLstRoleCodes(lstRoleCodes);
			}
			userDto.setLstSecurityQuestionsLookUp(getSecurityQuestions());
			userDto.setLstRoles(getRoles());
			
			// Fill userDto form bean -> End
			//Convert OrgUserMst , List<OrgEmpMst> , OrgUserImageMst , List<OrgUserRoleRlt> into userDto -> End
		}
		catch(Exception e)
		{
			throw e;
		}
		return userDto;
	}
	//Method to Get UserDto from database according to User Id -> End
	
	//Method to Get OrgUserMst from database according to User Name -> Start
	@Override
	public OrgUserMst getUserFromUsername(String username) throws Exception
	{
		OrgUserMst user = null;
		try
		{
			user = userDetailsDao.findByUsername(username);
		}
		catch(Exception e)
		{
			throw e;
		}
		return user;
	}
	
	//Method to Get OrgUserMst from database according to User Name -> Start
	@Override
	public OrgUserMst getUserFromMobileno(String mobileNo) throws Exception
	{
		OrgUserMst user = null;
		try
		{
			user = userDAO.getUserByMobileno(mobileNo);
		}
		catch(Exception e)
		{
			throw e;
		}
		return user;
	}
	
	//Method to update user in the database -> Start
	@Override
	public boolean updateUser(UserDto userDto) throws Exception
	{
		boolean isValuesChanged = false;			// To determine whether user changed the values or not
		try
		{
			//Convert userDto into OrgUserMst , List<OrgEmpMst> , OrgUserImageMst -> Start
			LoginDetailsVO loginDetailsVO = loginDetailsVOProviderService.getLoginDetailsVO();	//Get LoginDetailsVO from Security Context holder
			OrgUserMst updateUser =  loginDetailsVO.getUser();
			Date currDate = commonUtility.getCurrentDateFromDB();
			boolean activateFlag = userDto.isActivateFlag();
			
			OrgUserMst user = new OrgUserMst();
			user.setActivateFlag(activateFlag);
			user.setSecretQueCode(userDto.getSecretQueCode());
			user.setSecretAnswer(commonUtility.getStandardPBEEncryptedString(userDto.getSecretAnswer()));
			user.setOtpEnabled(userDto.isOtpEnabled());
			user.setRegMobileNo(userDto.getRegMobileNo());
			user.setOrgUserMstByUpdatedUserId(updateUser);
			user.setUpdatedDate(currDate);
			
			List<OrgEmpMst> lstOrgEmpMst = new ArrayList<OrgEmpMst>();
			OrgEmpMst emp = null;
			emp = new OrgEmpMst();	// For English Language
			emp.setEmpGender(userDto.getEmpGender());
			emp.setEmpFname(userDto.getEngEmpFname());
			emp.setEmpMname(userDto.getEngEmpMname());
			emp.setEmpLname(userDto.getEngEmpLname());
			emp.setEmpDob(userDto.getEmpDob());
			emp.setEmpDoj(userDto.getEmpDoj());
			emp.setCmnLanguageMst(commonUtility.getCmnLanguageMstFromLangId(CommonConstants.ENGLISH));
			emp.setActivateFlag(activateFlag);
			emp.setEmail(userDto.getEmail());
			emp.setOrgUserMstByUpdatedUserId(updateUser);
			emp.setUpdatedDate(currDate);
			lstOrgEmpMst.add(emp);
			
			emp = new OrgEmpMst();	// For Hindi Language
			emp.setEmpGender(userDto.getEmpGender());
			emp.setEmpFname(userDto.getHinEmpFname());
			emp.setEmpMname(userDto.getHinEmpMname());
			emp.setEmpLname(userDto.getHinEmpLname());
			emp.setEmpDob(userDto.getEmpDob());
			emp.setEmpDoj(userDto.getEmpDoj());
			emp.setCmnLanguageMst(commonUtility.getCmnLanguageMstFromLangId(CommonConstants.HINDI));
			emp.setActivateFlag(activateFlag);
			emp.setEmail(userDto.getEmail());
			emp.setOrgUserMstByUpdatedUserId(updateUser);
			emp.setUpdatedDate(currDate);
			lstOrgEmpMst.add(emp);
			
			OrgUserImageMst userImage = new OrgUserImageMst();
			if(!userDto.getUserImage().isEmpty())
			{
				userImage.setImage(userDto.getUserImage().getBytes());
			}
			userImage.setActivateFlag(activateFlag);
			userImage.setOrgUserMstByUpdatedUserId(updateUser);
			userImage.setUpdatedDate(currDate);
			
			//Convert userDto into OrgUserMst , List<OrgEmpMst> , OrgUserImageMst -> End
			long userId = userDto.getUserId();
			OrgUserMst userPersistent = userDAO.getUserById(userId);
			List<OrgEmpMst> lstEmployeesPersistent = userDAO.getEmployeeById(userId);
			OrgUserImageMst userImagePersistent = userDAO.getUserImageById(userId);
			//Compare user , lstEmployees , userImage and userPersistent , lstEmployeesPersistent , userImagePersistent  whether user changed something or not - > Start
			// Compare User -> Start
			if(	(user.getSecretQueCode() != userPersistent.getSecretQueCode()) || 						// Security Question code
					!commonUtility.getStandardPBEDecryptedString(user.getSecretAnswer()).equals(commonUtility.getStandardPBEDecryptedString(userPersistent.getSecretAnswer())) ||	// Security Answer
						user.isOtpEnabled() != userPersistent.isOtpEnabled()	||							// OTP Enabled
							user.getRegMobileNo() != userPersistent.getRegMobileNo()	||				// Registered Mobile No
								user.isActivateFlag() != userPersistent.isActivateFlag()	||				// Activate flag
			  					isValuesChanged
			  )
			{
				isValuesChanged = true;
			}
			// Compare User -> End
			
			// Compare Employee -> Start
			int lstOrgEmpMstSize = lstOrgEmpMst.size();
			int lstEmployeesPersistentSize = lstEmployeesPersistent.size();
			OrgEmpMst empFromScreen = null;
			OrgEmpMst empFromDb = null;
			for(int loopCountOuter = 0 ; loopCountOuter < lstOrgEmpMstSize ; loopCountOuter++)
			{
				for(int loopCountInner = 0 ; loopCountInner < lstEmployeesPersistentSize ; loopCountInner++)
				{
					if(lstOrgEmpMst.get(loopCountOuter).getCmnLanguageMst().getLangId() == lstEmployeesPersistent.get(loopCountInner).getCmnLanguageMst().getLangId())
					{
						empFromScreen = lstOrgEmpMst.get(loopCountOuter);
						empFromDb = lstEmployeesPersistent.get(loopCountInner);
						if(	!empFromScreen.getEmpGender().equals(empFromDb.getEmpGender()) || 						// Employee Gender
								!empFromScreen.getEmpFname().equals(empFromDb.getEmpFname())	||			// Employee First Name
										!empFromScreen.getEmpLname().equals(empFromDb.getEmpLname())	||				// Employee last name
											empFromScreen.getEmpDob().compareTo(empFromDb.getEmpDob()) != 0	||			//Employee Date of Birth
												empFromScreen.getEmpDoj().compareTo(empFromDb.getEmpDoj()) != 0	||		//Employee Date of Joining
													!empFromScreen.getEmail().equals(empFromDb.getEmail())	||			//Employee Email Id
														empFromScreen.isActivateFlag() != empFromDb.isActivateFlag()	||				// ActivateFlag
						  					isValuesChanged
						  )
						{
							isValuesChanged = true;
						}
					}
				}
			}
			// Compare Employee -> End
			// Compare UserImage -> Start
			if(!userDto.getUserImage().isEmpty())
			{
				isValuesChanged = true;
			}
			// Compare UserImage -> End
			
			if(isValuesChanged)
			{
				String[] ignoreProperties = null;
				//copy properties from (source, target , ignoreProperties)
				ignoreProperties = new String[]{"userId","orgUserMstByCreatedUserId","userName","password","createdDate","firstlogin","pwdchangedDate","invalidLoginCnt","unlockTime","otp","otpValidity"};
				BeanUtils.copyProperties(user , userPersistent , ignoreProperties);
				ignoreProperties = new String[]{"empId","cmnLanguageMst","orgUserMstByUserId","orgUserMstByCreatedUserId","createdDate"};
				BeanUtils.copyProperties(lstOrgEmpMst.get(0) , lstEmployeesPersistent.get(0), ignoreProperties);
				BeanUtils.copyProperties(lstOrgEmpMst.get(1) , lstEmployeesPersistent.get(1), ignoreProperties);
				if(!userDto.getUserImage().isEmpty())		
				{
					ignoreProperties = new String[]{"userImageId","orgUserMstByUserId","orgUserMstByCreatedUserId","encodedImageString","createdDate"};
				}
				else		// if user did not change the image then do not update image
				{
					ignoreProperties = new String[]{"userImageId","image","orgUserMstByUserId","orgUserMstByCreatedUserId","encodedImageString","createdDate"};
				}
				BeanUtils.copyProperties(userImage , userImagePersistent , ignoreProperties);
				userDAO.update(userPersistent , lstEmployeesPersistent , userImagePersistent);
			}
		}
		catch(Exception e)
		{
			throw e;
		}
		return isValuesChanged;
	}
	//Method to update user in the database -> End
}
