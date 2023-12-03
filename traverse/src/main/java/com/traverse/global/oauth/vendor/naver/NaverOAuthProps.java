package com.traverse.global.oauth.vendor.naver;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "oauth.naver")
public record NaverOAuthProps(String redirectUri,
							  String clientId,
							  String clientSecret,
							  String state
) {
}
