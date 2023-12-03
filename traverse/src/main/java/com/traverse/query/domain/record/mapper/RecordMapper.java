package com.traverse.query.domain.record.mapper;

import com.traverse.query.domain.record.dto.RecordDetailInfo;
import com.traverse.query.domain.record.dto.RecordInfo;
import com.traverse.query.domain.record.dto.ShareRecordInfo;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RecordMapper {

    List<RecordInfo> getMyRecords(@Param("memberId") Long memberId);

    RecordDetailInfo getMyRecordDetail(@Param("recordId") Long recordId, @Param("memberId") Long memberId);

    List<RecordInfo> getMyHighlights(@Param("memberId") Long memberId);

    List<ShareRecordInfo> getRecords();
}
