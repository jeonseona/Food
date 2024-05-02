package com.demo.service;

import java.util.List;

import com.demo.domain.Com_Board;
import com.demo.domain.Member;

public interface MypageService {
	
	// 개인정보 조회
	public Member getMember(String id);
	
	// 개인정보 변경
	public void changeInfo(Member vo);
	
	// 바디데이터 변경
	public void changeBody(Member vo);
	
	// 음식 선호도 및 알레르기 설정
	
	
	// 작성한 레시피 목록 보기
	public List<Com_Board> getRecipeList(String id);
	
}
