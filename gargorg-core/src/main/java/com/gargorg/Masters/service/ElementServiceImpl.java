/**
 * This Service will contain all the functionality related to element.
 * Like add , update and delete
 */
package com.gargorg.Masters.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gargorg.Admin.dto.OrgElementMstDto;
import com.gargorg.Admin.service.LoginDetailsVOProviderService;
import com.gargorg.Admin.valueObject.LoginDetailsVO;
import com.gargorg.Masters.dao.ElementDao;
import com.gargorg.Masters.dto.ElementDto;
import com.gargorg.Masters.valueObject.OrgElementDetailsRlt;
import com.gargorg.Masters.valueObject.OrgElementMst;
import com.gargorg.Masters.valueObject.OrgRoleElementRlt;
import com.gargorg.Masters.valueObject.OrgUserMst;
import com.gargorg.common.Utils.CommonUtility;
import com.gargorg.common.constant.CommonConstants;


/**
 * @author piyush
 *
 */
@Service
@Transactional
public class ElementServiceImpl implements ElementService 
{
	@Autowired
	private ElementDao elementDAO;
	@Autowired
	private LoginDetailsVOProviderService loginDetailsVOProviderService;
	@Autowired
	private CommonUtility commonUtility;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ElementServiceImpl.class);
	
	//Method to Get all Elements from database -> Start
	@Override
	public List<OrgElementMstDto> getElementList() throws Exception
	{
		try
		{
			return elementDAO.getAllElements();
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	//Method to Get all Elements from database -> End
	
	//Method to Get ElementDto from database according to Element Code-> Start
	@Override
	public ElementDto getElementDtoFromCode(long elementCode) throws Exception
	{
		ElementDto elementDto = null;
		try
		{
			List<OrgElementMstDto> lstElements = elementDAO.getElementByCode(elementCode);
			elementDto = new ElementDto();
			// Fill elementDto form bean -> Start
			elementDto.setElementCode(elementCode);
			elementDto.setElementParentCode(lstElements.get(0).getElementParentCode());
			elementDto.setActivateFlag(lstElements.get(0).isActivateFlag());
			elementDto.setElementType(lstElements.get(0).getElementType());
			elementDto.setElementUrl(lstElements.get(0).getElementUrl());
			elementDto.setElementOrder(lstElements.get(0).getElementOrder());
			elementDto.setEditable(lstElements.get(0).isEditable());
			
			elementDto.setEngElementName(lstElements.get(0).getElementName());
			elementDto.setEngElementDesc(lstElements.get(0).getElementDesc());
			elementDto.setEngElementToolTip(lstElements.get(0).getElementTooltip());
			
			elementDto.setHinElementName(lstElements.get(1).getElementName());
			elementDto.setHinElementDesc(lstElements.get(1).getElementDesc());
			elementDto.setHinElementToolTip(lstElements.get(1).getElementTooltip());
			
			elementDto.setLstOrgElementMstDto(getElementList());
			
			// Fill elementDto form bean -> End
		}
		catch(Exception e)
		{
			throw e;
		}
		return elementDto;
	}
	//Method to Get ElementDto from database according to Element Code-> End
	
	//Method to save element in the database -> Start
	@Override
	public void saveElement(ElementDto elementDto) throws Exception
	{
		try
		{
			//Convert elementDto into OrgElementMst and List<OrgElementDetailsRlt> -> Start
			List<OrgElementDetailsRlt> lstOrgElementDetailsRlt = new ArrayList<OrgElementDetailsRlt>();
			LoginDetailsVO loginDetailsVO = loginDetailsVOProviderService.getLoginDetailsVO();	//Get LoginDetailsVO from Security Context holder
			OrgElementMst element = null;
			OrgElementDetailsRlt elementDtls = null;
			OrgUserMst createdUser =  loginDetailsVO.getUser();
			Date currDate = commonUtility.getCurrentDateFromDB();
			long elementCode = elementDAO.getNextElementCode();
			boolean activateFlag = elementDto.isActivateFlag();
			boolean isEditable = elementDto.isEditable();
			
			//Create OrgElementMst VO -> Start
			element = new OrgElementMst();
			element.setElementCode(elementCode);
			element.setElementParentCode(elementDto.getElementParentCode());
			element.setElementType(elementDto.getElementType());
			element.setElementUrl(elementDto.getElementUrl());
			element.setElementOrder(elementDto.getElementOrder());
			element.setOrgUserMstByCreatedUserId(createdUser);
			element.setCreatedDate(currDate);
			element.setActivateFlag(activateFlag);
			element.setEditable(isEditable);
			//Create OrgElementMst VO -> End
			
			//Create OrgElementDetailsRlt language specific VO -> Start
			elementDtls = new OrgElementDetailsRlt();	// For English Language
			elementDtls.setElementCode(elementCode);
			elementDtls.setCmnLanguageMst(commonUtility.getCmnLanguageMstFromLangId(CommonConstants.ENGLISH));
			elementDtls.setElementName(elementDto.getEngElementName());
			elementDtls.setElementDesc(elementDto.getEngElementDesc());
			elementDtls.setElementTooltip(elementDto.getEngElementToolTip());
			elementDtls.setOrgUserMstByCreatedUserId(createdUser);
			elementDtls.setCreatedDate(currDate);
			elementDtls.setActivateFlag(activateFlag);
			lstOrgElementDetailsRlt.add(elementDtls);
			
			elementDtls = new OrgElementDetailsRlt();	// For Hindi Language
			elementDtls.setElementCode(elementCode);
			elementDtls.setCmnLanguageMst(commonUtility.getCmnLanguageMstFromLangId(CommonConstants.HINDI));
			elementDtls.setElementName(elementDto.getHinElementName());
			elementDtls.setElementDesc(elementDto.getHinElementDesc());
			elementDtls.setElementTooltip(elementDto.getHinElementToolTip());
			elementDtls.setOrgUserMstByCreatedUserId(createdUser);
			elementDtls.setCreatedDate(currDate);
			elementDtls.setActivateFlag(activateFlag);
			lstOrgElementDetailsRlt.add(elementDtls);
			
			//Convert elementDto into OrgElementMst and List<OrgElementDetailsRlt> -> End
			// Map newly created element with super administrator role -> Start
			OrgRoleElementRlt orgRoleElementRlt = new OrgRoleElementRlt();
			orgRoleElementRlt.setRoleCode(101L);		// Super admin role for proprietor
			orgRoleElementRlt.setElementCode(elementCode);
			orgRoleElementRlt.setOrgUserMstByCreatedUserId(createdUser);
			orgRoleElementRlt.setCreatedDate(currDate);
			orgRoleElementRlt.setActivateFlag(activateFlag);
			// Map newly created element with super administrator role -> End
			elementDAO.save(element , lstOrgElementDetailsRlt , orgRoleElementRlt);
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	//Method to save element in the database -> End

	//Method to update element in the database -> Start
	@Override
	public boolean updateElement(ElementDto elementDto) throws Exception
	{
		boolean isValuesChanged = false;			// To determine whether user changed the values or not
		try
		{
			//Convert elementDto into OrgElementMst and List<OrgElementDetailsRlt> -> Start
			List<OrgElementDetailsRlt> lstOrgElementDetailsRlt = new ArrayList<OrgElementDetailsRlt>();
			LoginDetailsVO loginDetailsVO = loginDetailsVOProviderService.getLoginDetailsVO();	//Get LoginDetailsVO from Security Context holder
			OrgElementMst element = null;
			OrgElementDetailsRlt elementDtls = null;
			long elementCode = elementDto.getElementCode();
			OrgUserMst updatedUser =  loginDetailsVO.getUser();
			Date currDate = commonUtility.getCurrentDateFromDB();
			boolean isEditable = elementDto.isEditable();
			
			//Create OrgElementMst VO -> Start
			element = new OrgElementMst();
			element.setElementCode(elementCode);
			element.setElementParentCode(elementDto.getElementParentCode());
			element.setElementType(elementDto.getElementType());
			element.setElementUrl(elementDto.getElementUrl());
			element.setElementOrder(elementDto.getElementOrder());
			element.setOrgUserMstByUpdatedUserId(updatedUser);
			element.setUpdatedDate(currDate);
			element.setActivateFlag(elementDto.isActivateFlag());
			element.setEditable(isEditable);
			//Create OrgElementMst VO -> End
			//Create OrgElementDetailsRlt language specific VO -> Start
			
			elementDtls = new OrgElementDetailsRlt();	// For English Language
			elementDtls.setElementCode(elementCode);
			elementDtls.setCmnLanguageMst(commonUtility.getCmnLanguageMstFromLangId(CommonConstants.ENGLISH));
			elementDtls.setElementName(elementDto.getEngElementName());
			elementDtls.setElementDesc(elementDto.getEngElementDesc());
			elementDtls.setElementTooltip(elementDto.getEngElementToolTip());
			elementDtls.setOrgUserMstByUpdatedUserId(updatedUser);
			elementDtls.setUpdatedDate(currDate);
			elementDtls.setActivateFlag(elementDto.isActivateFlag());
			lstOrgElementDetailsRlt.add(elementDtls);
			
			elementDtls = new OrgElementDetailsRlt();	// For Hindi Language
			elementDtls.setElementCode(elementCode);
			elementDtls.setCmnLanguageMst(commonUtility.getCmnLanguageMstFromLangId(CommonConstants.HINDI));
			elementDtls.setElementName(elementDto.getHinElementName());
			elementDtls.setElementDesc(elementDto.getHinElementDesc());
			elementDtls.setElementTooltip(elementDto.getHinElementToolTip());
			elementDtls.setOrgUserMstByUpdatedUserId(updatedUser);
			elementDtls.setUpdatedDate(currDate);
			elementDtls.setActivateFlag(elementDto.isActivateFlag());
			lstOrgElementDetailsRlt.add(elementDtls);
			//Create OrgElementDetailsRlt language specific VO -> End
			
			//Convert elementDto into OrgElementMst and List<OrgElementDetailsRlt> -> End
			
			List<OrgElementMstDto> lstOrgElementMstPersistent = elementDAO.getElementByCode(elementCode);
			//Compare OrgElementMst and OrgElementMstPersistent whether user changed something or not - > Start
			OrgElementMstDto elementFromDb = null;
			OrgElementMst elementFromScreen = element;
			elementFromDb = lstOrgElementMstPersistent.get(0);
		
			if(	elementFromScreen.getElementType() != elementFromDb.getElementType()	||			// ElementType
					!elementFromScreen.getElementUrl().equals(elementFromDb.getElementUrl())	||	// ElementUrl
						elementFromScreen.getElementOrder() != elementFromDb.getElementOrder()	||	// ElementOrder
								elementFromScreen.getElementParentCode() != elementFromDb.getElementParentCode()	||	// ElementParentCode
									elementFromScreen.isActivateFlag() != elementFromDb.isActivateFlag()	||				// ActivateFlag
										elementFromScreen.isEditable() != elementFromDb.isEditable()	||				// editable
				isValuesChanged
			  )
			{
				isValuesChanged = true;
			}
			//Compare OrgElementMst and OrgElementMstPersistent whether user changed something or not - > End
			//Compare lstOrgElementDetailsRlt and lstOrgElementMstPersistent whether user changed something or not - > Start
			if(!isValuesChanged)		// Compare only if user did not change anything in OrgElementMst fields
			{
				int lstOrgElementMstSize = lstOrgElementDetailsRlt.size();
				int lstOrgElementMstPersistentSize = lstOrgElementMstPersistent.size();
				OrgElementDetailsRlt elementDtlsFromScreen = null;
				for(int loopCountOuter = 0 ; loopCountOuter < lstOrgElementMstSize ; loopCountOuter++)
				{
					for(int loopCountInner = 0 ; loopCountInner < lstOrgElementMstPersistentSize ; loopCountInner++)
					{
						if(lstOrgElementDetailsRlt.get(loopCountOuter).getCmnLanguageMst().getLangId() == lstOrgElementMstPersistent.get(loopCountInner).getCmnLanguageMst().getLangId())
						{
							elementDtlsFromScreen = lstOrgElementDetailsRlt.get(loopCountOuter);
							elementFromDb = lstOrgElementMstPersistent.get(loopCountInner);
							if(	!elementDtlsFromScreen.getElementName().equals(elementFromDb.getElementName()) || 						// ElementName
										!elementDtlsFromScreen.getElementDesc().equals(elementFromDb.getElementDesc())	||				// ElementDesc
											!elementDtlsFromScreen.getElementTooltip().equals(elementFromDb.getElementTooltip())	||	// ElementToolTip
																	elementDtlsFromScreen.isActivateFlag() != elementFromDb.isActivateFlag()	||				// ActivateFlag
							  					isValuesChanged
							  )
							{
								isValuesChanged = true;
							}
						}
					}
				}
			}
			//Compare lstOrgElementDetailsRlt and lstOrgElementMstPersistent whether user changed something or not - > End
			if(isValuesChanged)
			{
				OrgUserMst orgUserMstByCreatedUserId = lstOrgElementMstPersistent.get(0).getOrgUserMstByCreatedUserId();
				Date createdDate = lstOrgElementMstPersistent.get(0).getCreatedDate();
				// Copy other properties fetched from database to make these objects persistent - > STart
				element.setElementId(lstOrgElementMstPersistent.get(0).getElementId());
				element.setOrgUserMstByCreatedUserId(orgUserMstByCreatedUserId);
				element.setCreatedDate(createdDate);
				
				lstOrgElementDetailsRlt.get(0).setElementDetailId(lstOrgElementMstPersistent.get(0).getElementDetailId());
				lstOrgElementDetailsRlt.get(0).setOrgUserMstByCreatedUserId(orgUserMstByCreatedUserId);
				lstOrgElementDetailsRlt.get(0).setCreatedDate(createdDate);
				
				lstOrgElementDetailsRlt.get(1).setElementDetailId(lstOrgElementMstPersistent.get(1).getElementDetailId());
				lstOrgElementDetailsRlt.get(1).setOrgUserMstByCreatedUserId(orgUserMstByCreatedUserId);
				lstOrgElementDetailsRlt.get(1).setCreatedDate(createdDate);
				// Copy other properties fetched from database to make these objects persistent - > End
				
				elementDAO.update(element , lstOrgElementDetailsRlt);
			}
		}
		catch(Exception e)
		{
			throw e;
		}
		return isValuesChanged;
	}
	//Method to update element in the database -> End
	
	//Method to check Element availability from database -> Start
	@Override
	public boolean isElementExist(ElementDto elementDto) throws Exception
	{
		boolean isElementExist = false;
		try
		{
			// Check element Name in English -> Start
			if(elementDAO.getElementByName(elementDto.getEngElementName()) != null)
			{
				isElementExist = true;
			}
			// Check element Name in English -> End
			// Check element Name in Hindi -> Start
			if(!isElementExist && elementDAO.getElementByName(elementDto.getHinElementName()) != null)
			{
				isElementExist = true;
			}
			// Check element Name in Hindi -> End
		}
		catch(Exception e)
		{
			throw e;
		}
		return isElementExist;
	}
	//Method to check Element availability from database -> End
}
