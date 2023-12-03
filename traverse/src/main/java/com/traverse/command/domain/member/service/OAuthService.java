package com.traverse.command.domain.member.service;


import com.traverse.command.domain.member.entity.Member;
import com.traverse.command.domain.member.repository.MemberRepository;
import com.traverse.command.domain.member.dto.LoginResponse;
import com.traverse.command.domain.member.dto.MemberInfo;
import com.traverse.command.domain.member.dto.Tokens;
import com.traverse.global.jwt.JwtTokenProvider;
import com.traverse.global.jwt.RefreshTokenRepository;
import com.traverse.global.jwt.exception.JwtErrorCode;
import com.traverse.global.jwt.exception.JwtException;
import com.traverse.global.oauth.component.OAuthCodeUrlProvider;
import com.traverse.global.oauth.component.OAuthMemberClient;
import com.traverse.global.oauth.vendor.enums.OAuthDomain;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class OAuthService {

	private final OAuthCodeUrlProvider oAuthCodeUrlProvider;
	private final OAuthMemberClient oAuthMemberClient;
	private final MemberRepository memberRepository;
	private final JwtTokenProvider jwtTokenProvider;
	private final RefreshTokenRepository refreshTokenRepository;

	@Transactional(readOnly = true)
	public String provideAuthCodeRequestUrl(OAuthDomain oauthDomain) {
		return oAuthCodeUrlProvider.provide(oauthDomain);
	}

	public LoginResponse login(OAuthDomain oauthDomain, String authCode) {
		Member oauthMember = oAuthMemberClient.fetch(oauthDomain, authCode);

		Member member = memberRepository.findByoAuth(oauthMember.getOAuth())
			.orElseGet(() -> memberRepository.save(oauthMember));

		String accessToken = jwtTokenProvider.issueAccessToken(member);
		String refreshToken = jwtTokenProvider.issueRefreshToken();

		log.info("== {} 회원에 대한 token 발급 : {} / {}", member.getId(), accessToken, refreshToken);

		refreshTokenRepository.save(refreshToken, accessToken);

		return LoginResponse.builder()
			.tokens(Tokens.builder()
				.accessToken(accessToken)
				.refreshToken(refreshToken)
				.build())
			.memberInfo(MemberInfo.builder()
				.id(member.getId())
				.email(member.getEmail())
				.thumbnail(member.getThumbnail())
				.nickname(member.getNickname())
				.build())
			.build();
	}

	public LoginResponse refreshToken(Tokens tokens) {

		String accessToken = refreshTokenRepository.find(tokens.refreshToken())
			.orElseThrow(() -> new JwtException(JwtErrorCode.INVALID_TOKEN));

		jwtTokenProvider.parseRefreshToken(tokens.refreshToken());

		if (!accessToken.equals(tokens.accessToken())) {
			throw new JwtException(JwtErrorCode.INVALID_TOKEN);
		}

		Member member = jwtTokenProvider.parseAccessTokenByBase64(accessToken);

		String newAccessToken = jwtTokenProvider.issueAccessToken(member);
		String newRefreshToken = jwtTokenProvider.issueRefreshToken();

		refreshTokenRepository.delete(tokens.refreshToken());
		refreshTokenRepository.save(newRefreshToken, newAccessToken);

		Tokens newToken = Tokens.builder()
			.accessToken(newAccessToken)
			.refreshToken(newRefreshToken)
			.build();

		return LoginResponse.builder()
			.tokens(newToken)
			.memberInfo(MemberInfo.builder()
				.id(member.getId())
				.email(member.getEmail())
				.thumbnail(member.getThumbnail())
				.nickname(member.getNickname())
				.build())
			.build();
	}

}
