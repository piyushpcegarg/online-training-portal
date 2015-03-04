/**
 * This class include all the functionality related to country , like retrieve , add , update , delete
 */
package com.gargorg.Admin.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import com.gargorg.Admin.valueObject.UserElements;
import com.gargorg.Masters.valueObject.OrgElementMst;
import com.gargorg.common.constant.CommonConstants;

public class PracticeHibernate 
{
	private static SessionFactory factory;
	private static Configuration configuration;
	private static ServiceRegistry serviceRegistry;

	public static void main(String[] args) 
	{
		 try
	      {
	    	 configuration = new Configuration().configure("hibernate.cfg.xml");
	    	 serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
	         factory = configuration.buildSessionFactory(serviceRegistry);
	      }
	      catch (Throwable ex) 
	      { 
	         System.err.println("Failed to create sessionFactory object." + ex);
	         throw new ExceptionInInitializerError(ex); 
	      }
		 PracticeHibernate mc = new PracticeHibernate();
		 mc.method();
		 mc.method3();
	}
	
	public void method()
	{
      Session session = factory.openSession();
      Transaction tx = null;
      try
      {
         tx = session.beginTransaction();
         
         List<OrgElementMst> moduleScreenList = null;
		String hqlQueryString = " select ele " +
				" from OrgRoleMst as role , OrgRoleElementRlt as roleEle , " +
				" OrgElementMst as ele , CmnLanguageMst as lang " +
				" where " + 
				" role.roleCode =  roleEle.roleCode " +
				" and roleEle.elementCode =  ele.elementCode " +
      			" and role.activateFlag =  roleEle.activateFlag " +
      			" and role.activateFlag =  ele.activateFlag " +
      			" and role.activateFlag =  lang.activateFlag " +
      			" and role.activateFlag =  :activateFlag " +
      			" and role.cmnLanguageMst =  lang.langId " +
      			" and ele.cmnLanguageMst =  lang.langId " +
      			" and lang.langShortName like :langShortName " +
      			" and role.roleCode in (:roleCodes )";
		
		Query hqlQuery = session.createQuery(hqlQueryString);
		
		// Get distinct role code from OrgRoleMst -> Start
		hqlQuery.setBoolean("activateFlag", true);
     	hqlQuery.setString("langShortName", "e%");
     	hqlQuery.setParameter("roleCodes", 101L);
		List<Object> resultList = hqlQuery.list();
		System.out.println("-------------------------------"+resultList.size());
		Object resultObject = null;
		OrgElementMst element = null;
		Boolean firstTimeExecution = true;
		List<OrgElementMst> userElementList = null;
		for(Iterator<Object> resultListIterator = resultList.listIterator() ; resultListIterator.hasNext() ; )
		{
			resultObject = resultListIterator.next();
			if(firstTimeExecution)
			{
				userElementList = new ArrayList<OrgElementMst>();
				element = (OrgElementMst)resultObject;
				userElementList.add(element);
				firstTimeExecution = false;
			}
			else
			{
				element = (OrgElementMst)resultObject;
				userElementList.add(element);
			}
		}
		
		moduleScreenList = new ArrayList<OrgElementMst>();
		//moduleScreenList = userElementList;
		//TODO Sort the list according to element order
		// Sorting userElementList according to element order - > Start
		if(userElementList != null && !userElementList.isEmpty())
		{
			moduleScreenList = new ArrayList<OrgElementMst>();
			List<OrgElementMst> moduleList = new ArrayList<OrgElementMst>();
			int count = 0;
			for (int i = 0; i < userElementList.size(); ++i)
			{
				OrgElementMst moduleElementObj = userElementList.get(i);
				if (moduleElementObj.getElementType() != CommonConstants.MODULE)	// Compare module elements
					continue;
				moduleList.add(moduleElementObj);
			}

		    int moduleListSize = moduleList.size();
		    //Collections.sort(moduleList);
		    for (int i = 0; i < moduleListSize ; i++)
		    {
		        moduleScreenList.add(count, moduleList.get(i));
		        count++;
		    }

		    for (int moduleCounter = 0; moduleCounter < moduleListSize ; moduleCounter++)
		    {
		    	OrgElementMst moduleElement = moduleList.get(moduleCounter);
		        List<OrgElementMst> screenElementList = new ArrayList<OrgElementMst>();
	
		        for (int counter = 0; counter < userElementList.size(); counter++)
		        {
		        	OrgElementMst screenElement = userElementList.get(counter);
	
		        	if ((screenElement.getElementType() != CommonConstants.SCREEN) || (screenElement.getElementParentCode() != moduleElement.getElementCode()))		// Compare module elements
		        		continue;
		        	screenElementList.add(screenElement);
		        }
	
		       // Collections.sort(screenElementList);
		        for (int j = 0; j < screenElementList.size(); j++)
		        {
		        	moduleScreenList.add(count, screenElementList.get(j));
		        	count++;
		        }
		    }
		}
		// Sorting userElementList according to element order - > End
		
		method1(moduleScreenList);
         
        tx.commit();
      }
      catch (HibernateException e) 
      {
         if (tx!=null) 
        	 tx.rollback();
         e.printStackTrace(); 
      }
      finally 
      {
         session.close(); 
      }
   }
	
	public void method1(List<OrgElementMst> moduleScreenList)
	{
	    long rootId = -1L;
	    UserElements userElements = null;//new UserElements(moduleScreenList , rootId);
	    
	    Map<String,Object> navigationUserElements = new HashMap<String,Object>();
	    navigationUserElements.put("navigationUserElements",userElements);
	    method2(navigationUserElements);
	}
	
	public void method2(Map<String,Object> resultMap)
	{
		String contextPathUrl = "/PatchGeneration";
		
		UserElements userElements= (UserElements)resultMap.get("navigationUserElements");
        if(userElements!=null)
        {
    		List<Integer> firstLevelModuleIndex = userElements.getRootChildElements();
	 		
	 		StringBuffer finalUlLiString = new StringBuffer();
	 		Integer elementIndex;
			
			finalUlLiString.append("<ul class=\"sf-menu\" id=\"nav\">");
			for(int k=0;k< firstLevelModuleIndex.size();k++)
			{
				elementIndex = firstLevelModuleIndex.get(k);
				finalUlLiString.append(userElements.getUlLiStringForElement(elementIndex , contextPathUrl));
			}										
			finalUlLiString.append("</ul>");
			System.out.println(finalUlLiString);
		}	
   		else
   		{
	       System.out.println("user element object is null  ");
   		}
	}
	
	public void method3()
	{
		Session session = factory.openSession();
		/*Criteria c  = session.createCriteria(OrgRoleMst.class);
		c.setProjection(Projections.max("roleCode"));
		Long elementCode = (Long)c.uniqueResult() + 1L;
		System.out.println(elementCode);
		
		c  = session.createCriteria(OrgEmpMst.class);
		c.add(Restrictions.eq("orgUserMstByUserId.userId", 1L));
		System.out.println((List<OrgEmpMst>)c.list());	
		*/
		Criteria c  = session.createCriteria(OrgElementMst.class);
		c.add(Restrictions.eq("elementName", "worklist").ignoreCase());
		
		OrgElementMst orgElementMst = (OrgElementMst)c.uniqueResult();
		if(orgElementMst != null)
		{
			System.out.println("Element Exist");
		}
		else
		{
			System.out.println("Element Not Exist");
		}
	}

}
