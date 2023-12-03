package com.traverse.global.jwt;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class RefreshTokenRepository {

	private final RedisTemplate<String, String> redisTemplate;
	private final JwtProps props;

	private static final String KEY_PREFIX = "refreshToken::";

	public void save(String refreshToken, String accessToken) {
		redisTemplate.opsForValue()
			.set(KEY_PREFIX + refreshToken, accessToken, props.refreshExpiration());
	}

	public Optional<String> find(String refreshToken) {
		String token = redisTemplate.opsForValue().get(KEY_PREFIX + refreshToken);

		return Optional.ofNullable(token);
	}

	public void delete(String refreshToken) {
		redisTemplate.delete(KEY_PREFIX + refreshToken);
	}

}
