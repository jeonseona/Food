package com.demo.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.demo.domain.Com_Board_Detail;
import com.demo.domain.MemberData;
import com.demo.domain.Recommend_History;
import com.demo.domain.WeightRecord;
import com.demo.service.Com_Board_DetailService;
import com.demo.service.MemberService;
import com.demo.service.Recommend_HistoryService;
import com.demo.service.WeightRecordService;

import jakarta.servlet.http.HttpSession;

@Controller
public class MypageController {

	@Autowired
	private MemberService memberService;
	@Autowired
	private Com_Board_DetailService boardService;
	@Autowired
	private Recommend_HistoryService recommendService;
	@Autowired
	private WeightRecordService recordService;

	
	// 마이페이지 메인화면
	@GetMapping("/mypage")
	public String mypageView(HttpSession session, Model model) {
		MemberData loginUser = (MemberData)session.getAttribute("loginUser");
		
		if(loginUser == null) {
			return "redirect:/login";
		} else {
			
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
			
	        model.addAttribute("memberData", loginUser);
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
	
	// 개인정보 수정
	@PostMapping("/update_info")
	public String infoUpdateAction(HttpSession session, MemberData vo) {
		MemberData loginUser = (MemberData)session.getAttribute("loginUser");
		
		if(loginUser == null) {
			return "redirect:/login";
		} else {
			// 로그인한 회원수정
			memberService.changeInfo(vo);
			
			return "mypage/infoView";
		}
	}
	
	// 닉네임 중복 확인 처리
	@GetMapping("/nickname_check_form")
	public String nicknameCheckView(MemberData vo, Model model) {
		int result = memberService.confirmNickname(vo.getNickname());
		
		model.addAttribute("message", result);
		model.addAttribute("nickname", vo.getNickname());
		
		return "mypage/nickcheck";
	}
	
	// 바디데이터 수정 화면
	@GetMapping("bodyUpdate")
	public String bodyUpdateView(HttpSession session) {
		MemberData loginUser = (MemberData)session.getAttribute("loginUser");
		
		if(loginUser == null) {
			return "redirect:/login";
		} else {
			return "mypage/bodyUpdate";
		}
	}
	
	// 바디데이터 수정
	@PostMapping("/update_body")
	public String bodyUpdateAction(HttpSession session, MemberData vo) {
		MemberData loginUser = (MemberData)session.getAttribute("loginUser");
		
		if(loginUser == null) {
			return "redirect:/login";
		} else {
			// 로그인한 회원 바디데이터 수정
			vo.setId(loginUser.getId());
			memberService.changeBodyData(vo);
			
			return "mypage/infoView";
		}
	}
	
	// 나의 체중 변화 그래프 화면
	@GetMapping("/myWeightChart")
	public String myWeightChartView(HttpSession session, Model model) {
		MemberData loginUser = (MemberData)session.getAttribute("loginUser");
		
		if(loginUser == null) {
			return "redirect:/login";
		} else {
			return "mypage/myWeightChart";
		}
	}
	// 체중변화 값 저장하기
	@PostMapping("/weight_record")
	@ResponseBody
	public String changeWeight(HttpSession session, @RequestParam("re_date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date reDate, 
	                           @RequestParam("re_weight") Double reWeight) {
	    MemberData loginUser = (MemberData) session.getAttribute("loginUser");
	    
	    if (loginUser == null) {
	        return "redirect:/login";
	    } else {
	        WeightRecord weightRecord = new WeightRecord();
	        weightRecord.setRe_date(reDate);
	        weightRecord.setRe_weight(reWeight);
	        weightRecord.setMember(loginUser);
	        recordService.saveWeightRecord(weightRecord);
	        return "체중 기록이 저장되었습니다.";
	    }
	}
	// 저장된 값들로 체중변화 차트 그리기
	@GetMapping("/getRecordChart")
	@ResponseBody
	public Map<String, Object> myWeightRecordChart(HttpSession session) {
	    Map<String, Object> response = new HashMap<>();
	    MemberData loginUser = (MemberData) session.getAttribute("loginUser");

	    if (loginUser == null) {
	        response.put("redirect", "/login");
	        return response;
	    } else {
	        // 조회한 값들 중 최근 일주일의 데이터를 전송
	        List<WeightRecord> recentWeekRecords = recordService.getRecentWeekRecords(loginUser.getId());
	        // 조회한 값들 중 최근 한달의 데이터를 전송
	        List<WeightRecord> recentMonthRecords = recordService.getRecentMonthRecords(loginUser.getId());

	        // 최근 일주일과 한달의 체중 기록을 Map에 담아 반환
	        response.put("weeklyData", recentWeekRecords);
	        response.put("monthlyData", recentMonthRecords);
	        return response;
	    }
	}
	
	
	// 나의 레시피 화면
	@GetMapping("/myRecipeList")
	public String myRecipeListView(HttpSession session, Model model) {
		MemberData loginUser = (MemberData)session.getAttribute("loginUser");
		
		if(loginUser == null) {
			return "redirect:/login";
		} else {
			List<Com_Board_Detail> recipeList = boardService.getMyRecipe(loginUser.getId());
			
			model.addAttribute("recipeList", recipeList);
			
			return "mypage/myRecipeList";
		}
	}
	
	// 추천받은 음식 화면
	@GetMapping("/foodRecommend")
	public String foodRecommendView(HttpSession session, Model model) {
		MemberData loginUser = (MemberData)session.getAttribute("loginUser");
		
		if(loginUser == null) {
			return "redirect:/login";
		} else {
			List<Recommend_History> recommend = recommendService.getMyRecommendHistory(loginUser.getId());
			model.addAttribute("recommend", recommend);
			
			return "mypage/recommendHistory";
		}
	}
	
	
	
	
	/* 추가 기능용 자리
	 * 아직 구상중..
	@PostMapping("/myGoal")
	public String adviceOfGoal(HttpSession session, Model model) {
		MemberData loginUser = (MemberData)session.getAttribute("loginUser");
		
		if(loginUser == null) {
			return "redirect:login";
		} else {
			
			
			return "mypage/advice";
		}
	}
	
	// 1:1문의 관련 안쓰는 기능
	@GetMapping("/myInquiry")
	public String myInquiryView(HttpSession session, Model model) {
		MemberData loginUser = (MemberData)session.getAttribute("loginUser");
		
		if(loginUser == null) {
			return "redirect:/login";
		} else {
			List<askBoard> inquiry = customerService.getMyInquiry(loginUser.getId());
			
			model.addAttribute("InquiryList", inquiry);
			return "mypage/myInquiry";
		}
	}
	
	
	
	
	*/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
