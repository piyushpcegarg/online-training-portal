/**
 * 
 */
package com.gargorg.Admin.dao;

import java.util.List;

/**
 * @author piyush
 * 
 */
public interface AccessControlDao {

	public List<String> getAllowedRoles(String requestUrl) throws Exception;
}
