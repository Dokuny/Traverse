package com.traverse.global.oauth.vendor.google.client;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;


import com.traverse.global.oauth.vendor.google.dto.GoogleMemberResponse;
import com.traverse.global.oauth.vendor.google.dto.GoogleToken;
import com.traverse.global.youtube.dto.YoutubeSearchResponse;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;


public interface GoogleApiClient {

	// 인증 코드 발송 후, 액세스 토큰 받아오기
	@PostExchange(url = "https://oauth2.googleapis.com/token", contentType = APPLICATION_FORM_URLENCODED_VALUE)
	GoogleToken fetchToken(@RequestParam(value = "params") MultiValueMap<String, String> params);

	// 액세스 토큰으로 회원 정보 받아오기
	@GetExchange("https://www.googleapis.com/userinfo/v2/me")
	GoogleMemberResponse fetchMember(@RequestHeader(name = AUTHORIZATION) String bearerToken);


	@GetExchange("https://www.googleapis.com/youtube/v3/search")
	YoutubeSearchResponse searchYoutube(@RequestParam(value = "params") MultiValueMap<String, String> params);



}
