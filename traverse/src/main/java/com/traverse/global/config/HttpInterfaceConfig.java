package com.traverse.global.config;


import com.traverse.global.oauth.vendor.google.client.GoogleApiClient;
import com.traverse.global.oauth.vendor.kakao.client.KakaoApiClient;
import com.traverse.global.oauth.vendor.naver.client.NaverApiClient;
import com.traverse.global.weather.PublicDataApiClient;
import java.time.Duration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class HttpInterfaceConfig {

	@Bean
	public KakaoApiClient kakaoApiClient() {
		return createHttpInterface(KakaoApiClient.class);
	}
	@Bean
	public NaverApiClient naverApiClient() { return createHttpInterface(NaverApiClient.class); }
	@Bean
	public GoogleApiClient googleApiClient() { return createHttpInterface(GoogleApiClient.class); }
	@Bean
	public PublicDataApiClient publicDataApiClient() { return createHttpInterface(
		PublicDataApiClient.class); }


	private <T> T createHttpInterface(Class<T> clazz) {
		WebClient webClient = WebClient.create();
		HttpServiceProxyFactory build = HttpServiceProxyFactory
			.builder(WebClientAdapter.forClient(webClient))
			.blockTimeout(Duration.ofMillis(15000))
			.build();
		return build.createClient(clazz);
	}

}
