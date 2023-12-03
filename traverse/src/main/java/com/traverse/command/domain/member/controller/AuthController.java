package com.traverse.command.domain.member.controller;


import com.traverse.command.domain.member.dto.LoginResponse;
import com.traverse.command.domain.member.dto.Tokens;
import com.traverse.command.domain.member.service.OAuthService;
import com.traverse.global.common.Message;
import com.traverse.global.oauth.vendor.enums.OAuthDomain;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthController {

	private final OAuthService oAuthService;

	@SneakyThrows
	@GetMapping("/{oAuthDomain}")
	public ResponseEntity<Message> redirectOAuthCodeRequestUrl(
		@PathVariable OAuthDomain oAuthDomain,
		HttpServletResponse response
	) {

		String redirectUrl = oAuthService.provideAuthCodeRequestUrl(oAuthDomain);

		response.sendRedirect(redirectUrl);

		return ResponseEntity.ok().body(Message.success());
	}

	@GetMapping("/{oAuthDomain}/login")
	public ResponseEntity<Message<LoginResponse>> login(
		@PathVariable OAuthDomain oAuthDomain,
		@RequestParam("code") String authCode
	) {
		LoginResponse result = oAuthService.login(oAuthDomain, authCode);
		return ResponseEntity.ok().body(Message.success(result));
	}

	@PostMapping("/refresh")
	public ResponseEntity<Message<LoginResponse>> refreshToken(@RequestBody Tokens tokens) {
		return ResponseEntity.ok().body(Message.success(oAuthService.refreshToken(tokens)));
	}
}
