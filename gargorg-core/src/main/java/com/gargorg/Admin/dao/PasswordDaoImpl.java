/**
 * 
 */
package com.gargorg.Admin.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gargorg.Masters.valueObject.CmnLanguageMst;

/**
 * @author piyush
 *
 */
@Repository
public class PasswordDaoImpl implements PasswordDao 
{
	private static final Logger LOGGER = LoggerFactory.getLogger(PasswordDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	Session session = null;
	Query hqlQuery = null;
	String hqlQueryString = null;
	
	@Override
	public long getLanguageByLocale(String locale) throws Exception
	{
		session = sessionFactory.getCurrentSession();
		Criteria c  = session.createCriteria(CmnLanguageMst.class);
		c.add(Restrictions.eq("langShortName", locale));
		c.add(Restrictions.eq("activateFlag", true));
		List<CmnLanguageMst> lstCmnLanguageMst = c.list();
		return lstCmnLanguageMst.get(0).getLangId();
	}
}