/**
 * 
 */
package com.gargorg.Admin.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gargorg.Admin.dto.CmnLookupMstDto;
import com.gargorg.Admin.service.LoginDetailsVOProviderService;
import com.gargorg.Admin.valueObject.LoginDetailsVO;
import com.gargorg.Masters.valueObject.CmnLookupDetailsRlt;
import com.gargorg.Masters.valueObject.CmnLookupMst;
import com.gargorg.Masters.valueObject.OrgUserMst;
import com.gargorg.common.Utils.CommonUtility;

/**
 * @author piyush
 *
 */
@Repository
public class SettingDaoImpl implements SettingDao 
{
	private static final Logger LOGGER = LoggerFactory.getLogger(SettingDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private CommonUtility commonUtility;
	@Autowired
	private LoginDetailsVOProviderService loginDetailsVOProviderService;
	
	Session session = null;
	Query hqlQuery = null;
	String hqlQueryString = null;
	
	@Override
	public void updateSecuritySettings(List<CmnLookupMstDto> lstCmnLookupMst) throws Exception
	{
		try
		{
			session = sessionFactory.getCurrentSession();
			// create list of lookup codes -> Start
			List<Long> lstlookupCode = new ArrayList<Long>();
			for(CmnLookupMstDto cmnLookupMstDto : lstCmnLookupMst)
			{
				lstlookupCode.add(cmnLookupMstDto.getLookupCode());
			}
			// create list of lookup codes -> End
			hqlQueryString = " select lookUp , lookUpDtls " +
					" from CmnLookupMst as lookUp , CmnLookupDetailsRlt as lookUpDtls " +
					" where lookUp.lookupCode = lookUpDtls.lookupCode " +
	     			" and lookUp.activateFlag = lookUpDtls.activateFlag " +
	     			" and lookUp.lookupCode in (:lstlookupCode) " +
					" order by lookUp.lookupCode ";
			
			session = sessionFactory.getCurrentSession();
			hqlQuery = session.createQuery(hqlQueryString);
			hqlQuery.setParameterList("lstlookupCode", lstlookupCode);
			List<Object[]> lstCmnLookupMstPersistent = hqlQuery.list();
			
			if(lstCmnLookupMstPersistent != null && !lstCmnLookupMstPersistent.isEmpty())
			{
				LoginDetailsVO loginDetailsVO = loginDetailsVOProviderService.getLoginDetailsVO();	//Get LoginDetailsVO from Security Context holder
				OrgUserMst updatedUser = loginDetailsVO.getUser();
				Date currDate = commonUtility.getCurrentDateFromDB();
				
				int lstCmnLookupMstSize = lstCmnLookupMst.size();
				int lstCmnLookupMstPersistentSize = lstCmnLookupMstPersistent.size();
				Object[] objArray = null;
				CmnLookupMst cmnLookupMst = null;
				CmnLookupDetailsRlt cmnLookupDetailsRlt = null;
				for(int loopCountOuter = 0 ; loopCountOuter < lstCmnLookupMstSize ; loopCountOuter++)
				{
					for(int loopCountInner = 0 ; loopCountInner < lstCmnLookupMstPersistentSize ; loopCountInner++)
					{
						objArray = lstCmnLookupMstPersistent.get(loopCountInner);
						cmnLookupMst =  (CmnLookupMst)objArray[0];
						cmnLookupDetailsRlt	= (CmnLookupDetailsRlt)objArray[1];	
						
						if(lstCmnLookupMst.get(loopCountOuter).getLookupCode() == cmnLookupMst.getLookupCode())
						{
							cmnLookupMst.setActivateFlag(lstCmnLookupMst.get(loopCountOuter).isActivateFlag());
							cmnLookupMst.setOrgUserMstByUpdatedUserId(updatedUser);
							cmnLookupMst.setUpdatedDate(currDate);
							
							cmnLookupDetailsRlt.setActivateFlag(lstCmnLookupMst.get(loopCountOuter).isActivateFlag());
							cmnLookupDetailsRlt.setOrgUserMstByUpdatedUserId(updatedUser);
							cmnLookupDetailsRlt.setUpdatedDate(currDate);
							
							session.update(cmnLookupMst);
							session.update(cmnLookupDetailsRlt);
						}
					}
				}
			}
		}
		catch(Exception e)
		{
			throw e;
		}
	}
}