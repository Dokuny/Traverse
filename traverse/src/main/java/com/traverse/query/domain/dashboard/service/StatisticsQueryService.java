package com.traverse.query.domain.dashboard.service;

import com.traverse.query.domain.dashboard.dto.MyTouringAreasStatisticsInfo;
import com.traverse.query.domain.dashboard.dto.MyTouringTypesStatisticsInfo;
import com.traverse.query.domain.dashboard.mapper.StatisticsMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StatisticsQueryService {

	private final StatisticsMapper statisticsMapper;

	public List<MyTouringTypesStatisticsInfo> getMyTouringTypes(Long memberId) {
		return statisticsMapper.getMyTouringTypesStatistics(memberId);
	}

	public MyTouringAreasStatisticsInfo getMyTouringAreas(Long memberId) {
		return statisticsMapper.getMyTouringAreasStatistics(memberId);
	}
}
