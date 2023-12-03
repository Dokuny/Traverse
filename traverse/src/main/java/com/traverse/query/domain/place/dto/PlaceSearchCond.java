package com.traverse.query.domain.place.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.Min;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@JsonNaming(SnakeCaseStrategy.class)
public class PlaceSearchCond {

	private String keyword;

	@Min(0)
	private Long id = 0L;

	private List<String> types;
}
