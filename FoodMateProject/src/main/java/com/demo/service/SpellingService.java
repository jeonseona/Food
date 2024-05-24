package com.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.demo.dto.NaverSpellingApi;
import com.google.gson.Gson;

@Service
public class SpellingService {
    // 맞춤법 검사기
    
    @Value("${naver.client.id}")
    private String id;
    
    @Value("${naver.client.secret}")
    private String secret;
    
    private String API_URL = "https://openapi.naver.com/v1/search/errata.json";
    private RestTemplate restTemplate = new RestTemplate();
    private Gson gson = new Gson();
    private RetryTemplate spelling_retryTemplate;
    private  NaverSpellingApi spelling;
    
    private String result;
    
    public SpellingService(RetryTemplate spelling_retryTemplate, NaverSpellingApi spelling) {
    	this.spelling_retryTemplate = spelling_retryTemplate;
        this.spelling = spelling;
    }
    
    
    
}
