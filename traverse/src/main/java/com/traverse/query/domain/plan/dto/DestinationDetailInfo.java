package com.traverse.query.domain.plan.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonNaming(SnakeCaseStrategy.class)
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DestinationDetailInfo {

	private Long destinationId;
	private Long placeId;
	private String name;
	private String placeType;
	private String address;
	private String comment;
	private Double lat;
	private Double lng;
	private String thumbnail;
	private String visitedAt;

}
