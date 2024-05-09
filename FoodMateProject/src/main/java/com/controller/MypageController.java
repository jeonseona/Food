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
import com.demo.domain.Recommend_History;
import com.demo.dto.MemberData;
import com.demo.service.Com_Board_DetailService;
import com.demo.service.MemberDataService;
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

	@GetMapping("/")
	public String homePage(Model model) {
		// 홈페이지에 필요한 데이터를 모델에 추가하고, 뷰 이름을 반환합니다.
		model.addAttribute("welcomeMessage", "건강한 식단을 추천해드릴게요!");
		return "main"; // 여기서 "main"는 타임리프 템플릿 파일의 이름입니다.
	}
		
	// 마이페이지 메인화면
	@GetMapping("/mypage")
	public String mypageView(HttpSession session, Model model) {
		MemberData loginUser = (MemberData)session.getAttribute("loginUser");
		
		if(loginUser == null) {
			return "redirect:/login";
		} else {
			// memberService.changeInfo(loginUser);
			return "mypage/mypageMain";
		}
	}
	
	// 내 정보 화면
	@GetMapping("/infoView")
	public String myInfoView(HttpSession session, Model model) {
		MemberData loginUser = (MemberData)session.getAttribute("loginUser");
		
		if(loginUser == null) {
			return "redirect:/login";
		} else {
			//	기본키가 no_data라서 id로 조회가 안됨.
			MemberData member = mdService.getMember(loginUser.getNo_data());
			model.addAttribute("loginUser", member);
			model.addAttribute("********", "********");
		}
		return "mypage/infoView";
	}
	
	// 내 정보 수정 화면
	@GetMapping("/infoUpdate")
	public String infoUpdateView(HttpSession session, Model model) {
		MemberData loginUser = (MemberData)session.getAttribute("loginUser");
		
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
		MemberData loginUser = (MemberData)session.getAttribute("loginUser");
		
		if(loginUser == null) {
			return "redirect:/login";
		} else {
			
			mdService.changeInfo(vo);
			
			return "mypage/mypageMain";
		}
	}
	
	// 나의 호불호음식 및 알레르기 화면 ?????????????
	@GetMapping("/preferences")
	public String myFoodView(HttpSession session, MemberData vo) {
		MemberData loginUser = (MemberData)session.getAttribute("loginUser");
		
		if(loginUser == null) {
			return "redirect:/login";
		} else {
			
			return "t_mypage/xxxxxxxx";
		}
	}
	
	// 나의 레시피 화면 (테스트 x)
	@GetMapping("/myRecipeList")
	public String myRecipeListView(HttpSession session, Model model,
			@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size) {
		MemberData loginUser = (MemberData)session.getAttribute("loginUser");
		
		if(loginUser == null) {
			return "redirect:/login";
		} else {
			Page<Com_Board_Detail> pagetList = cbdService.getMyRecipe(loginUser.getId(), page, size);
			List<Com_Board_Detail> recipeList = pagetList.getContent();
			model.addAttribute("recipeList", recipeList);
			
			return "mypage/myRecipeList";
		}
	}
	
	// 추천받은 음식 화면 (테스트 x)
	@GetMapping("/foodRecommend")
	public String foodRecommendView(HttpSession session, Model model,
			@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size) {
		MemberData loginUser = (MemberData)session.getAttribute("loginUser");
		
		if(loginUser == null) {
			return "redirect:/login";
		} else {
			Page<Recommend_History> recommend = rhService.getMyRecommendHistory(loginUser.getId(), page, size);
			
			model.addAttribute("recommend", recommend);
			
			return "mypage/recommendHistory";
		}
	}

}
