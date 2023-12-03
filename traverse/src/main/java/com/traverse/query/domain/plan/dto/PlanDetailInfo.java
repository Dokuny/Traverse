package com.traverse.query.domain.plan.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonNaming(SnakeCaseStrategy.class)
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class PlanDetailInfo {

	private Long planId;
	private String title;
	private String createdAt;

	private List<DestinationDetailInfo> destinations;
}
