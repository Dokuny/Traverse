package com.traverse.query.domain.place.controller;

import com.traverse.global.common.Message;
import com.traverse.query.domain.place.dto.PlaceInfo;
import com.traverse.query.domain.place.dto.PlaceSearchCond;
import com.traverse.query.domain.place.service.PlaceQueryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/places")
@RestController
public class PlaceQueryController {

	private final PlaceQueryService placeQueryService;

	@GetMapping
	public ResponseEntity<Message<List<PlaceInfo>>> searchPlace(PlaceSearchCond cond) {
		return ResponseEntity.ok(Message.success(placeQueryService.searchPlaces(cond)));
	}

	@GetMapping("/duplicate-check")
	public ResponseEntity<Message<Boolean>> checkDuplicatePlace(
		@RequestParam("name") String name,
		@RequestParam("address") String address,
		@RequestParam("type") String type
	) {
		return ResponseEntity.ok(
			Message.success(placeQueryService.checkDuplicate(name, address, type)));
	}

}
