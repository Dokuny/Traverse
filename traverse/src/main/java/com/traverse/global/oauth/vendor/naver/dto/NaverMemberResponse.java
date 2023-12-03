package com.traverse.global.oauth.vendor.naver.dto;



import static com.traverse.global.oauth.vendor.enums.OAuthDomain.NAVER;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.traverse.command.domain.member.entity.Member;
import com.traverse.command.domain.member.entity.enums.MemberRole;
import com.traverse.command.domain.member.entity.embedded.OAuth;


@JsonNaming(SnakeCaseStrategy.class)
public record NaverMemberResponse(
	String resultcode,
	String message,
	Response response
) {

	public Member toDomain() {
		return Member.builder()
			.oAuth(new OAuth(String.valueOf(response.id), NAVER))
			.email(response.email)
			.nickname(response.nickname)
			.thumbnail(response.profileImage)
			.role(MemberRole.MEMBER)
			.build();
	}


	@JsonNaming(SnakeCaseStrategy.class)
	public record Response(
		String id,
		String nickname,
		String name,
		String email,
		String gender,
		String age,
		String birthday,
		String profileImage,
		String birthyear,
		String mobile
	) {
	}


}
