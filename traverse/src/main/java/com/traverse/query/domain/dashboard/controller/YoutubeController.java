package com.traverse.query.domain.dashboard.controller;


import com.traverse.global.common.Message;
import com.traverse.query.domain.dashboard.dto.YoutubeInfo;
import com.traverse.query.domain.dashboard.service.YoutubeQueryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/youtube")
@RestController
public class YoutubeController {

	private final YoutubeQueryService youtubeQueryService;

	@GetMapping("/daily")
	public ResponseEntity<Message<List<YoutubeInfo>>> getDailyYoutube() {
		return ResponseEntity.ok(Message.success(youtubeQueryService.getDailyYoutube()));
	}
}
