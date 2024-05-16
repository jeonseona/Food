package com.demo.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.demo.dto.KeywordData;

@RestController
public class HomeController {

    @PostMapping("/processKeywords")
    public String processKeywords(@RequestBody KeywordData keywordData) {
        // 선택된 키워드와 제외할 재료를 받아서 처리
        String selectedKeywords = keywordData.getSelectedKeywords();
        String excludeIngredients = keywordData.getExcludeIngredients();
        String dietCalory = keywordData.getDietCalory();
        String nutrientChoice = keywordData.getNutrientChoice();
        
        // Python 스크립트에 데이터 전달
        RestTemplate restTemplate = new RestTemplate();
        String pythonUrl = "http://localhost:8080/processKeywords";  // Python 스크립트가 실행되는 주소
        String result = restTemplate.postForObject(pythonUrl, keywordData, String.class);

        return result;
    }
}