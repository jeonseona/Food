//package com.demo.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.bind.support.SessionStatus;
//
//import com.demo.domain.Member;
//import com.demo.dto.MemberData;
//import com.demo.service.MemberService;
//
//@Controller
//@SessionAttributes("loginUser")
//public class MemberController {
//
//    @Autowired
//    private MemberService memberService;
//
//    // 약정화면 표시
//    @GetMapping("/contract")
//    public String contractView() {
//    	
//        return "member/contract";
//    }
//   
//    @PostMapping("/contract")
//    public String signUp(@RequestParam(name = "agree", required = false) String agree) {
//        if ("on".equals(agree)) { // 약관에 동의한 경우
//            return "member/join"; // 회원가입 페이지로 이동
//        } else {
//            return "member/contract"; // 약관 동의 페이지로 다시 이동
//        }
//    }
//    
//   @PostMapping("/join_form")
//   public String joinForm() {
//	   
//	   return "member/join";
//   }
//
//    // 회원가입 화면 표시 (GET 요청)
//    @GetMapping("/join")
//    public String joinView() {
//        return "member/contract";
//    }
//
//    // 로그인 화면 표시
//    @GetMapping("/login_form")
//    public String loginView() {
//        return "member/login";
//    }
//    
//    // 아이디 비밀번호찾기 화면
//    @GetMapping("/findIdAndPassword")
//    public String findIdAndPasswordView() {
//        return "member/findIdAndPassword";
//    }
//  
// // 아이디 찾기 처리
// 	@PostMapping("/find_id")
// 	public String findIdAction(Member vo, Model model) {
// 		
// 		Member member = memberService.getIdByNameEmail(vo.getName(), vo.getEmail());
// 		
// 		System.out.println("member=" + member);
// 		if (member != null) { //아이디 조회 성공
// 			model.addAttribute("message", 1);
// 			model.addAttribute("id", member.getId());
// 		} else {
// 			model.addAttribute("message", -1);
// 		}
// 		
// 		return "member/findResult";
// 	}
// 	
// 	
//	// 비밀번호 찾기
//	@PostMapping("/find_pwd")
//	public String findPwdAction(Member vo, Model model) {
//		
//		Member member = memberService.getPwdByIdNameEmail(vo.getId(), vo.getName(), vo.getEmail());
//		
//		if (member != null) { // 사용자 조회 성공
//			model.addAttribute("message", 1);
//			model.addAttribute("id", member.getId());
//		} else {
//			model.addAttribute("message", -1);
//		}
//		
//		return "member/findPwdResult";
//	}
// 	
// 	
//    
//    // 로그인
//    @PostMapping("/login")
//    public String loginAction(Member vo, Model model) {
//        String url = "";        
//        if (memberService.loginID(vo) == 1) { 
//            model.addAttribute("loginUser", memberService.loginID(vo));
//            url = "redirect:main";
//        } else {
//            url = "member/login_fail";
//        }
//        return url;
//    }
//
//    // 로그아웃 처리
//    @GetMapping("/logout")
//    public String logout(SessionStatus status) {
//        status.setComplete(); // 세션 완료 상태로 설정
//        return "redirect:/login_form"; // 로그아웃 후 로그인 화면으로 리다이렉트
//    }
//    
//   
//    // ID 중복 확인 처리
//    @GetMapping("/id_check_form")
//    public String idCheckView(Member vo, Model model) {
//        int result = memberService.confirmID(vo.getId());
//        
//        model.addAttribute("message", result);
//        model.addAttribute("id", vo.getId());
//        
//        return "member/idcheck";
//    }
//
//    // 이메일 중복 확인 처리
//    @GetMapping("/email_check_form")
//    public String emailCheckView(Member vo, Model model) {
//        int result = memberService.confirmEmail(vo.getEmail());
//        model.addAttribute("message", result);
//        model.addAttribute("email", vo.getEmail());
//        return "member/emailcheck";
//    }
//    
//    // 회원가입 처리 (POST 요청)
//    @PostMapping("/join")
//    public String joinAction(MemberData vo) {
//        memberService.insertMember(vo);
//        return "redirect:/main"; // 회원가입 후 메인 페이지로 리다이렉트
//    }
//    
//    
//        
//}