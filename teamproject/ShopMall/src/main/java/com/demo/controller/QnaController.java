package com.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.demo.domain.Member;
import com.demo.domain.Qna;
import com.demo.service.QnaService;

import jakarta.servlet.http.HttpSession;

@Controller
public class QnaController {
	
	@Autowired QnaService qnaservice;
	
	@GetMapping("qna_list")
	public String getQnaListView(HttpSession session, Model model) {
		Member loginUser = (Member) session.getAttribute("loginUser");

		if (loginUser == null) { 
			return "member/login"; 
		} else {
			List<Qna> qnaList = qnaservice.getListQna(loginUser.getId());
			model.addAttribute("qnaList", qnaList);
			return "qna/qnaList";
		}
	}
	
	
	@GetMapping("qna_view")
	public String getQnaView(Qna vo, HttpSession session, Model model) {
		Member loginUser = (Member) session.getAttribute("loginUser");

		if (loginUser == null) { 
			return "member/login"; 
		} else {
			Qna qna = qnaservice.getQna(vo.getQseq());
			
			model.addAttribute("qnaVO" , qna);
			return "qna/qnaView";
		}
	}
	
	//QNA 글쓰기 이동
	@GetMapping("/qna_write_form")
	public String getQnaWriteView(HttpSession session) {
		Member loginUser = (Member) session.getAttribute("loginUser");

		if (loginUser == null) { 
			return "member/login"; 
		} else {
			return "qna/qnaWrite";
		}
	}
	
	//QNA 글쓰기
	@PostMapping("qna_write")
	public String insertQna(Qna vo, HttpSession session) {
		Member loginUser = (Member) session.getAttribute("loginUser");

		if (loginUser == null) { 
			return "member/login"; 
		} else {
			vo.setMember(loginUser);
			qnaservice.insertQna(vo);		
			
			return "redirect:qna_list";
	}
	
	
	}
}
