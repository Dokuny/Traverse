package com.traverse.global.weather;

import com.traverse.global.weather.dto.WeatherInfo;
import java.util.concurrent.CompletableFuture;

public interface WeatherProvider {

	CompletableFuture<WeatherInfo> getCurWeather(Double lat, Double lng);

}
