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
public class ShareRecordInfo {

    private Long recordId;
    private String memberNickname;
    private String memberThumbnail;
    private String recordContent;
    private String placeName;
    private List<RecordImage> recordImages;
}
