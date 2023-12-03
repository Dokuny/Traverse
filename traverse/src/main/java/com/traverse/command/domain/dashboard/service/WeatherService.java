package com.traverse.command.domain.dashboard.service;

import com.traverse.command.domain.dashboard.dto.NowWeather;
import com.traverse.global.weather.AddressProvider;
import com.traverse.global.weather.WeatherProvider;
import com.traverse.global.weather.dto.AddressInfo;
import com.traverse.global.weather.dto.WeatherInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class WeatherService {

	private final WeatherProvider weatherProvider;
	private final AddressProvider addressProvider;

	public NowWeather getNowWeather(Double lat, Double lng) {
		try {

			WeatherInfo curWeather = weatherProvider.getCurWeather(lat, lng).get();
			AddressInfo address = addressProvider.getAddress(lat, lng).get();
			return NowWeather.builder()
				.district(address.name())
				.temparature(curWeather.temparature())
				.weather(curWeather.weathewr())
				.build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  null;
	}
}
