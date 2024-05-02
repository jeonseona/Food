package com.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.demo.domain.Com_Board_Detail;
import com.demo.domain.Reply;
import com.demo.service.ReplyService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ReplyController {
	
	@Autowired
	ReplyService replyService;
	
	//댓글 출력
	@GetMapping("/reply_list")
	public String getboard_list(int seq, Model model) {
		List<Reply> ReplyList = replyService.getReplyList();
		model.addAttribute("replyList", ReplyList);
		return "comboard/BoardDetail";
		}
	
	
	//댓글 등록
	@GetMapping("/reply_save")
	public String insertReply(Reply vo, HttpSession session) {
		Com_Board_Detail loginUser = (Com_Board_Detail) session.getAttribute("loginUser");
		
		if (loginUser == null) { 
			return "member/login"; // 로그인페이지 넣기 (아직 안넣음!)
		}else {
			replyService.insertReply(vo);			
			return "redirect:BoardDetail";
			}
	}
	
	//댓글 수정
	@GetMapping("/reply_update")
	public String updateReply(Reply vo, HttpSession session) {
		Com_Board_Detail loginUser = (Com_Board_Detail) session.getAttribute("loginUser");
		
		if (loginUser == null) { 
			return "member/login"; // 로그인페이지 넣기 (아직 안넣음!)
		}else if(loginUser.getMember_data().getId() == vo.getMember_data().getId()){
			return "본인이 작성한 댓글만 수정가능합니다.";
		}else {
			replyService.updateReply(vo);			
			return "redirect:BoardDetail";
	}
	}
	
	//댓글 삭제 
	@GetMapping("/reply_delete")
	public String deleteReply(Reply vo, HttpSession session) {
		Com_Board_Detail loginUser = (Com_Board_Detail) session.getAttribute("loginUser");
		
		if (loginUser == null) { 
			return "member/login"; // 로그인페이지 넣기 (아직 안넣음!)
		}else if(loginUser.getMember_data().getId() == vo.getMember_data().getId()){
			return "본인이 작성한 댓글만 삭제가능합니다.";
		}else {
			replyService.deleteReply(vo);			
			return "redirect:BoardDetail";
	}
	}
	
	

}
