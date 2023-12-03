package com.traverse.global.oauth.exception;

import lombok.Getter;

@Getter
public class OAuthException extends RuntimeException {

	private final OAuthErrorCode errorCode;

	public OAuthException(OAuthErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}
}
