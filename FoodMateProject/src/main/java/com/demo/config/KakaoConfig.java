package com.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KakaoConfig {

	@Value("${kakao.api.key}")
	private String apiKey;
	
	@Value("${kakao.redirect_uri}")
	private String redirectUri;
	
	@Value("${kakao.client.id}")
	private String clientId;
	
	@Value("${kakao.client.secret}")
	private String clientSecret;

	public String getApiKey() {
		return apiKey;
	}

	public String getRedirectUri() {
		return redirectUri;
	}

	public String getClientId() {
		return clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}
	
	
}
