package com.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("/main")
    public String mainPage(Model model) {
        model.addAttribute("welcomeMessage", "환영합니다! FoodMate와 함께 건강한 식단을 관리해 보세요.");
        return "main";
    }
}
