/**
 * This Service will contain all the functionality related to training.
 * Like add , update and delete
 */
package com.trainingportal.Transactions.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gargorg.Admin.service.LoginDetailsVOProviderService;
import com.gargorg.common.Utils.CommonUtility;
import com.trainingportal.Transactions.dao.AttendanceDao;


/**
 * @author piyush
 *
 */
@Service
@Transactional
public class AttendanceServiceImpl implements AttendanceService
{
	@Autowired
	private AttendanceDao attendanceDAO;
	@Autowired
	private LoginDetailsVOProviderService loginDetailsVOProviderService;
	@Autowired
	private CommonUtility commonUtility;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AttendanceServiceImpl.class);
	
}
