package com.traverse.global.common;

import com.traverse.command.domain.place.entity.enums.PlaceType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class PlaceTypeConverter implements AttributeConverter<PlaceType, String> {

	@Override
	public String convertToDatabaseColumn(PlaceType placeType) {
		return placeType.getDescription();
	}

	@Override
	public PlaceType convertToEntityAttribute(String dbData) {
		return PlaceType.from(dbData);
	}
}
