package com.gargorg.common.Utils;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.gargorg.Masters.valueObject.CmnLanguageMst;

@Component("commonUtility")
@Transactional
public class CommonUtilityImpl implements CommonUtility
{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private final Logger LOGGER = LoggerFactory.getLogger(CommonUtilityImpl.class);

	@Value("${DBTYPE}") 
	private String dbType;
	@Value("${MYSQL.DBDATE_QUERY}") 
	private String mysqlDBdateQuery;
	@Value("${ORACLE.DBDATE_QUERY}") 
	private String oracleDBdateQuery;

	@Override
	public Date getCurrentDateFromDB()
	{
		Date currentDate = null;
		try
		{
			String dateQuery = null;
			Session session = sessionFactory.getCurrentSession();
	
			if (dbType.trim().equalsIgnoreCase("mysql")) {
				dateQuery = mysqlDBdateQuery;
			} else if (dbType.trim().equalsIgnoreCase("oracle")) {
				dateQuery = oracleDBdateQuery;
			}
			
			SQLQuery sqlQuery = session.createSQLQuery(dateQuery);
			currentDate = (Date) sqlQuery.uniqueResult();
		}
		catch(Exception e)
		{
			LOGGER.error("Error description", e);
		}
		return currentDate;
	}
	
	@Override
	public CmnLanguageMst getCmnLanguageMstFromLangId(long langId) throws Exception
	{
		CmnLanguageMst cmnLanguageMst = null;
		try
		{
			Session session = sessionFactory.getCurrentSession();
			Criteria c  = session.createCriteria(CmnLanguageMst.class);
			c.add(Restrictions.eq("langId", langId));
			List<CmnLanguageMst> lstCmnLanguageMst = c.list();
			cmnLanguageMst = lstCmnLanguageMst.get(0);
		}
		catch(Exception e)
		{
			throw e;
		}
		return cmnLanguageMst;
	}
}