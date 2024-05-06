package com.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.demo.domain.Member;
import com.demo.service.MemberService;

import jakarta.servlet.http.HttpSession;

@Controller
public class MypageController {

	@Autowired
	private MemberService memberService;
	
//	@Autowired
//	private 커뮤니티게시판	// 내가 작성한 레시피용
//	
//	@Autowired
//	private 추천기록	// 나의 추천받은 기록 목록용
	
	
	// 마이페이지 메인화면
	@GetMapping("/t_mypage")
	public String mypageView(HttpSession session, Model model) {
		Member loginUser = (Member)session.getAttribute("loginUser");
		
		if(loginUser == null) {
			return "member/login";
		} else {
			// memberService.changeInfo(loginUser);
			return "mypage/mypageMain";
		}
	}
	
	// 내 정보 화면
	@GetMapping("/infoView")
	public String myInfoView(HttpSession session, Model model, Member vo) {
		Member loginUser = (Member)session.getAttribute("loginUser");
		
		if(loginUser == null) {
			return "member/login";
		} else {
			String secrit = "********";
			Member member = memberService.getMember(loginUser.getId());
			model.addAttribute("loginUser", member);
			model.addAttribute("********", secrit);
		}
		return "mypage/infoView";
	}
	
	// 내 정보 수정 화면
	@GetMapping("/infoUpdate")
	public String infoUpdateView(HttpSession session, Model model) {
		Member loginUser = (Member)session.getAttribute("loginUser");
		
		if(loginUser == null) {
			return "member/login";
		} else {
			model.addAttribute("loginUser", loginUser);
			return "mypage/infoUpdate";
		}
	}
	
	// 개인정보 수정하기
	@PostMapping("/update_info")
	public String infoUpdateAction(HttpSession session, Member vo) {
		Member loginUser = (Member)session.getAttribute("loginUser");
		
		if(loginUser == null) {
			return "member/login";
		} else {
			
			memberService.changeInfo(vo);
			
			return "mypage/mypageMain";
		}
	}
	
	// 나의 호불호음식 및 알레르기 화면 ?????????????
	@GetMapping("/preferences")
	public String myFoodView(HttpSession session, Member vo) {
		Member loginUser = (Member)session.getAttribute("loginUser");
		
		if(loginUser == null) {
			return "member/login";
		} else {
			
			return "t_mypage/xxxxxxxx";
		}
	}
	
	// 나의 레시피 화면
	@GetMapping("/myRecipeList")
	public String myRecipeListView(HttpSession session, Model model) {
		Member loginUser = (Member)session.getAttribute("loginUser");
		
		if(loginUser == null) {
			return "member/login";
		} else {
			List<> recipetList = Service.getRecipeList(loginUser.getId());
			
			model.addAttribute("recipeList", recipeList);
			
			return "t_mypage/MyRecipeList";
		}
	}
	
	// 추천받은 음식 화면
	@GetMapping("/foodRecommend")
	public String foodRecommendView(HttpSession session, Model model) {
		Member loginUser = (Member)session.getAttribute("loginUser");
		
		if(loginUser == null) {
			return "member/login";
		} else {
			List<> recommend = Service.getRecommendHistory(loginUser.getId());
			
			model.addAttribute("recommend", recommend);
			
			return "t_mypage/recommendHistory";
		}
	}
	

}
