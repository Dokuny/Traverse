package com.traverse.global.error;

import com.traverse.command.domain.place.exception.PlaceException;
import com.traverse.command.domain.plan.exception.PlanException;
import com.traverse.global.common.Message;
import com.traverse.global.jwt.exception.JwtException;
import com.traverse.global.weather.exception.WeatherException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(PlaceException.class)
	public ResponseEntity<Message> placeExceptionHandler(PlaceException e) {
		return ResponseEntity.badRequest()
			.body(Message.fail(e.getErrorCode().name(), e.getMessage()));
	}

	@ExceptionHandler(PlanException.class)
	public ResponseEntity<Message> planExceptionHandler(PlanException e) {
		return ResponseEntity.badRequest()
			.body(Message.fail(e.getErrorCode().name(), e.getMessage()));
	}

	@ExceptionHandler(WeatherException.class)
	public ResponseEntity<Message> utilExceptionHandler(WeatherException e) {
		return ResponseEntity.badRequest()
			.body(Message.fail(e.getErrorCode().name(), e.getMessage()));
	}

	@ExceptionHandler(JwtException.class)
	public ResponseEntity<Message> jwtExceptionHandler(JwtException e) {
		return ResponseEntity.status(e.getErrorCode().getHttpStatus())
			.body(Message.fail(e.getErrorCode().name(), e.getMessage()));
	}
}
