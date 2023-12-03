package com.traverse.command.domain.chat.controller;


import com.traverse.command.domain.chat.dto.ChatMessage;
import com.traverse.command.domain.chat.service.ChatService;
import com.traverse.global.jwt.security.LoginMember;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ChatController {

	private final ChatService chatService;

	@MessageMapping("chat/{placeId}")
	public void send(
		Principal principal,
		@DestinationVariable Long placeId,
		ChatMessage message
	) {
		chatService.sendMessage(message,  Long.valueOf(principal.getName()), placeId);
	}

}
