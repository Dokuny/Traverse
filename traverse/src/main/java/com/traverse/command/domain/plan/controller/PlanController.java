package com.traverse.command.domain.plan.controller;

import com.traverse.command.domain.plan.dto.PlanModifyForm;
import com.traverse.command.domain.plan.dto.PlanRegisterForm;
import com.traverse.command.domain.plan.service.PlanService;
import com.traverse.global.common.Message;
import com.traverse.global.jwt.security.LoginMember;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/plans")
@RestController
public class PlanController {

	private final PlanService planService;

	@PreAuthorize("hasAnyAuthority('MEMBER')")
	@PostMapping
	public ResponseEntity<Message<Long>> registerPlan(
		@RequestBody @Valid PlanRegisterForm form,
		@AuthenticationPrincipal LoginMember loginMember
	) {
		return ResponseEntity.ok(Message.success(planService.registerPlan(loginMember.id(), form)));
	}

	@PreAuthorize("hasAnyAuthority('MEMBER','ADMIN')")
	@DeleteMapping("/{planId}")
	public ResponseEntity<Message<Long>> deletePlan(
		@PathVariable Long planId,
		@AuthenticationPrincipal LoginMember loginMember
	) {
		return ResponseEntity.ok(Message.success(planService.deletePlan(loginMember.id(), planId)));
	}

	@PreAuthorize("hasAnyAuthority('MEMBER')")
	@PutMapping("/{planId}")
	public ResponseEntity<Message<Long>> modifyPlan(
		@PathVariable Long planId,
		@RequestBody @Valid PlanModifyForm form,
		@AuthenticationPrincipal LoginMember loginMember
	) {
		return ResponseEntity.ok(
			Message.success(planService.modifyPlan(loginMember.id(), planId, form)));
	}

}
