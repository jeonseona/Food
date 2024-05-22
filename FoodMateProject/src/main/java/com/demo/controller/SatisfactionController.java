package com.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.dto.SatisfactionDTO;
import com.demo.service.SatisfactionService;

@RestController
public class SatisfactionController {

    @Autowired
    private SatisfactionService satisfactionService;

    @PostMapping("/submitSatisfaction.do")
    public ResponseEntity<Void> submitSatisfaction(@RequestBody SatisfactionDTO satisfactionDTO) {
        satisfactionService.saveSatisfaction(satisfactionDTO);
        return ResponseEntity.ok().build();
        
        
    }
}