/**
 * 
 */
package com.gargorg.Masters.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gargorg.Admin.dto.OrgElementMstDto;
import com.gargorg.Masters.dto.ElementDto;


/**
 * @author piyush
 *
 */
@Service
public interface ElementService 
{
	public List<OrgElementMstDto> getElementList() throws Exception;
	public ElementDto getElementDtoFromCode(long elementCode) throws Exception;
	public void saveElement(ElementDto elementDto) throws Exception;
	public boolean updateElement(ElementDto elementDto) throws Exception;
	public boolean isElementExist(ElementDto elementDto) throws Exception;
}
