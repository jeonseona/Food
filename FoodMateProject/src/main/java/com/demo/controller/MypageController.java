package com.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.demo.domain.Com_Board_Detail;
import com.demo.dto.MemberData;
import com.demo.service.Com_Board_DetailService;
import com.demo.service.MemberDataService;

import jakarta.servlet.http.HttpSession;

@Controller
public class MypageController {

	@Autowired
	private MemberDataService mdService;
	
	@Autowired
	private Com_Board_DetailService cbdService;
	
//	@Autowired
//	private 추천기록	// 나의 추천받은 기록 목록용
	
	
	// 마이페이지 메인화면
	@GetMapping("/t_mypage")
	public String mypageView(HttpSession session, Model model) {
		MemberData loginUser = (MemberData)session.getAttribute("loginUser");
		
		if(loginUser == null) {
			return "member/login";
		} else {
			// memberService.changeInfo(loginUser);
			return "mypage/mypageMain";
		}
	}
	
	// 내 정보 화면
	@GetMapping("/infoView")
	public String myInfoView(HttpSession session, Model model, MemberData dto) {
		MemberData loginUser = (MemberData)session.getAttribute("loginUser");
		
		if(loginUser == null) {
			return "member/login";
		} else {
			String secrit = "********";
			//	기본키가 no_data라서 id로 조회가 안됨.
			MemberData member = mdService.getMember(loginUser.getNo_data());
			model.addAttribute("loginUser", member);
			model.addAttribute("********", secrit);
		}
		return "mypage/infoView";
	}
	
	// 내 정보 수정 화면
	@GetMapping("/infoUpdate")
	public String infoUpdateView(HttpSession session, Model model) {
		MemberData loginUser = (MemberData)session.getAttribute("loginUser");
		
		if(loginUser == null) {
			return "member/login";
		} else {
			model.addAttribute("loginUser", loginUser);
			return "mypage/infoUpdate";
		}
	}
	
	// 개인정보 수정하기
	@PostMapping("/update_info")
	public String infoUpdateAction(HttpSession session, MemberData dto) {
		MemberData loginUser = (MemberData)session.getAttribute("loginUser");
		
		if(loginUser == null) {
			return "member/login";
		} else {
			
			mdService.changeInfo(dto);
			
			return "mypage/mypageMain";
		}
	}
	
	// 나의 호불호음식 및 알레르기 화면 ?????????????
	@GetMapping("/preferences")
	public String myFoodView(HttpSession session, MemberData vo) {
		MemberData loginUser = (MemberData)session.getAttribute("loginUser");
		
		if(loginUser == null) {
			return "member/login";
		} else {
			
			return "t_mypage/xxxxxxxx";
		}
	}
	
	// 나의 레시피 화면
	@GetMapping("/myRecipeList")
	public String myRecipeListView(HttpSession session, Model model) {
		MemberData loginUser = (MemberData)session.getAttribute("loginUser");
		
		if(loginUser == null) {
			return "member/login";
		} else {
			List<Com_Board_Detail> recipetList = cbdService.getMyRecipe(loginUser.getId());
			
			model.addAttribute("recipeList", recipetList);
			
			return "mypage/myRecipeList";
		}
	}
	
	// 추천받은 음식 화면
	@GetMapping("/foodRecommend")
	public String foodRecommendView(HttpSession session, Model model) {
		MemberData loginUser = (MemberData)session.getAttribute("loginUser");
		
		if(loginUser == null) {
			return "member/login";
		} else {
			//List<> recommend = Service.getRecommendHistory(loginUser.getId());
			
			//model.addAttribute("recommend", recommend);
			
			return "mypage/recommendHistory";
		}
	}
	

}
