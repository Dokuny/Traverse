package com.traverse.global.oauth.vendor.google.dto;



import static com.traverse.global.oauth.vendor.enums.OAuthDomain.GOOGLE;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.traverse.command.domain.member.entity.Member;
import com.traverse.command.domain.member.entity.enums.MemberRole;
import com.traverse.command.domain.member.entity.embedded.OAuth;


@JsonNaming(SnakeCaseStrategy.class)
public record GoogleMemberResponse(
	String familyName,
	String name,
	String picture,
	String locale,
	String email,
	String givenName,
	String id,
	boolean verifiedEmail
) {

	public Member toDomain() {
		return Member.builder()
			.oAuth(new OAuth(String.valueOf(id), GOOGLE))
			.email(email)
			.nickname(name)
			.thumbnail(picture)
			.role(MemberRole.MEMBER)
			.build();
	}

}
