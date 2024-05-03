package com.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RecommendedFoodController {

    @GetMapping("/RecommendedFood")
    public String showRecommendedFoodPage() {
        return "RecommendedFood"; // RecommendedFood.html 파일의 이름 (뷰 이름)
    }
}