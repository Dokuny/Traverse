package com.traverse.global.jwt.security;


import com.traverse.command.domain.member.entity.enums.MemberRole;
import lombok.Builder;

@Builder
public record LoginMember(
	Long id,
	String email,
	String nickname,
	String profileImageUrl,
	MemberRole role
) {
}
