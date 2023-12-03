package com.traverse.global.oauth.vendor.kakao.authcode;


import com.traverse.global.oauth.component.OAuthCodeUrlProvider;
import com.traverse.global.oauth.vendor.enums.OAuthDomain;
import com.traverse.global.oauth.vendor.kakao.KakaoOAuthProps;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class KakaoOAuthCodeUrlProvider implements OAuthCodeUrlProvider {

	private final KakaoOAuthProps props;

	@Override
	public OAuthDomain support() {
		return OAuthDomain.KAKAO;
	}

	@Override
	public String provide(OAuthDomain oAuthServerType) {
		return UriComponentsBuilder
			.fromUriString("https://kauth.kakao.com/oauth/authorize")
			.queryParam("response_type","code")
			.queryParam("client_id",props.clientId())
			.queryParam("redirect_uri",props.redirectUri())
			.queryParam("scope",String.join(",",props.scope()))
			.toUriString();
	}
}
