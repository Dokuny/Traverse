package com.traverse.command.domain.place.dto;


import com.traverse.command.domain.place.entity.Place;
import com.traverse.command.domain.place.entity.enums.PlaceType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


public record PlaceRegisterForm(
	@NotEmpty
	String name,
	String introduce,
	@NotEmpty
	String address,
	@NotNull
	Double lat,
	@NotNull
	Double lng,
	String thumbnail,
	@NotNull
	PlaceType placeType

) {


	public Place toEntity() {
		return Place.builder()
			.name(name)
			.introduce(introduce)
			.address(address)
			.lat(lat)
			.lng(lng)
			.thumbnail(thumbnail)
			.placeType(placeType)
			.build();
	}

}
