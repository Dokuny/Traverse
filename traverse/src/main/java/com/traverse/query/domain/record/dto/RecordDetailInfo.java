package com.traverse.query.domain.record.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RecordDetailInfo {

    private Long recordId;
    private String title;
    private String content;
    private Boolean isHighlight;
    private List<RecordImage> recordImages;


}
