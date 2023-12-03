package com.traverse.global.oauth.vendor.google.dto;


import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(SnakeCaseStrategy.class)
public record GoogleToken(
	String accessToken,
	String expiresIn,
	String refreshToken,
	String scope,
	String tokenType
) {

}
