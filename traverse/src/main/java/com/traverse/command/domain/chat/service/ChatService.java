package com.traverse.command.domain.chat.service;


import com.traverse.command.domain.chat.dto.Chat;
import com.traverse.command.domain.chat.dto.ChatMessage;
import com.traverse.command.domain.member.exception.MemberErrorCode;
import com.traverse.command.domain.member.exception.MemberException;
import com.traverse.global.jwt.exception.JwtException;
import com.traverse.global.jwt.security.LoginMember;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {

	private final RabbitTemplate rabbitTemplate;
	private final TopicExchange topicExchange;

	public void sendMessage(ChatMessage message, Long memberId, Long placeId) {
		log.info("{}",memberId);
		if (!Objects.equals(memberId, message.getMemberId())) {
			throw new MemberException(MemberErrorCode.NOT_EXISTS_MEMBER);
		}

		Chat chat = Chat.builder()
			.memberId(memberId)
			.message(message.getMessage())
			.nickname(message.getNickname())
			.thumbnail(message.getThumbnail())
			.sendTime(LocalDateTime.now())
			.build();

		rabbitTemplate.convertAndSend(topicExchange.getName(), "room." + placeId, chat);
	}

}
