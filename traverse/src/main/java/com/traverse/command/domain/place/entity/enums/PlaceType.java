package com.traverse.command.domain.place.entity.enums;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AllArgsConstructor;

@JsonFormat
@AllArgsConstructor
public enum PlaceType {
	TOUR("관광지"),
	ACCOMMODATION("숙박"),
	LEISURE("레포츠"),
	CULTURE("문화시설"),
	SHOP("쇼핑"),
	RESTAURANT("음식점");

	private String description;

	@JsonCreator
	public static PlaceType from(String type) {
		for (PlaceType placeType : PlaceType.values()) {
			if (placeType.description.equals(type)) {
				return placeType;
			}
		}

		throw new RuntimeException("존재하지 않는 여행 타입");
	}

	@JsonValue
	public String getDescription() {
		return description;
	}

}
