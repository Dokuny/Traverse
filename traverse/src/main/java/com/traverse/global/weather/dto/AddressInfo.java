package com.traverse.global.weather.dto;

import lombok.Builder;

@Builder
public record AddressInfo(
	String name,
	String firstDepth,
	String secondDepth,
	String thirdDepth
) {

}
