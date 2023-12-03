package com.traverse.global.oauth.vendor.kakao.dto;


import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(SnakeCaseStrategy.class)
public record KakaoToken(
	String accessToken,
	String refreshToken,
	String tokenType,
	String expiresIn,
	String error,
	String errorDescription
) {

}
