package com.traverse.command.domain.plan.exception;

import lombok.Getter;

@Getter
public class PlanException extends RuntimeException {

	private final PlanErrorCode errorCode;

	public PlanException(PlanErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}
}
