package com.traverse.command.domain.dashboard.controller;

import com.traverse.command.domain.dashboard.service.WeatherService;
import com.traverse.command.domain.dashboard.dto.NowWeather;
import com.traverse.global.common.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/weather")
@RestController
public class WeatherController {

	private final WeatherService weatherService;

	@GetMapping("/now")
	public ResponseEntity<Message<NowWeather>> getNowWeather(
		@RequestParam("lat") Double lat,
		@RequestParam("lng") Double lng
	) {
		return ResponseEntity.ok(Message.success(weatherService.getNowWeather(lat, lng)));
	}

}
