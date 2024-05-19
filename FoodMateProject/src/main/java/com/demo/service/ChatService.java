package com.demo.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

@Service
public class ChatService {

    @Value("${huggingface.api.token}")
    private String apiToken;

    private static final String API_URL = "https://api-inference.huggingface.co/models/skt/ko-gpt-trinity-1.2B-v0.5";
    private final RestTemplate restTemplate = new RestTemplate();
    private final Gson gson = new Gson();
    private final RetryTemplate retryTemplate;

    // 사전 정의된 응답 맵
    private final Map<String, String> predefinedResponses = new HashMap<>();

    // 생성자
    public ChatService(RetryTemplate retryTemplate) {
        this.retryTemplate = retryTemplate;
        initializePredefinedResponses();
    }

    
    private void initializePredefinedResponses() {
        predefinedResponses.put("비밀번호", "비밀번호 재설정을 위해서는 다음 단계를 따르세요...");
        predefinedResponses.put("주문 상태", "주문 상태는 [링크]에서 확인하실 수 있습니다...");
        
    }

    @Retryable(value = {org.springframework.web.client.HttpServerErrorException.ServiceUnavailable.class}, maxAttempts = 5, backoff = @Backoff(delay = 5000))
    public String getChatbotResponse(String userMessage) {
        // 사전 정의된 응답이 있는 경우 해당 응답 반환
        if (predefinedResponses.containsKey(userMessage)) {
            return predefinedResponses.get(userMessage);
        }

        // 사전 정의된 응답이 없는 경우 Hugging Face API 호출
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiToken);
        headers.set("Content-Type", "application/json");

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("inputs", userMessage);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> response = retryTemplate.execute(context -> restTemplate.exchange(API_URL, HttpMethod.POST, entity, String.class));
            Map<String, Object>[] chatResponses = gson.fromJson(response.getBody(), Map[].class);
            if (chatResponses.length > 0 && chatResponses[0].containsKey("generated_text")) {
                return chatResponses[0].get("generated_text").toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "죄송합니다. 이해하지 못했습니다.";
    }
}
