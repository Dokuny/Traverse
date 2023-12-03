package com.traverse.global.weather;

import com.traverse.global.weather.dto.PublicDataWeatherResponse;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

public interface PublicDataApiClient {

	@GetExchange("https://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtFcst")
	PublicDataWeatherResponse fetchWeather(@RequestParam(value = "params") MultiValueMap<String,String> params);
}
