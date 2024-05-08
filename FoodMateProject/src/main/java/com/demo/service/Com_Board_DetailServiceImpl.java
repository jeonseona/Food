package com.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.demo.domain.Com_Board_Detail;
import com.demo.persistence.Com_Board_DetailRepository;

@Service
public class Com_Board_DetailServiceImpl implements Com_Board_DetailService {

	@Autowired
	private Com_Board_DetailRepository BoardDetailRepo;
	@Override
	public Page<Com_Board_Detail> getMyRecipe(String id, int page, int size) {
		Pageable pageable = PageRequest.of(page-1, size, Direction.ASC, "d_regdate");
		return BoardDetailRepo.getMyRecipeListById(id, pageable);
	}

}
