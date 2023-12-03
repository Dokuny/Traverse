package com.traverse.global.oauth.vendor.naver.authcode;


import com.traverse.global.oauth.component.OAuthCodeUrlProvider;
import com.traverse.global.oauth.vendor.enums.OAuthDomain;
import com.traverse.global.oauth.vendor.naver.NaverOAuthProps;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class NaverOAuthCodeUrlProvider implements OAuthCodeUrlProvider {

	private final NaverOAuthProps props;

	@Override
	public OAuthDomain support() {
		return OAuthDomain.NAVER;
	}

	@Override
	public String provide(OAuthDomain oAuthDomain) {
		return UriComponentsBuilder
			.fromUriString("https://nid.naver.com/oauth2.0/authorize")
			.queryParam("response_type", "code")
			.queryParam("client_id", props.clientId())
			.queryParam("redirect_uri", props.redirectUri())
			.queryParam("state", props.state())
			.toUriString();
	}
}
