package com.traverse.global.oauth.component;

import com.traverse.global.oauth.vendor.enums.OAuthDomain;

public interface OAuthCodeUrlProvider {

	OAuthDomain support();

	String provide(OAuthDomain oAuthDomain);

}
