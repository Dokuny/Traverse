package com.traverse.global.oauth.component;

import com.traverse.command.domain.member.entity.Member;
import com.traverse.global.oauth.vendor.enums.OAuthDomain;


public interface OAuthMemberClient {

	OAuthDomain getOAuthDomain();

	Member fetch(OAuthDomain oAuthServerType, String authCode);

}
