package com.traverse.query.domain.dashboard.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;

@JsonNaming(SnakeCaseStrategy.class)
@Builder
public record YoutubeInfo(
	String code
){}
