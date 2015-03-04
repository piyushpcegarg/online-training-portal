package com.trainingportal.Masters.dto;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class LocationDto implements Serializable {
	
	private static final long serialVersionUID = 2177671729960906009L;
	
	private long locationCode;
	@NotBlank(message="{LocationDto.locationName.NotBlank}")
	@Length(min = 10, max = 100 , message = "{LocationDto.locationName.Length}")
	private String locationName;
	
	public long getLocationCode() {
		return locationCode;
	}
	public void setLocationCode(long locationCode) {
		this.locationCode = locationCode;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public boolean isActivateFlag() {
		return activateFlag;
	}
	public void setActivateFlag(boolean activateFlag) {
		this.activateFlag = activateFlag;
	}
	private boolean activateFlag;
}
