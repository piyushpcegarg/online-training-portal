package com.gargorg.Masters.dto;

import java.io.Serializable;
import java.util.List;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.gargorg.Admin.dto.OrgElementMstDto;

public class ElementDto implements Serializable {
	

	private static final long serialVersionUID = 231328389161937082L;
	
	private long elementCode;
	private long elementParentCode;
	@NotBlank(message="{ElementDto.engElementName.NotBlank}")
	@Length(min = 4, max = 200 , message = "{ElementDto.engElementName.Length}")
	private String engElementName;
	@NotBlank(message="{ElementDto.engElementDesc.NotBlank}")
	@Length(min = 4, max = 200, message = "{ElementDto.engElementDesc.Length}")
	private String engElementDesc;
	@NotBlank(message="{ElementDto.engElementToolTip.NotBlank}")
	@Length(min = 4, max = 200 , message = "{ElementDto.engElementToolTip.Length}")
	private String engElementToolTip;
	@NotBlank(message="{ElementDto.hinElementName.NotBlank}")
	@Length(min = 4, max = 200 , message = "{ElementDto.hinElementName.Length}")
	private String hinElementName;
	@NotBlank(message="{ElementDto.hinElementDesc.NotBlank}")
	@Length(min = 4, max = 200 , message = "{ElementDto.hinElementDesc.Length}")
	private String hinElementDesc;
	@NotBlank(message="{ElementDto.hinElementToolTip.NotBlank}")
	@Length(min = 4, max = 200 , message = "{ElementDto.hinElementToolTip.Length}")
	private String hinElementToolTip;
	private long elementType;
	@NotBlank(message="{ElementDto.elementUrl.NotBlank}")
	@Length(min = 2, max = 250 , message = "{ElementDto.elementUrl.Length}")
	private String elementUrl;
	private long elementOrder;
	private boolean activateFlag;
	private boolean editable;
	@NotEmpty(message = "{ElementDto.lstOrgElementMst.NotEmpty}")
	private List<OrgElementMstDto> lstOrgElementMstDto;
	
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
	public String getEngElementName() {
		return engElementName;
	}
	public void setEngElementName(String engElementName) {
		this.engElementName = engElementName;
	}
	public String getEngElementDesc() {
		return engElementDesc;
	}
	public void setEngElementDesc(String engElementDesc) {
		this.engElementDesc = engElementDesc;
	}
	public String getEngElementToolTip() {
		return engElementToolTip;
	}
	public void setEngElementToolTip(String engElementToolTip) {
		this.engElementToolTip = engElementToolTip;
	}
	public String getHinElementName() {
		return hinElementName;
	}
	public void setHinElementName(String hinElementName) {
		this.hinElementName = hinElementName;
	}
	public String getHinElementDesc() {
		return hinElementDesc;
	}
	public void setHinElementDesc(String hinElementDesc) {
		this.hinElementDesc = hinElementDesc;
	}
	public String getHinElementToolTip() {
		return hinElementToolTip;
	}
	public void setHinElementToolTip(String hinElementToolTip) {
		this.hinElementToolTip = hinElementToolTip;
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
	public boolean isEditable() {
		return editable;
	}
	public void setEditable(boolean editable) {
		this.editable = editable;
	}
	public List<OrgElementMstDto> getLstOrgElementMstDto() {
		return lstOrgElementMstDto;
	}
	public void setLstOrgElementMstDto(List<OrgElementMstDto> lstOrgElementMstDto) {
		this.lstOrgElementMstDto = lstOrgElementMstDto;
	}
}
