package com.traverse.command.domain.place.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum PlaceErrorCode {
	ALREADY_EXISTS_PLACE("이미 등록된 장소입니다.", BAD_REQUEST);


	private final String message;
	private final HttpStatus httpStatus;


}
