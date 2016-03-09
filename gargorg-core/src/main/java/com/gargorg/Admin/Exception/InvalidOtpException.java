package com.gargorg.Admin.Exception;

import org.springframework.security.core.AuthenticationException;

@SuppressWarnings("serial")
public class InvalidOtpException extends AuthenticationException {
	public InvalidOtpException(String msg) {
		super(msg);
	}

	public InvalidOtpException(String msg, Throwable t) {
		super(msg, t);
	}
}