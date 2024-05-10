package com.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.domain.Member;
import com.demo.domain.Product;
import com.demo.domain.ProductComment;
import com.demo.service.CommentService;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/comments")
@RestController
public class CommentController {
	
	@Autowired
	CommentService commentservice;
	
	@GetMapping(value="/list")
	public Map<String, Object> commentList(
		@RequestParam(value="pseq")int pseq, ProductComment vo){
		Map<String, Object> commentInfo = new HashMap<>();
		
		List<ProductComment> commentList = commentservice.getCommentList(pseq);
		
		commentInfo.put("commentList", commentList);
		commentInfo.put("commentCount", commentList.size());
		
		return commentInfo;
	}
	
	
	@PostMapping(value="/save", produces="application/json; charset=UTF-8")
	public Map<String, Object> saveCommentAction(ProductComment vo,
			@RequestParam(value="pseq")int pseq,
			HttpSession session){
		
		Map<String, Object> map = new HashMap<>();
		Member loginUser = (Member) session.getAttribute("loginUser");
		
		if (loginUser == null) {
			map.put("result", "not_logedin");

		} else {
			vo.setMember(loginUser);
			Product p = new Product();
			p.setPseq(pseq);
			vo.setProduct(p);
			
			commentservice.saveComment(vo);
			
			map.put("result", "success");
	}
		return map;
	}
	
	
	@PostMapping(value="/count")
	public int countComment(@RequestParam(value="pseq")int pseq) {
		return commentservice.getCountCommentList(pseq);
	}

}
