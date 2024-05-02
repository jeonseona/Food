package com.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.demo.domain.Com_Board_Detail;
import com.demo.service.Com_BoardService;
import com.demo.service.Com_Board_DetailService;

import jakarta.servlet.http.HttpSession;

@Controller
public class BoardController {
	
	@Autowired
	Com_BoardService BoardService;
	@Autowired
	Com_Board_DetailService Board_DetailService;	
	
	//게시글 목록 조회
	@GetMapping("/board_list")
	public String getboard_list(Model model) {
		List<Com_Board_Detail> com_boardList = Board_DetailService.getCom_Board();
		model.addAttribute("boardList", com_boardList);
		return "comboard/BoardList";
		}
	
	
	//게시글 상세정보 조회
	@PostMapping("/com_board_detail")
	public String getCom_Board_DetailView(Com_Board_Detail vo, Model model) {
		Com_Board_Detail com_board = Board_DetailService.getCom_Board_Datail(vo.getSeq());
		model.addAttribute("Com_Board_DetailVO" , com_board);
		return "comboard/BoardDetail";
	}
	
	//글 수정
	@PostMapping("/board_update")
	public String updateCom_Board(Com_Board_Detail vo, HttpSession session) {
		Com_Board_Detail loginUser = (Com_Board_Detail) session.getAttribute("loginUser");
		
		if (loginUser == null) { 
			return "member/login"; // 로그인페이지 넣기 (아직 안넣음!)
		}else if(loginUser.getMember_data().getId() == vo.getMember_data().getId()){
			return "본인이 작성한 글만 수정가능합니다.";
		}else {
			vo.setCnt(vo.getCnt());
			vo.setD_regdate(vo.getD_regdate());
			Board_DetailService.updateBoard(vo);			
			return "redirect:BoardDetail";
	}
	}
	
	//글 삭제
	@GetMapping("/board_delete")
	public String deleteCom_Board(Com_Board_Detail vo, HttpSession session) {
		Com_Board_Detail loginUser = (Com_Board_Detail) session.getAttribute("loginUser");
		
		if (loginUser == null) { 
			return "member/login"; // 로그인페이지 넣기 (아직 안넣음!)
		}else if(loginUser.getMember_data().getId() == vo.getMember_data().getId()){
			return "본인이 작성한 글만 삭제가능합니다.";
		}else {
			Board_DetailService.deleteBoard(vo);			
			return "redirect:BoardList";
	}
	}
	
	//글쓰기 페이지로 이동
	@GetMapping("/board_write")
	public String getCom_Board_DetailWriteView(HttpSession session) {
		Com_Board_Detail loginUser = (Com_Board_Detail) session.getAttribute("loginUser");

//		if (loginUser == null) { 
//			return "member/login"; // 로그인페이지 넣기 (아직 안넣음!)
//		} else {
			return "comboard/BoardWrite";
//		}
	}
	
	//글쓰기 등록
	@PostMapping("/board_write_t")
	public String insertCom_Board(Com_Board_Detail vo, HttpSession session) {
		Com_Board_Detail loginUser = (Com_Board_Detail) session.getAttribute("loginUser");
		
		if (loginUser == null) { 
			return "member/login"; // 로그인페이지 넣기 (아직 안넣음!)
		}else {
			Board_DetailService.insertBoard(vo);			
			return "redirect:BoardList";
			}
	
	}
	
	// 제목으로 검색
	// 글쓴이로 검색
	
}

















