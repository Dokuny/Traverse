package com.traverse.command.domain.record.service;

import com.traverse.command.domain.place.entity.Place;
import com.traverse.command.domain.place.repository.PlaceRepository;
import com.traverse.command.domain.plan.entity.Plan;
import com.traverse.command.domain.plan.repository.PlanRepository;
import com.traverse.command.domain.record.dto.RecordModifyForm;
import com.traverse.command.domain.record.dto.RecordRegisterForm;
import com.traverse.command.domain.record.entity.Record;
import com.traverse.command.domain.record.entity.RecordImage;
import com.traverse.command.domain.record.exception.RecordErrorCode;
import com.traverse.command.domain.record.exception.RecordException;
import com.traverse.command.domain.record.repository.RecordRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.traverse.global.oci.bucket.ObjectStorageService;
import java.util.concurrent.ExecutionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Transactional
@Service
@Slf4j
public class RecordService {

    private final RecordRepository recordRepository;
    private final PlanRepository planRepository;
    private final PlaceRepository placeRepository;
    private final ObjectStorageService objectStorageService;

    public Long registerRecord(Long memberId, RecordRegisterForm form, List<MultipartFile> files)
        throws IOException, ExecutionException, InterruptedException {
        Plan plan = getPlan(form.planId());

        validatePlanOwnership(plan, memberId);

        Place place = placeRepository.findById(form.placeId())
			.orElseThrow(() -> new RecordException(RecordErrorCode.NOT_EXISTS_PLACE));

        // 레코드 엔티티 만들기
        Record record = form.toEntity(plan, place);

        // 이미지 엔티티 만들기 -> URL 필요 (실제 업로드가 필요)
        for (MultipartFile file : files) {
            String url = objectStorageService.uploadImage(file).get();

            record.addImage(RecordImage.builder()
                            .record(record)
                            .url(url)
                    .build());
        }

        return recordRepository.save(record).getId();
    }

    public Long modifyRecord(Long memberId, Long recordId, RecordModifyForm form) {
        Record record = getRecord(recordId);

        Plan plan = getPlan(record.getPlan().getId());

        validatePlanOwnership(plan, memberId);

        record.changeContent(form.content());
        record.changeHighlight(form.highlight());

        List<RecordImage> images = record.getImages();

        int idx = 0;
        while (images.size() != idx) {

            boolean isDelete = true;

            for (Long imageId : form.images()) {
                if (images.get(idx).getId().equals(imageId)) {
                    isDelete = false;
                }
            }
            if (isDelete) {
                images.remove(idx);
            } else {
                idx++;
            }
        }

        return recordId;
    }


    public Long deleteRecord(Long memberId, Long recordId) {
        Record record = getRecord(recordId);

        Plan plan = getPlan(record.getPlan().getId());

        validatePlanOwnership(plan, memberId);

        recordRepository.delete(record);

        return recordId;
    }

    private Record getRecord(Long recordId) {
        return recordRepository.findById(recordId)
                .orElseThrow(() -> new RecordException(RecordErrorCode.NOT_EXISTS_RECORD));
    }

    private Plan getPlan(Long planId) {
        return planRepository.findById(planId)
                .orElseThrow(() -> new RecordException(RecordErrorCode.NOT_EXISTS_RECORD));
    }

    private void validatePlanOwnership(Plan plan, Long memberId) {
        if (!plan.getMember().getId().equals(memberId)) {
            throw new RecordException(RecordErrorCode.NO_PERMISSION);
        }
    }


}
