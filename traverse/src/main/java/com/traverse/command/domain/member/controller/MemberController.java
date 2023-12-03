package com.traverse.command.domain.member.controller;

import com.traverse.command.domain.member.dto.MemberModifyForm;
import com.traverse.command.domain.member.service.MemberService;
import com.traverse.command.domain.member.dto.MemberInfo;
import com.traverse.global.common.Message;
import com.traverse.global.jwt.security.LoginMember;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RequestMapping("/members")
@RestController
public class MemberController {

	private final MemberService memberService;

	@PutMapping("/me")
	public ResponseEntity<Message<MemberInfo>> modifyMemberInfo(
		@AuthenticationPrincipal LoginMember loginMember,
		@RequestPart(required = false, value = "thumbnail") MultipartFile thumbnail,
		@RequestPart("form") @Valid MemberModifyForm form
	) throws IOException, ExecutionException, InterruptedException {
		return ResponseEntity.ok(
			Message.success(memberService.modifyMemberInfo(loginMember.id(), thumbnail, form)));
	}

	@DeleteMapping("/me")
	public ResponseEntity<Message<Long>> resignMember(
		@AuthenticationPrincipal LoginMember loginMember
	) {
		return ResponseEntity.ok(
			Message.success(memberService.resignMember(loginMember.id())));
	}
}
