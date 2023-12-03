package com.traverse.global.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
public record Message<T> (DataHeader dataHeader, T dataBody) {

	public static <T> Message<T> success(T dataBody) {
		return Message.<T>builder()
			.dataHeader(DataHeader.success())
			.dataBody(dataBody)
			.build();
	}

	public static <T> Message<T> success(T dataBody, String code, String resultMessage) {
		return Message.<T>builder()
			.dataHeader(DataHeader.success(code, resultMessage))
			.dataBody(dataBody)
			.build();
	}

	public static Message success() {
		return Message.builder()
			.dataHeader(DataHeader.success())
			.build();
	}

	public static <T> Message<T> fail(String resultCode, String resultMessage) {
		return Message.<T>builder()
			.dataHeader(DataHeader.fail(resultCode, resultMessage))
			.dataBody(null)
			.build();
	}


	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	@Getter
	private static class DataHeader {

		private int successCode;
		private String resultCode;
		private String resultMessage;

		private static DataHeader success() {
			return DataHeader.builder()
				.successCode(0)
				.build();
		}

		private static DataHeader success(String code, String resultMessage) {
			return DataHeader.builder()
				.successCode(0)
				.resultCode(code)
				.resultMessage(resultMessage)
				.build();
		}

		private static DataHeader fail(String resultCode, String resultMessage) {
			return DataHeader.builder()
				.successCode(1)
				.resultCode(resultCode)
				.resultMessage(resultMessage)
				.build();
		}


	}
}