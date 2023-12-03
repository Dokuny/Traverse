package com.traverse.query.domain.record.controller;

import com.traverse.global.common.Message;
import com.traverse.global.jwt.security.LoginMember;
import com.traverse.query.domain.record.dto.RecordDetailInfo;
import com.traverse.query.domain.record.dto.RecordInfo;
import com.traverse.query.domain.record.dto.ShareRecordInfo;
import com.traverse.query.domain.record.service.RecordQueryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/records")
@RestController
public class RecordQueryController {

    private final RecordQueryService recordQueryService;

    @GetMapping("/me")
    public ResponseEntity<Message<List<RecordInfo>>> getMyRecords(
            @AuthenticationPrincipal LoginMember loginMember
            ){
        return ResponseEntity.ok(Message.success(recordQueryService.getMyRecords(loginMember.id())));
    }

    @GetMapping("/me/{recordId}")
    public ResponseEntity<Message<RecordDetailInfo>> getMyRecordDetail(@AuthenticationPrincipal LoginMember loginMember,
                                                                       @PathVariable Long recordId) {
        return ResponseEntity.ok(Message.success(recordQueryService.getMyRecordDetail(recordId, loginMember.id())));
    }

    @GetMapping("/highlights")
    public ResponseEntity<Message<List<RecordInfo>>> getMyHighlights(@AuthenticationPrincipal LoginMember loginMember) {
        return ResponseEntity.ok(Message.success(recordQueryService.getMyHighlights(loginMember.id())));
    }

    @GetMapping
    public ResponseEntity<Message<List<ShareRecordInfo>>> getRecords() {
        return ResponseEntity.ok(Message.success(recordQueryService.getRecords()));
    }

}
