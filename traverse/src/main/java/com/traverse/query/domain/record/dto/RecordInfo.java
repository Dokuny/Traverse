package com.traverse.query.domain.record.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecordInfo {

    private Long recordId;
    private String title;
    private List<RecordImage> recordImages;
    private Boolean isHighlight;
}
