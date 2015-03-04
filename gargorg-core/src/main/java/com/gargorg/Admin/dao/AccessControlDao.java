/**
 * 
 */
package com.gargorg.Admin.dao;


/**
 * @author piyush
 * 
 */
public interface AccessControlDao {

	public String[] getAllowedRoles(String requestUrl) throws Exception;
}
