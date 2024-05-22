package com.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.domain.SatisfactionSurvey;
import com.demo.dto.SatisfactionDTO;
import com.demo.persistence.SatisfactionRepository;
import com.demo.service.SatisfactionService;

@Service
public class SatisfactionServiceImpl implements SatisfactionService {

    @Autowired
    private SatisfactionRepository satisfactionRepository;

    @Override
    public void saveSatisfaction(SatisfactionDTO satisfactionDTO) {
        // DTO를 엔티티로 변환하여 저장
        SatisfactionSurvey satisfactionSurvey = new SatisfactionSurvey();
        satisfactionSurvey.setSatisfaction((String) satisfactionDTO.getSatisfaction());
        satisfactionRepository.save(satisfactionSurvey);
    }
}
