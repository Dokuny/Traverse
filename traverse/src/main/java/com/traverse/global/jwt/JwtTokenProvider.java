package com.traverse.global.jwt;


import static com.traverse.global.jwt.exception.JwtErrorCode.EXPIRED_TOKEN;
import static com.traverse.global.jwt.exception.JwtErrorCode.INVALID_TOKEN;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.traverse.command.domain.member.entity.Member;
import com.traverse.command.domain.member.entity.enums.MemberRole;
import com.traverse.global.jwt.exception.JwtException;
import com.traverse.global.jwt.security.LoginMember;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

	private final JwtProps props;
	private final ObjectMapper objectMapper;

	private static final String CLAIM_EMAIL = "email";
	private static final String CLAIM_VENDOR = "vendor";
	private static final String CLAIM_NICKNAME = "nickname";
	private static final String CLAIM_ROLE = "role";
	private static final String CLAIM_PROFILE_IMG = "thumbnail";

	// 액세스 토큰 발급
	public String issueAccessToken(Member member) {

		Claims claims = Jwts.claims()
			.id(String.valueOf(member.getId()))
			.add(CLAIM_EMAIL, member.getEmail())
			.add(CLAIM_PROFILE_IMG, member.getThumbnail())
			.add(CLAIM_NICKNAME, member.getNickname())
			.add(CLAIM_ROLE, member.getRole())
			.build();

		return issueToken(claims, props.accessExpiration(), props.accessKey());
	}

	// 리프레시 토큰 발급
	public String issueRefreshToken() {
		return issueToken(null, props.refreshExpiration(), props.refreshKey());
	}

	private String issueToken(Claims claims, Duration expiration, String secretKey) {
		Date now = new Date();

		return Jwts.builder()
			.claims(claims)
			.issuedAt(now)
			.expiration(new Date(now.getTime() + expiration.toMillis()))
			.signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
			.compact();
	}

	// 토큰을 회원 정보로 파싱
	public LoginMember parseAccessToken(String accessToken) {
		Claims payload = parseToken(accessToken, props.accessKey());

		return LoginMember.builder()
			.id(Long.valueOf(payload.getId()))
			.nickname(payload.get(CLAIM_NICKNAME, String.class))
			.email(payload.get(CLAIM_EMAIL, String.class))
			.profileImageUrl(payload.get(CLAIM_PROFILE_IMG, String.class))
			.role(MemberRole.fromName(payload.get(CLAIM_ROLE, String.class)))
			.build();
	}

	public void parseRefreshToken(String refreshToken) {
		parseToken(refreshToken, props.refreshKey());
	}

	private Claims parseToken(String token, String secretKey) {
		Claims payload;

		try {
			payload = Jwts.parser()
				.verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
				.build()
				.parseSignedClaims(token).getPayload();

		} catch (ExpiredJwtException e) {
			throw new JwtException(EXPIRED_TOKEN);
		} catch (MalformedJwtException | SecurityException | IllegalArgumentException e) {
			throw new JwtException(INVALID_TOKEN);
		}

		return payload;
	}

	public Member parseAccessTokenByBase64(String accessToken) {
		String payload = accessToken.split("\\.")[1];

		String decodePayload = new String(Base64.getUrlDecoder().decode(payload));

		BasicJsonParser jsonParser = new BasicJsonParser();

		Map<String, Object> map = jsonParser.parseMap(decodePayload);

		return Member.builder()
			.id(Long.valueOf((String)(map.get("jti"))))
			.email((String)map.get(CLAIM_EMAIL))
			.nickname((String)map.get(CLAIM_NICKNAME))
			.thumbnail((String)map.get(CLAIM_PROFILE_IMG))
			.role(MemberRole.fromName((String)map.get(CLAIM_ROLE)))
			.build();
	}

}
