package com.traverse.query.domain.place.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;

@JsonNaming(SnakeCaseStrategy.class)
@Builder
public record PlaceInfo (

	Long placeId,
	String name,
	String address,
	String placeType,
	String thumbnail,
	String introduce,
	Double lat,
	Double lng

){

}
