package com.traverse.global.oauth.vendor.naver.client;


import com.traverse.command.domain.member.entity.Member;
import com.traverse.global.oauth.component.OAuthMemberClient;
import com.traverse.global.oauth.vendor.enums.OAuthDomain;
import com.traverse.global.oauth.vendor.naver.NaverOAuthProps;
import com.traverse.global.oauth.vendor.naver.dto.NaverToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@RequiredArgsConstructor
@Component
public class NaverMemberClient implements OAuthMemberClient {

	private final NaverApiClient naverApiClient;
	private final NaverOAuthProps props;

	@Override
	public OAuthDomain getOAuthDomain() {
		return OAuthDomain.NAVER;
	}

	@Override
	public Member fetch(OAuthDomain oAuthDomain, String authCode) {
		NaverToken token = naverApiClient.fetchToken(tokenRequestParams(authCode));

		return naverApiClient.fetchMember("Bearer " + token.accessToken()).toDomain();
	}

	private MultiValueMap<String, String> tokenRequestParams(String authCode) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", props.clientId());
		params.add("code", authCode);
		params.add("state", props.state());
		params.add("client_secret", props.clientSecret());
		return params;
	}
}
