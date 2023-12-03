package com.traverse.global.oauth.component.impl;


import static com.traverse.global.oauth.exception.OAuthErrorCode.NOT_SUPPORT_VENDOR;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;


import com.traverse.global.oauth.component.OAuthCodeUrlProvider;
import com.traverse.global.oauth.exception.OAuthException;
import com.traverse.global.oauth.vendor.enums.OAuthDomain;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component
public class OAuthCodeUrlProviderComposite implements OAuthCodeUrlProvider {

	private final Map<OAuthDomain, OAuthCodeUrlProvider> providerMap;

	public OAuthCodeUrlProviderComposite(Set<OAuthCodeUrlProvider> providers) {
		this.providerMap = providers.stream()
			.collect(
				toMap(OAuthCodeUrlProvider::support, identity()));
	}

	@Override
	public OAuthDomain support() {
		return null;
	}

	@Override
	public String provide(OAuthDomain oAuthDomain) {
		return getProvider(oAuthDomain).provide(oAuthDomain);
	}

	private OAuthCodeUrlProvider getProvider(OAuthDomain oAuthDomain) {
		return Optional.ofNullable(providerMap.get(oAuthDomain))
			.orElseThrow(() -> new OAuthException(NOT_SUPPORT_VENDOR));
	}

}
