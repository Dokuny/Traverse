package com.traverse.command.domain.record.controller;

import com.traverse.command.domain.member.dto.MemberModifyForm;
import com.traverse.command.domain.record.dto.RecordModifyForm;
import com.traverse.command.domain.record.dto.RecordRegisterForm;
import com.traverse.command.domain.record.service.RecordService;
import com.traverse.global.common.Message;
import com.traverse.global.jwt.security.LoginMember;
import jakarta.validation.Valid;
import java.util.concurrent.ExecutionException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/records")
@RestController
public class RecordController {

	private final RecordService recordService;

	@PreAuthorize("hasAnyAuthority('MEMBER')")
	@PostMapping
	public ResponseEntity<Message<Long>> registerRecord(
		@AuthenticationPrincipal LoginMember loginMember,
		@RequestPart(required = false, value = "files") List<MultipartFile> files,
		@RequestPart("data") @Valid RecordRegisterForm form
	) throws IOException, ExecutionException, InterruptedException {
		return ResponseEntity.ok(
			Message.success(recordService.registerRecord(loginMember.id(), form, files)));
	}

	@PreAuthorize("hasAnyAuthority('MEMBER')")
	@PutMapping("/{recordId}")
	public ResponseEntity<Message<Long>> modifyRecord(
		@AuthenticationPrincipal LoginMember loginMember,
		@PathVariable Long recordId,
		@RequestBody @Valid RecordModifyForm form
	) {
		return ResponseEntity.ok(
			Message.success(recordService.modifyRecord(loginMember.id(), recordId, form)));
	}

	@PreAuthorize("hasAnyAuthority('MEMBER', 'ADMIN')")
	@DeleteMapping("/{recordId}")
	public ResponseEntity<Message<Long>> deleteRecord(
		@AuthenticationPrincipal LoginMember loginMember,
		@PathVariable Long recordId
	) {
		return ResponseEntity.ok(
			Message.success(recordService.deleteRecord(loginMember.id(), recordId)));
	}
}
