package com.traverse.global.ws;

import com.sun.security.auth.UserPrincipal;
import com.traverse.global.jwt.JwtTokenProvider;
import com.traverse.global.jwt.exception.JwtErrorCode;
import com.traverse.global.jwt.exception.JwtException;
import com.traverse.global.jwt.security.JwtAuthenticationToken;
import com.traverse.global.jwt.security.LoginMember;
import jakarta.ws.rs.core.HttpHeaders;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
@Component
public class WebSocketAuthenticationInterceptor implements ChannelInterceptor {

	private final JwtTokenProvider jwtTokenProvider;

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message,
			StompHeaderAccessor.class);

		if (StompCommand.CONNECT.equals(accessor.getCommand()) || StompCommand.SEND.equals(accessor.getCommand())) {
			try {
				LoginMember loginMember = jwtTokenProvider.parseAccessToken(
					accessor.getFirstNativeHeader(HttpHeaders.AUTHORIZATION).substring(7));

				log.info("Member connected WebSocket. id : {} , name : {}",loginMember.id(),loginMember.nickname());

				accessor.setUser(new UserPrincipal(String.valueOf(loginMember.id())));
			} catch (Exception e) {
				throw new JwtException(JwtErrorCode.INVALID_TOKEN);
			}
		}

		return message;
	}

}
