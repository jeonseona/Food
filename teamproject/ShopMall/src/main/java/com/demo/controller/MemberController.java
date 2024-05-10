package com.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.demo.domain.Address;
import com.demo.domain.Member;
import com.demo.service.MemberService;

@Controller
@SessionAttributes("loginUser")
public class MemberController {

	@Autowired
	MemberService memberService;

	@GetMapping("/login_form")
	public String login_form() {
		return "member/login";
	}

	// 약정화면 표시
	@GetMapping("/contract")
	public String contractView() {
		return "member/contract";
	}

	// 회원가입 화면 표시
	@PostMapping("/join_form")
	public String joinView() {
		return "member/join";
	}

	// 사용자인증 (로그인)
	@PostMapping("/login")
	public String loginAction(Member vo, Model model) {
		String url = "";

		if (memberService.LoginID(vo) == 1) { // 정상사용자
			model.addAttribute("loginUser", memberService.getMember(vo.getId()));
			url = "redirect:main";
		} else {
			url = "member/login_fail";
		}
		return url;
	}

	// 로그아웃
	@GetMapping("/logout")
	public String logoutAction(SessionStatus session) {
		session.setComplete();

		return "member/login";
	}

	// ID 중복확인처리
	@GetMapping("/id_check_form")
	public String idCheckView(Member vo, Model model) {
		int result = memberService.confirmID(vo.getId());

		model.addAttribute("id", vo.getId());
		model.addAttribute("message", result);
		return "member/idcheck";
	}

	// 회원가입처리
	@PostMapping("/join")
	public String joinAction(@RequestParam(value = "addr1", defaultValue = "") String addr1,
			@RequestParam(value = "addr2", defaultValue = "") String addr2, Member vo) {

		vo.setAddress(addr1 + "" + addr2);
		memberService.insertMember(vo);

		return "member/login";
	}

	// 주소찾기 화면표시
	@GetMapping("/find_zip_num")
	public String findZipNumView() {
		return "member/findZipNum";
	}

	// 동이름으로 주소찾기 처리
	@PostMapping("/find_zip_num")
	public String findZipNumAction(Address vo, Model model) {
		List<Address> addrlist = memberService.getAddressByDong(vo.getDong());
		model.addAttribute("addressList", addrlist);

		return "member/findZipNum";
	}

	// 아이디 비밀번호 찾기 화면표시
	@GetMapping("find_id_form")
	public String findIdView() {
		return "member/findIdAndPassword";
	}

	// 아이디 찾기
	@PostMapping("find_id")
	public String findById(Member vo, Model model) {
		Member member = memberService.getIdByNameEmail(vo.getName(), vo.getEmail());
		if (member != null) { // 아이디 조회 성공
			model.addAttribute("message", 1);
			model.addAttribute("id", member.getId());
		} else {
			model.addAttribute("message", -1);
		}

		return "member/findResult";
	}

	@PostMapping("find_pwd")
	public String findByPwd(Member vo, Model model) {
		Member member = memberService.getPwdByIdNameEmail(vo.getId(), vo.getName(), vo.getEmail());

		if (member != null) { // 비번 조회 성공
			model.addAttribute("message", 1);
			model.addAttribute("pwd", member.getPwd());
			model.addAttribute("id", member.getId());
		} else {
			model.addAttribute("message", -1);
		}
		return "member/findResultPwd";
	}

	@PostMapping("new_pwd")
	public String updatePwdAction(Member vo) {
		System.out.println("비밀번호 변경: " + vo);
		memberService.updateByPwd(vo);

		return "member/changePwdOk";
	}
	
	
	
	
	

}
