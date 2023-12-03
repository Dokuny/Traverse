package com.traverse.query.domain.dashboard.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonNaming(SnakeCaseStrategy.class)
public record MyTouringTypesStatisticsInfo(
	Integer cnt,
	String placeType

){}
