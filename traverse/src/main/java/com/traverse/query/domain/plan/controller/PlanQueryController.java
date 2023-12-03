package com.traverse.query.domain.plan.controller;

import com.traverse.global.common.Message;
import com.traverse.global.jwt.security.LoginMember;
import com.traverse.query.domain.plan.dto.NearPlanInfo;
import com.traverse.query.domain.plan.dto.PlanDetailInfo;
import com.traverse.query.domain.plan.dto.PlanInfo;
import com.traverse.query.domain.plan.service.PlanQueryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/plans")
@RestController
public class PlanQueryController {

	private final PlanQueryService planQueryService;

	@GetMapping("/me")
	public ResponseEntity<Message<List<PlanDetailInfo>>> getMyPlans(
		@AuthenticationPrincipal LoginMember loginMember
	) {
		return ResponseEntity.ok(Message.success(planQueryService.getMyPlans(loginMember.id())));
	}

	@GetMapping("/me/{planId}")
	public ResponseEntity<Message<PlanDetailInfo>> getMyPlan(
		@AuthenticationPrincipal LoginMember loginMember,
		@PathVariable Long planId
	) {
		return ResponseEntity.ok(
			Message.success(planQueryService.getMyPlan(planId, loginMember.id())));
	}

	@PreAuthorize("hasAnyAuthority('MEMBER')")
	@GetMapping("/me/near")
	public ResponseEntity<Message<NearPlanInfo>> getMyNearPlan(
		@AuthenticationPrincipal LoginMember loginMember
	) {
		return ResponseEntity.ok(Message.success(planQueryService.getMyNearPlan(loginMember.id())));
	}
}
