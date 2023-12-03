package com.traverse.query.domain.plan.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;

@JsonNaming(SnakeCaseStrategy.class)
@Builder
public record PlanInfo(
	Long planId,
	String title,
	String createdAt
){

}
