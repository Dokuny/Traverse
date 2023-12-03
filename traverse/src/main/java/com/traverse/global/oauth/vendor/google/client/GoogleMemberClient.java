package com.traverse.global.oauth.vendor.google.client;


import com.traverse.command.domain.member.entity.Member;
import com.traverse.global.oauth.component.OAuthMemberClient;
import com.traverse.global.oauth.vendor.enums.OAuthDomain;
import com.traverse.global.oauth.vendor.google.GoogleOAuthProps;
import com.traverse.global.oauth.vendor.google.dto.GoogleToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@RequiredArgsConstructor
@Component
public class GoogleMemberClient implements OAuthMemberClient {

	private final GoogleApiClient googleApiClient;
	private final GoogleOAuthProps props;

	@Override
	public OAuthDomain getOAuthDomain() {
		return OAuthDomain.GOOGLE;
	}

	@Override
	public Member fetch(OAuthDomain oAuthDomain, String authCode) {
		GoogleToken token = googleApiClient.fetchToken(tokenRequestParams(authCode));

		return googleApiClient.fetchMember("Bearer " + token.accessToken()).toDomain();
	}

	private MultiValueMap<String, String> tokenRequestParams(String authCode) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", props.clientId());
		params.add("redirect_uri", props.redirectUri());
		params.add("code", authCode);
		params.add("client_secret", props.clientSecret());
		return params;
	}
}
