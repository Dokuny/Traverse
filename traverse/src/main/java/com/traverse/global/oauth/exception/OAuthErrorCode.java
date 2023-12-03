package com.traverse.global.oauth.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OAuthErrorCode {
	NOT_SUPPORT_VENDOR("지원하지 않는 벤더입니다.");

	private final String message;


}
