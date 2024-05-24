package com.demo.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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

import com.demo.dto.Foodcal;
import com.demo.dto.NaverSpellingApi;
import com.demo.persistence.FoodcalRepository;
import com.google.gson.Gson;

@Service
public class ChatService {

    @Value("${huggingface.api.token}")
    private String apiToken;

    private static final String API_URL = "https://api-inference.huggingface.co/models/skt/ko-gpt-trinity-1.2B-v0.5";
    private final RestTemplate restTemplate = new RestTemplate();
    private final Gson gson = new Gson();
    private final RetryTemplate retryTemplate;
    private final FoodcalRepository foodcalRepository;

    //응답 맵
    private final Map<String, String> predefinedResponses = new HashMap<>();

    // 생성자
    public ChatService(RetryTemplate retryTemplate, FoodcalRepository foodcalRepository) {
    	this.retryTemplate = retryTemplate;
        this.foodcalRepository = foodcalRepository; // 수정: 주입된 FoodcalRepository 사용
        initializePredefinedResponses();
    }

    
    private void initializePredefinedResponses() {
        predefinedResponses.put("FoodMate란 ?", "회원님들이 정보를 입력하시면 적절한 요리메뉴와 그 요리의 레시피까지 제공하는 웹 사이트입니다.");
        predefinedResponses.put("나만의 레시피 ?", "메뉴에서 Community를 통해서 회원님들만의 레시피를 공개할 수 있습니다.");
        predefinedResponses.put("메뉴 추천", "메인페이지에서 시작하기 버튼을 누르면 메뉴를 추천받을 수 있습니다.");
        predefinedResponses.put("불만", "Customer Service에서 1:1문의를 작성 해 주세요");
        predefinedResponses.put("이전에 추천받았던 내용이 궁금해", "메뉴 마이페이지에 들어가시면 이전 기록을 확인하실 수 있습니다.");
        
    }

    @Retryable(value = {org.springframework.web.client.HttpServerErrorException.ServiceUnavailable.class}, maxAttempts = 5, backoff = @Backoff(delay = 5000))
    public String getChatbotResponse(String userMessage) {
        
        if (predefinedResponses.containsKey(userMessage)) {
            return predefinedResponses.get(userMessage);
        }
        
        Optional<Foodcal> foodcal = foodcalRepository.findByFoodname(userMessage);
        if (foodcal.isPresent()) {
            Foodcal food = foodcal.get();
            return String.format("%s\n: %sg당 \n칼로리: %s,\n탄수화물: %s,\n단백질: %s,\n지방: %s,\n당류: %s,\n나트륨: %s,\n콜레스테롤: %s",
                    food.getFoodname(), food.getG(), food.getKcal(), food.getCarbohydrate(), food.getProtein(), food.getProvince(), food.getSugars(), food.getSalt(), food.getCholesterol());
        }

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
