package com.gargorg.common.Utils;

import java.util.Date;

import com.gargorg.Masters.valueObject.CmnLanguageMst;

public interface CommonUtility 
{
	public Date getCurrentDateFromDB();
	public CmnLanguageMst getCmnLanguageMstFromLangId(long langId) throws Exception;
	public String getStandardPBEEncryptedString(String clearTextString);
	public String getStandardPBEDecryptedString(String clearTextString);
	public String getEncodedPassword(String password);
	public boolean compareEncodedPassword(String rawPassword ,  String encodedPassword);
}