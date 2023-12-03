package com.traverse.query.domain.record.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HighlightInfo {
    RecordDetailInfo recordDetailInfo;
}
