package com.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.domain.foodRecipe;
import com.demo.service.AdminService;

@RestController
public class RecommendFoodController {

    @Autowired
    private AdminService foodService; // 서비스 또는 리포지토리 객체
    
    @GetMapping("/getNutrientData")
    public ResponseEntity<Map<String, Object>> getNutrientData(@RequestParam("idx") int idx) {
        foodRecipe food = foodService.getFoodByIndex(idx); // 음식 데이터를 가져오는 서비스 메소드
        if (food != null) {
            Map<String, Object> nutrientData = new HashMap<>();
            nutrientData.put("idx", food.getIdx());
            nutrientData.put("name", food.getName());
            nutrientData.put("carbohydrates", food.getCarbohydratecontent());
            nutrientData.put("protein", food.getProteincontent());
            nutrientData.put("fat", food.getFatcontent());
            nutrientData.put("saturatedfat", food.getSaturatedfatcontent());
            nutrientData.put("cholesterol", food.getCholesterolcontent());
            nutrientData.put("sodium", food.getSodiumcontent());
            nutrientData.put("sugar", food.getSugarcontent());
            
            return ResponseEntity.ok(nutrientData);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
