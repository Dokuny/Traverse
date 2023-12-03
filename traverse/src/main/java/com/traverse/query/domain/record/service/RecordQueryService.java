package com.traverse.query.domain.record.service;

import com.traverse.query.domain.record.dto.RecordDetailInfo;
import com.traverse.query.domain.record.dto.RecordInfo;
import com.traverse.query.domain.record.dto.ShareRecordInfo;
import com.traverse.query.domain.record.mapper.RecordMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RecordQueryService {

    private final RecordMapper recordMapper;

    public List<RecordInfo> getMyRecords(Long memberId) {
//        List<RecordInfo> myRecords = recordMapper.getMyRecords(memberId);
//
//        for (RecordInfo myRecord : myRecords) {
//            log.info(" 내 레코드 정보 : {}", myRecord.getRecordId());
//
//            List<RecordImage> recordImages = myRecord.getRecordImages();
//            for (RecordImage recordImage : recordImages) {
//                log.info("내 이미지 정보 : {}", recordImage.getUrl() );
//            }
//        }

        return recordMapper.getMyRecords(memberId);
    }

    public RecordDetailInfo getMyRecordDetail(Long recordId, Long memberId) {
        return recordMapper.getMyRecordDetail(recordId, memberId);
    }

    public List<RecordInfo> getMyHighlights(Long memberId) {
        return recordMapper.getMyHighlights(memberId);
    }

    public List<ShareRecordInfo> getRecords() {
        return recordMapper.getRecords();
    }


}
