package com.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.demo.domain.Member;

@Controller
@SessionAttributes("loginUser")
public class testController {

	@GetMapping("/login")
	public String testdo() {
		return "admin/logintest";
	}
	
    @PostMapping("/login.do")
    public String loginUser(@RequestParam("id") String id,
                            @RequestParam("usercode") int usercode,
                            @RequestParam("nickname") String nickname,
                            Model model) {

        // 임의의 Member 객체를 생성하여 로그인 정보를 저장합니다.
        Member loginUser = new Member();
        loginUser.setId(id);
        loginUser.setUsercode(usercode);
        loginUser.setNickname(nickname);

        // 세션에 loginUser를 저장합니다.
        model.addAttribute("loginUser", loginUser);

        // main.do로 리다이렉트합니다.
        return "redirect:/main.do";
    }
}