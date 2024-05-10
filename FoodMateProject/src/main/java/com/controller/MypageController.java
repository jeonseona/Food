package com.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.demo.domain.Com_Board_Detail;
import com.demo.domain.Member;
import com.demo.domain.Recommend_History;
import com.demo.dto.MemberData;
import com.demo.service.Com_Board_DetailService;
import com.demo.service.MemberDataService;
import com.demo.service.MemberService;
import com.demo.service.Recommend_HistoryService;

import jakarta.servlet.http.HttpSession;

@Controller
public class MypageController {

	@Autowired
	private MemberDataService mdService;
	@Autowired
	private Com_Board_DetailService cbdService;
	@Autowired
	private Recommend_HistoryService rhService;

	// 마이페이지 메인화면
	@GetMapping("/mypage")
	public String mypageView(HttpSession session, Model model) {
		Member loginUser = (Member)session.getAttribute("loginUser");
		
		if(loginUser == null) {
			return "redirect:/login";
		} else {
			
			return "mypage/mypageMain";
		}
	}
	
	// 내 정보 화면
	@GetMapping("/infoView")
	public String myInfoView(HttpSession session, Model model) {
		Member loginUser = (Member)session.getAttribute("loginUser");
		
		if(loginUser == null) {
			return "redirect:/login";
		} else {
			// 회원 ID를 사용하여 MemberData 조회
	        MemberData memberData = mdService.findByMember(loginUser);
	        model.addAttribute("member", loginUser);  // Member 테이블 정보
	        model.addAttribute("memberData", memberData);  // MemberData 테이블 정보
			model.addAttribute("********", "********");
		}
		return "mypage/infoView";
	}
	
	// 내 정보 수정 화면
	@GetMapping("/infoUpdate")
	public String infoUpdateView(HttpSession session, Model model) {
		Member loginUser = (Member)session.getAttribute("loginUser");
		
		if(loginUser == null) {
			return "redirect:/login";
		} else {
			model.addAttribute("loginUser", loginUser);
			return "mypage/infoUpdate";
		}
	}
	
	// 개인정보 수정하기
	@PostMapping("/update_info")
	public String infoUpdateAction(HttpSession session, MemberData vo) {
		Member loginUser = (Member)session.getAttribute("loginUser");
		
		if(loginUser == null) {
			return "redirect:/login";
		} else {
			vo.setMember(loginUser);	// 로그인한 회원수정
			mdService.changeInfo(vo);
			
			return "mypage/mypageMain";
		}
	}
	
	// 나의 호불호음식 및 알레르기 화면 ?????????????
	@GetMapping("/preferences")
	public String myFoodView(HttpSession session, MemberData vo) {
		Member loginUser = (Member)session.getAttribute("loginUser");
		
		if(loginUser == null) {
			return "redirect:/login";
		} else {
			
			return "mypage/preferences";
		}
	}
	
	// 나의 레시피 화면
	@GetMapping("/myRecipeList")
	public String myRecipeListView(HttpSession session, Model model,
			@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size) {
		Member loginUser = (Member)session.getAttribute("loginUser");
		
		if(loginUser == null) {
			return "redirect:/login";
		} else {
			List<Com_Board_Detail> recipeList = cbdService.getMyRecipe(loginUser.getId());
			
			model.addAttribute("recipeList", recipeList);
			
			return "mypage/myRecipeList";
		}
	}
	
	// 추천받은 음식 화면
	@GetMapping("/foodRecommend")
	public String foodRecommendView(HttpSession session, Model model,
			@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size) {
		Member loginUser = (Member)session.getAttribute("loginUser");
		
		if(loginUser == null) {
			return "redirect:/login";
		} else {
			List<Recommend_History> recommend = rhService.getMyRecommendHistory(loginUser.getId());
			model.addAttribute("recommend", recommend);
			
			return "mypage/recommendHistory";
		}
	}

}
