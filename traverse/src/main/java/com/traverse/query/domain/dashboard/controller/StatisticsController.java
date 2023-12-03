package com.traverse.query.domain.dashboard.controller;

import com.traverse.global.common.Message;
import com.traverse.global.jwt.security.LoginMember;
import com.traverse.query.domain.dashboard.dto.MyTouringAreasStatisticsInfo;
import com.traverse.query.domain.dashboard.dto.MyTouringTypesStatisticsInfo;
import com.traverse.query.domain.dashboard.service.StatisticsQueryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/statistics")
@RestController
public class StatisticsController {

	private final StatisticsQueryService statisticsQueryService;

	@GetMapping("/me/types")
	public ResponseEntity<Message<List<MyTouringTypesStatisticsInfo>>> getMyTouringTypes(
		@AuthenticationPrincipal LoginMember loginMember
	) {
		return ResponseEntity.ok(
			Message.success(statisticsQueryService.getMyTouringTypes(loginMember.id())));
	}

	@GetMapping("/me/areas")
	public ResponseEntity<Message<MyTouringAreasStatisticsInfo>> getMyTouringAreas(
		@AuthenticationPrincipal LoginMember loginMember
	) {
		return ResponseEntity.ok(
			Message.success(statisticsQueryService.getMyTouringAreas(loginMember.id())));
	}
}
