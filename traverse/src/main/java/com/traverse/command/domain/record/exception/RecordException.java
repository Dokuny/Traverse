package com.traverse.command.domain.record.exception;

import lombok.Getter;

@Getter
public class RecordException extends RuntimeException {

	private final RecordErrorCode errorCode;

	public RecordException(RecordErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}
}
