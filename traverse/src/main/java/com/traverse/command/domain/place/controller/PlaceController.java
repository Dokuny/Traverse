package com.traverse.command.domain.place.controller;

import com.traverse.command.domain.place.dto.PlaceRegisterForm;
import com.traverse.command.domain.place.service.PlaceService;
import com.traverse.global.common.Message;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/places")
@RestController
public class PlaceController {

	private final PlaceService placeService;

	@PostMapping
	public ResponseEntity<Message<Long>> registerPlace(@RequestBody @Valid PlaceRegisterForm form) {
		return ResponseEntity.ok(Message.success(placeService.registerPlace(form)));
	}

}
