package com.traverse.global.oauth.vendor.enums;

public enum OAuthDomain {
	KAKAO, NAVER, GOOGLE;

	public static OAuthDomain fromName(String providerName) {
		return OAuthDomain.valueOf(providerName.toUpperCase());
	}

}
