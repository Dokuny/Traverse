package com.traverse.query.domain.plan.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.time.LocalDateTime;
import lombok.Builder;

@JsonNaming(SnakeCaseStrategy.class)
@Builder
public record NearPlanInfo(
	Long planId,
	LocalDateTime startedAt,
	LocalDateTime endedAt,
	String title
){ }
