package com.traverse.global.weather.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.List;

@JsonNaming(SnakeCaseStrategy.class)
public record KakaoCoordToDistrictResponse(
	Meta meta,
	List<District> documents

) {

	@JsonNaming(SnakeCaseStrategy.class)
	public record Meta(
		Long totalCount
	) {
	}

	public record District(
		String region_type,
		String code,
		String address_name,
		String region_1depth_name,
		String region_2depth_name,
		String region_3depth_name,
		Double x,
		Double y
	) {
	}



}
