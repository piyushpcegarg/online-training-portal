package com.gargorg.Admin.Exception;

import org.springframework.security.core.AuthenticationException;

public class InvalidOtpException extends AuthenticationException {
	public InvalidOtpException(String msg) {
		super(msg);
	}

	@Deprecated
	public InvalidOtpException(String msg, Object extraInformation) {
		super(msg, extraInformation);
	}

	public InvalidOtpException(String msg, Throwable t) {
		super(msg, t);
	}
}