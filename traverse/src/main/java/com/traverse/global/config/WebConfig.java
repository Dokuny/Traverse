package com.traverse.global.config;

import com.traverse.global.oauth.vendor.enums.OAuthDomainConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {




	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new OAuthDomainConverter());
	}


}
