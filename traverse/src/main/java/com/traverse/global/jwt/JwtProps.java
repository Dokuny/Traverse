package com.traverse.global.jwt;

import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public record JwtProps(
	String accessKey,
	Duration accessExpiration,
	String refreshKey,
	Duration refreshExpiration
) {
}