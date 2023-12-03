package com.traverse.command.domain.dashboard.service;

import com.traverse.command.domain.dashboard.entity.DailyYoutube;
import com.traverse.command.domain.dashboard.repository.DailyYoutubeRepository;
import com.traverse.global.youtube.DailyYoutubeCollector;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class YoutubeService {

	private final DailyYoutubeCollector dailyYoutubeCollector;
	private final DailyYoutubeRepository dailyYoutubeRepository;

	@Scheduled(cron = "0 5 0 * * *")
	protected void collectDailyYoutube() {
		List<String> codes = dailyYoutubeCollector.collectDailyYoutubeCodes("국내 여행");

		codes.forEach(code ->
			dailyYoutubeRepository.save(DailyYoutube.builder()
				.code(code)
				.build())
		);
	}

}
