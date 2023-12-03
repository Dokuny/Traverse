package com.traverse.global.oauth.component.impl;


import static com.traverse.global.oauth.exception.OAuthErrorCode.NOT_SUPPORT_VENDOR;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;


import com.traverse.command.domain.member.entity.Member;
import com.traverse.global.oauth.component.OAuthMemberClient;
import com.traverse.global.oauth.exception.OAuthException;
import com.traverse.global.oauth.vendor.enums.OAuthDomain;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component
public class OAuthMemberClientComposite implements OAuthMemberClient {

	private final Map<OAuthDomain, OAuthMemberClient> clientMap;

	public OAuthMemberClientComposite(Set<OAuthMemberClient> clients) {
		this.clientMap = clients.stream()
			.collect(
				toMap(OAuthMemberClient::getOAuthDomain, identity()));
	}

	@Override
	public OAuthDomain getOAuthDomain() {
		return null;
	}

	public Member fetch(OAuthDomain oAuthDomain, String authCode) {
		return getClient(oAuthDomain).fetch(oAuthDomain,authCode);
	}

	private OAuthMemberClient getClient(OAuthDomain oAuthDomain) {
		return Optional.ofNullable(clientMap.get(oAuthDomain))
			.orElseThrow(() -> new OAuthException(NOT_SUPPORT_VENDOR));
	}

}
