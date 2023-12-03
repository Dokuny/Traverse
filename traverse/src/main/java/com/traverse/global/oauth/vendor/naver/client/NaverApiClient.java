package com.traverse.global.oauth.vendor.naver.client;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;


import com.traverse.global.oauth.vendor.naver.dto.NaverMemberResponse;
import com.traverse.global.oauth.vendor.naver.dto.NaverToken;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;


public interface NaverApiClient {

	// 인증 코드 발송 후, 액세스 토큰 받아오기
	@PostExchange(url = "https://nid.naver.com/oauth2.0/token", contentType = APPLICATION_FORM_URLENCODED_VALUE)
	NaverToken fetchToken(@RequestParam(value = "params") MultiValueMap<String, String> params);

	// 액세스 토큰으로 회원 정보 받아오기
	@GetExchange("https://openapi.naver.com/v1/nid/me")
	NaverMemberResponse fetchMember(@RequestHeader(name = AUTHORIZATION) String bearerToken);


}
