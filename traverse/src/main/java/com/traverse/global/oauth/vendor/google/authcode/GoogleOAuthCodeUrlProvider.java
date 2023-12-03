package com.traverse.global.oauth.vendor.google.authcode;


import com.traverse.global.oauth.component.OAuthCodeUrlProvider;
import com.traverse.global.oauth.vendor.enums.OAuthDomain;
import com.traverse.global.oauth.vendor.google.GoogleOAuthProps;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class GoogleOAuthCodeUrlProvider implements OAuthCodeUrlProvider {

	private final GoogleOAuthProps props;

	@Override
	public OAuthDomain support() {
		return OAuthDomain.GOOGLE;
	}

	@Override
	public String provide(OAuthDomain oAuthDomain) {
		return UriComponentsBuilder
			.fromUriString("https://accounts.google.com/o/oauth2/v2/auth")
			.queryParam("response_type","code")
			.queryParam("client_id",props.clientId())
			.queryParam("redirect_uri",props.redirectUri())
			.queryParam("scope",String.join(" ",props.scope()))
			.toUriString();
	}
}
