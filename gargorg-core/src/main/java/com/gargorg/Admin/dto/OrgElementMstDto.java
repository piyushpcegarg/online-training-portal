package com.gargorg.Admin.dto;

import java.util.Date;

import com.gargorg.Masters.valueObject.CmnLanguageMst;
import com.gargorg.Masters.valueObject.OrgUserMst;

public class OrgElementMstDto implements java.io.Serializable , Comparable<Object>  {

	private static final long serialVersionUID = 4284037659345629809L;
	private long elementId;
	private long elementDetailId;
	private long elementCode;
	private long elementParentCode;
	private long elementType;
	private String elementUrl;
	private long elementOrder;
	private String elementName;
	private String elementDesc;
	private String elementTooltip;
	private boolean activateFlag;
	private boolean editable;
	private CmnLanguageMst cmnLanguageMst;
	private OrgUserMst orgUserMstByCreatedUserId;
	private Date createdDate;
	
	
	public long getElementId() {
		return elementId;
	}


	public void setElementId(long elementId) {
		this.elementId = elementId;
	}


	public long getElementDetailId() {
		return elementDetailId;
	}


	public void setElementDetailId(long elementDetailId) {
		this.elementDetailId = elementDetailId;
	}


	public long getElementCode() {
		return elementCode;
	}


	public void setElementCode(long elementCode) {
		this.elementCode = elementCode;
	}


	public long getElementParentCode() {
		return elementParentCode;
	}


	public void setElementParentCode(long elementParentCode) {
		this.elementParentCode = elementParentCode;
	}


	public long getElementType() {
		return elementType;
	}


	public void setElementType(long elementType) {
		this.elementType = elementType;
	}


	public String getElementUrl() {
		return elementUrl;
	}


	public void setElementUrl(String elementUrl) {
		this.elementUrl = elementUrl;
	}


	public long getElementOrder() {
		return elementOrder;
	}


	public void setElementOrder(long elementOrder) {
		this.elementOrder = elementOrder;
	}


	public String getElementName() {
		return elementName;
	}


	public void setElementName(String elementName) {
		this.elementName = elementName;
	}


	public String getElementDesc() {
		return elementDesc;
	}


	public void setElementDesc(String elementDesc) {
		this.elementDesc = elementDesc;
	}


	public String getElementTooltip() {
		return elementTooltip;
	}


	public void setElementTooltip(String elementTooltip) {
		this.elementTooltip = elementTooltip;
	}


	public boolean isActivateFlag() {
		return activateFlag;
	}


	public void setActivateFlag(boolean activateFlag) {
		this.activateFlag = activateFlag;
	}


	public boolean isEditable() {
		return editable;
	}


	public void setEditable(boolean editable) {
		this.editable = editable;
	}


	public CmnLanguageMst getCmnLanguageMst() {
		return cmnLanguageMst;
	}


	public void setCmnLanguageMst(CmnLanguageMst cmnLanguageMst) {
		this.cmnLanguageMst = cmnLanguageMst;
	}


	public OrgUserMst getOrgUserMstByCreatedUserId() {
		return orgUserMstByCreatedUserId;
	}


	public void setOrgUserMstByCreatedUserId(OrgUserMst orgUserMstByCreatedUserId) {
		this.orgUserMstByCreatedUserId = orgUserMstByCreatedUserId;
	}


	public Date getCreatedDate() {
		return createdDate;
	}


	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}


	@Override
	public int compareTo(Object o1) 
	{
		int compareValue;
		if (getElementOrder() == ((OrgElementMstDto) o1).getElementOrder()) 
		{
			compareValue = 0;
		} 
		else 
		{
			if (getElementOrder() > ((OrgElementMstDto) o1).getElementOrder()) {
				compareValue = 1;
			} else {
				compareValue = -1;
			}
		}
		return compareValue;
	}
}
