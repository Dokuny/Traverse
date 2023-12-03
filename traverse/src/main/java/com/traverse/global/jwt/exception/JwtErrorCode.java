package com.traverse.global.jwt.exception;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum JwtErrorCode {
	EXPIRED_TOKEN("토큰이 만료되었습니다.", UNAUTHORIZED),
	INVALID_TOKEN("사용할 수 없는 토큰 입니다.", UNAUTHORIZED);


	private final String message;
	private HttpStatus httpStatus;


}
