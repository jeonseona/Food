package com.demo.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.dto.KeywordData;

@RestController
public class HomeController {

    @PostMapping("/processKeywords")
    public String processKeywords(@RequestBody KeywordData keywordData) {
        // 선택된 키워드와 제외할 재료를 받아서 처리
        String selectedKeywords = keywordData.getSelectedKeywords();
        String excludeIngredients = keywordData.getExcludeIngredients();
        
        // ProcessStream으로 값을 전달하고 처리
        // processStream.process(selectedKeywords, excludeIngredients);
        
        return "Keywords processed successfully";
    }
}