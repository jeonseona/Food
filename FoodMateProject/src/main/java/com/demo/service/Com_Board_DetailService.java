package com.demo.service;

import org.springframework.data.domain.Page;

import com.demo.domain.Com_Board_Detail;

public interface Com_Board_DetailService {

	// 회원별 작성한 레시피 목록(마이페이지용)
	public Page<Com_Board_Detail> getMyRecipe(String id, int page, int size);
}
