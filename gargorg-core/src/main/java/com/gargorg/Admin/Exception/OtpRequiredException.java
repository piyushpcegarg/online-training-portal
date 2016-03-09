package com.gargorg.Admin.Exception;

import org.springframework.security.core.AuthenticationException;

@SuppressWarnings("serial")
public class OtpRequiredException extends AuthenticationException {
	public OtpRequiredException(String msg) {
		super(msg);
	}

	public OtpRequiredException(String msg, Throwable t) {
		super(msg, t);
	}
}