package com.gargorg.Admin.valueObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gargorg.Admin.dto.OrgElementMstDto;
import com.gargorg.common.constant.CommonConstants;

public class UserElements
{
  private List<OrgElementMstDto> ModuleScreenList;
  private Map<Long,List<Integer>> getChildIndexByParentCode;
  private long rootId = 0L;

  private static final Logger LOGGER = LoggerFactory.getLogger(UserElements.class);

  public UserElements(List<OrgElementMstDto> moduleScreenList , long rootId)
  {
    this.ModuleScreenList = moduleScreenList;
    this.rootId = rootId;
    try
    {
      OrgElementMstDto userElement = null;

      this.getChildIndexByParentCode = new HashMap<Long,List<Integer>>();

      userElement = null;

      List<Integer> screenIndexList = null;
      List<Integer> moduleIndexList = new ArrayList<Integer>();

      int moduleElementIndex = 0;
      int totalMod = this.ModuleScreenList.size();
      for (int moduleIndex = 0; moduleIndex < totalMod; ++moduleIndex)
      {
        userElement = this.ModuleScreenList.get(moduleIndex);
        if (userElement.getElementType() == CommonConstants.MODULE)  // Compare module elements
        {
          long moduleParentCode = userElement.getElementParentCode();
          if (moduleParentCode == rootId)
          {
            moduleIndexList.add(Integer.valueOf(moduleElementIndex));
          }
        }
        ++moduleElementIndex;
      }
      this.getChildIndexByParentCode.put(Long.valueOf(rootId), moduleIndexList);
      int totalModScr = this.ModuleScreenList.size();
      for (int moduleIndex = 0; moduleIndex < totalModScr; ++moduleIndex)
      {
        userElement = this.ModuleScreenList.get(moduleIndex);
        if (userElement.getElementType() != CommonConstants.MODULE)	// Compare module elements
          continue;
        long moduleElementCode = userElement.getElementCode();

        OrgElementMstDto screenUserElement = null;
        int screenIndex = 0;
        screenIndexList = new ArrayList<Integer>();
        for (int j = 0; j < totalModScr; ++j)
        {
          screenUserElement = this.ModuleScreenList.get(j);
          if (screenUserElement.getElementParentCode() == moduleElementCode)
          {
            screenIndexList.add(Integer.valueOf(screenIndex));
          }
          ++screenIndex;
        }
        this.getChildIndexByParentCode.put(Long.valueOf(moduleElementCode), screenIndexList);
      }
    }
    catch (Exception e)
    {
    	LOGGER.error("Error in class UserElements constructor ", e);
    }
  }

  public List<Integer> getChildElements(long elementCode)
  {
    return (this.getChildIndexByParentCode.get(Long.valueOf(elementCode)));
  }

  public List<Integer> getRootChildElements()
  {
    return (this.getChildIndexByParentCode.get(Long.valueOf(this.rootId)));
  }

  public StringBuffer getUlLiStringForElement(int elementIndex , String contextPathUrl)
  {
    StringBuffer ulLiString = new StringBuffer();

    OrgElementMstDto elementObj = this.ModuleScreenList.get(elementIndex);
    getChildUlLiString(elementObj, ulLiString , contextPathUrl);
    return ulLiString;
  }

  private StringBuffer getChildUlLiString(OrgElementMstDto elementObj, StringBuffer ulLiString , String contextPathUrl)
  {
    List<Integer> childIndexList = new ArrayList<Integer>();
    List<Integer> grandChildElementIndexList = new ArrayList<Integer>();

    String elementName = elementObj.getElementName();
    childIndexList = getChildElements(elementObj.getElementCode());

    if ((childIndexList == null) || (childIndexList.isEmpty()))
    {
    	String elementUrlLink = elementObj.getElementUrl();
    	ulLiString.append("<li><a href=\"" + contextPathUrl + elementUrlLink + "\">" + elementName + "</a></li>");
    }
    else
    {
      OrgElementMstDto elementToCheck = this.ModuleScreenList.get(childIndexList.get(0).intValue());

      grandChildElementIndexList = getChildElements(elementToCheck.getElementCode());
      
      if (grandChildElementIndexList == null)
      {
          ulLiString.append("<li><a href=\"" + contextPathUrl + elementToCheck.getElementUrl() + "\"> " + elementName + "</a><ul>");
      }
      else 
      {
        ulLiString.append("<li><a href=\"#\" >" + elementName + "</a><ul>");
      }

      int childIndexSize = childIndexList.size();
      for (int iter = 0; iter < childIndexSize; ++iter)
      {
    	  elementObj = this.ModuleScreenList.get(childIndexList.get(iter).intValue());
    	  getChildUlLiString(elementObj, ulLiString , contextPathUrl);
      }
      ulLiString.append("</ul></li>");
    }
    return null;
  }
}