package com.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.domain.Com_Board_Detail;
import com.demo.persistence.Com_Board_DetailRepository;

@Service
public class Com_Board_DetailServiceImpl implements Com_Board_DetailService {

	@Autowired
	private Com_Board_DetailRepository BoardDetailRepo;
	
	@Override
	public List<Com_Board_Detail> getMyRecipe(String id) {
		
		return BoardDetailRepo.getMyRecipeListById(id);
	}

}
