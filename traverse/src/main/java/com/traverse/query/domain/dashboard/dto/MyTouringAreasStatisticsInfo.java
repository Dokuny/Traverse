package com.traverse.query.domain.dashboard.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(SnakeCaseStrategy.class)
public record MyTouringAreasStatisticsInfo(

	Integer seoul,
	Integer busan,
	Integer daegu,
	Integer incheon,
	Integer gwangju,
	Integer daejeon,
	Integer ulsan,
	Integer sejong,
	Integer gyeongi,
	Integer gangwon,
	Integer chungbuk,
	Integer chungnam,
	Integer jeonbuk,
	Integer jeonnam,
	Integer gyeongbuk,
	Integer gyeongnam,
	Integer jeju
){}
