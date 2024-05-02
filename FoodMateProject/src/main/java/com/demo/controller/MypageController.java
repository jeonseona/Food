package com.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.demo.domain.Member;
import com.demo.service.MypageService;

import jakarta.servlet.http.HttpSession;

@Controller
public class MypageController {

//	@Autowired
//	private MemberService memberService;
	@Autowired
	private MypageService mypageService;
	
	// 마이페이지 메인
	@GetMapping("/mypage")
	public String getMypage(HttpSession session, Model model) {
	    Member loginUser = (Member) session.getAttribute("loginUser"); // 세션에 저장된 로그인 유저

	    if (loginUser == null) { // 비로그인 상태
	        return "redirect:/login"; // 로그인 페이지로 리다이렉션
	    } else { // 로그인 상태
	        // 로그인 유저의 기타 정보
	        model.addAttribute("loginUser", loginUser);
	        
	        // 음식 선호도 및 알레르기 정보도 로딩 
//	        List<Food> foods = foodService.getFoodsPreferences(loginUser.getId());
//	        model.addAttribute("foods", foods);
	        
	        // 사용자 ID로 검색된 레시피 목록 가져오기
//	        List<Recipe> recipeList = recipeService.getRecipeList(loginUser.getId());
//	        model.addAttribute("recipeList", recipeList);
	        
	        return "mypage/MypageMain"; // 마이페이지 메인 뷰 이름 반환
	    }
	}
	
	// 개인정보 수정처리
	@PostMapping("/update_info")
	public String updateInfo(Member vo) {
		mypageService.changeInfo(vo);
		return "mypage/MypageMain";
	}
	
	// 바디데이터 수정처리
	@PostMapping("/update_body")
	public String updateBody(Member vo) {
		mypageService.changeBody(vo);
		return "mypage/MypageMain";
	}
	
	// 음식 선호도 및 알레르기
	
	
	
	
	
	
	
	
	
}
