package com.traverse.global.weather.impl;

import com.traverse.global.weather.GpsTransfer;
import com.traverse.global.weather.GpsTransfer.XY;
import com.traverse.global.weather.PublicDataApiClient;
import com.traverse.global.weather.WeatherProvider;
import com.traverse.global.weather.dto.PublicDataWeatherResponse;
import com.traverse.global.weather.dto.PublicDataWeatherResponse.Response.Body.Items.Item;
import com.traverse.global.weather.dto.WeatherInfo;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;


@RequiredArgsConstructor
@Component
public class PublicDatarWeatherProvider implements WeatherProvider {


	@Value("${public-data.weather-key}")
	private String key;

	private final PublicDataApiClient client;

	@Async("threadPoolTaskExecutor")
	@Override
	public CompletableFuture<WeatherInfo> getCurWeather(Double lat, Double lng) {

		String curDate = getCurDate();
		String curTime = getCurTime();

		XY xy = GpsTransfer.transferToXY(lat, lng);

		PublicDataWeatherResponse response = client.fetchWeather(
			params(curDate, curTime, xy.getX(), xy.getY()));

		List<Item> infos = response.response().body().items().item();

		String weather = getWeather(infos);
		String temp = getTemparature(infos);

		return CompletableFuture.completedFuture(WeatherInfo.builder()
			.temparature(temp)
			.weathewr(weather)
			.build());
	}

	private static String getTemparature(List<Item> infos) {
		return infos.stream()
			.filter(item -> item.category().equals("T1H"))
			.map(Item::fcstValue)
			.findFirst()
			.get();
	}

	private static String getWeather(List<Item> infos) {
		String sky = infos.stream()
			.filter(item -> item.category().equals("SKY"))
			.map(Item::fcstValue)
			.findFirst()
			.get();

		String pty = infos.stream()
			.filter(item -> item.category().equals("PTY"))
			.map(Item::fcstValue)
			.findFirst()
			.get();


		String weather = "";

		switch (sky) {
			case "1":
				weather = "맑음";
				break;
			case "3", "4":
				weather = "구름";
				break;
		}

		switch (pty) {
			case "1", "2", "5", "6":
				weather = "비";
				break;
			case "3", "7":
				weather = "눈";
				break;
		}

		return weather;
	}

	private MultiValueMap<String, String> params(String date, String time, Integer x, Integer y) {
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("serviceKey", key);
		map.add("pageNo", "1");
		map.add("numOfRows", "1000");
		map.add("dataType", "JSON");
		map.add("base_date", date);
		map.add("base_time", time);
		map.add("nx", String.valueOf(x));
		map.add("ny", String.valueOf(y));
		return map;
	}

	private String getCurDate() {
		StringBuilder curDate = new StringBuilder();

		LocalDate now = LocalDate.now();

		curDate.append(now.getYear())
			.append(formatZero(now.getMonthValue()))
			.append(formatZero(now.getDayOfMonth()));

		return curDate.toString();
	}

	private String getCurTime() {
		StringBuilder curTime = new StringBuilder();

		LocalDateTime now = LocalDateTime.now();

		curTime.append(formatZero(now.getHour()-1))
			.append(formatZero(now.getMinute()));

		return curTime.toString();
	}

	private String formatZero(int num) {
		if (num < 10) {
			return "0" + num;
		}
		return String.valueOf(num);
	}
}
