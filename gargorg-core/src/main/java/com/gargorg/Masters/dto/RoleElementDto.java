package com.gargorg.Masters.dto;

import java.io.Serializable;

public class RoleElementDto implements Serializable {
	
	private static final long serialVersionUID = -8745172479121843744L;
	
	private long elementCode;
	private String elementName;
	private String elementDesc;
	private long elementType;
	private String elementUrl;
	private long elementOrder;
	private boolean activateFlag;
	
	public long getElementCode() {
		return elementCode;
	}
	public void setElementCode(long elementCode) {
		this.elementCode = elementCode;
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
	public boolean isActivateFlag() {
		return activateFlag;
	}
	public void setActivateFlag(boolean activateFlag) {
		this.activateFlag = activateFlag;
	}
}
