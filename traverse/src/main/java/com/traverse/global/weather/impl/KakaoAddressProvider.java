package com.traverse.global.weather.impl;

import static com.traverse.global.weather.exception.WeatherErrorCode.BAD_REQUEST;

import com.traverse.global.oauth.vendor.kakao.KakaoOAuthProps;
import com.traverse.global.oauth.vendor.kakao.client.KakaoApiClient;
import com.traverse.global.weather.dto.AddressInfo;
import com.traverse.global.weather.AddressProvider;
import com.traverse.global.weather.dto.KakaoCoordToDistrictResponse;
import com.traverse.global.weather.exception.WeatherException;
import com.traverse.global.weather.dto.KakaoCoordToDistrictResponse.District;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@RequiredArgsConstructor
@Component
public class KakaoAddressProvider implements AddressProvider {

	private final KakaoApiClient kakaoApiClient;
	private final KakaoOAuthProps props;

	@Async("threadPoolTaskExecutor")
	@Override
	public CompletableFuture<AddressInfo> getAddress(Double lat, Double lng) {

		KakaoCoordToDistrictResponse response = kakaoApiClient.fetchAddress(
			"KakaoAK " + props.clientId(), params(lat, lng));

		if (response.documents().size() == 0) {
			throw new WeatherException(BAD_REQUEST);
		}

		District district = response.documents().get(0);

		return CompletableFuture.completedFuture(AddressInfo.builder()
			.name(district.address_name())
			.firstDepth(district.region_1depth_name())
			.secondDepth(district.region_2depth_name())
			.thirdDepth(district.region_3depth_name())
			.build());
	}

	private MultiValueMap<String, Double> params(Double lat, Double lng) {
		MultiValueMap<String, Double> map = new LinkedMultiValueMap<>();
		map.add("x", lng);
		map.add("y", lat);
		return map;
	}
}
