package com.demo.service;

import java.util.List;

import com.demo.domain.Com_Board_Detail;

public interface Com_Board_DetailService {

	// 회원별 작성한 레시피 목록(마이페이지용)
	public List<Com_Board_Detail> getMyRecipe(String id);
}
