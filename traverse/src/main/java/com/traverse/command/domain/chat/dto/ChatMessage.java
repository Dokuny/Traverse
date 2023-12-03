package com.traverse.command.domain.chat.dto;

import lombok.Data;

@Data
public class ChatMessage {

	private Long memberId;
	private String nickname;
	private String thumbnail;
	private String message;

}
