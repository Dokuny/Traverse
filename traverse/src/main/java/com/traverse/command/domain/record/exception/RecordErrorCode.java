package com.traverse.command.domain.record.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum RecordErrorCode {
	NOT_EXISTS_PLAN("존재하지 않는 계획입니다.", BAD_REQUEST),
	NOT_EXISTS_PLACE("존재하지 않는 장소입니다.", BAD_REQUEST),
	NOT_EXISTS_RECORD("존재하지 않는 기록입니다.", BAD_REQUEST),
	NO_PERMISSION("권한이 없습니다.", FORBIDDEN),;


	private final String message;
	private final HttpStatus httpStatus;


}
