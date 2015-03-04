package com.gargorg.Admin.Exception;

import org.springframework.security.core.AuthenticationException;

public class OtpRequiredException extends AuthenticationException {
	public OtpRequiredException(String msg) {
		super(msg);
	}

	@Deprecated
	public OtpRequiredException(String msg, Object extraInformation) {
		super(msg, extraInformation);
	}

	public OtpRequiredException(String msg, Throwable t) {
		super(msg, t);
	}
}