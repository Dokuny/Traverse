package com.traverse.command.domain.member.dto;

import lombok.Builder;

@Builder
public record Tokens(String accessToken, String refreshToken) {
}
