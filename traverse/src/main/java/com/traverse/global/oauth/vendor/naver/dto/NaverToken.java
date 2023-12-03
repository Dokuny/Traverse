package com.traverse.global.oauth.vendor.naver.dto;


import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(SnakeCaseStrategy.class)
public record NaverToken(
	String tokenType,
	String accessToken,
	String idToken,
	String expiresIn,
	String refreshToken,
	String refreshTokenExpiresIn,
	String scope
) {

}
