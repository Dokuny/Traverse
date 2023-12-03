package com.traverse.query.domain.dashboard.service;

import com.traverse.query.domain.dashboard.dto.YoutubeInfo;
import com.traverse.query.domain.dashboard.mapper.YoutubeMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class YoutubeQueryService {

	private final YoutubeMapper youtubeMapper;

	public List<YoutubeInfo> getDailyYoutube() {
		return youtubeMapper.getDailyYoutube();
	}
}
