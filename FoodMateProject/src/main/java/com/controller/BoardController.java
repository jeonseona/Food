package com.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.demo.domain.Com_Board_Detail;
import com.demo.service.Com_Board_DetailService;

import jakarta.servlet.http.HttpSession;


@Controller
public class BoardController {
	
	@Autowired
	Com_Board_DetailService Board_DetailService;
	
	@Value("${com.demo.upload.path}")
	private String uploadPath;
	
	
	//게시글 목록 조회
	@GetMapping("/board_list")
	public String getboard_list(@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "size", defaultValue = "10") int size, Model model) {
		Page<Com_Board_Detail> pageList = Board_DetailService.getAllCom_Board(page, size);
		List<Com_Board_Detail> boardList = pageList.getContent();
		
		model.addAttribute("boardList", boardList);
		model.addAttribute("pageInfo", pageList);
		return "comboard/BoardList";
		} 
	
	
	//게시글 상세정보 조회
	@GetMapping("/com_board_detail")
	public String getCom_Board_DetailView(int seq, Model model) {
		Com_Board_Detail com_board = Board_DetailService.getCom_Board_Datail(seq);
		model.addAttribute("Com_Board_DetailVO" , com_board);
		return "comboard/BoardDetail";
	}
	
	//글 수정
	@PostMapping("/board_update")
	public String updateCom_Board(Com_Board_Detail vo, HttpSession session) {
		Com_Board_Detail loginUser = (Com_Board_Detail) session.getAttribute("loginUser");
		
//		if (loginUser == null) { 
//			return "member/login"; // 로그인페이지 넣기 (아직 안넣음!)
//		}else if(loginUser.getMember_data().getId() == vo.getMember_data().getId()){
//			return "본인이 작성한 글만 수정가능합니다.";
//		}else {
//			vo.setCnt(vo.getCnt());
			vo.setD_regdate(vo.getD_regdate());
			Board_DetailService.updateBoard(vo);			
			return "redirect:BoardDetail";
//	}
	}
	
	//글 삭제
	@GetMapping("/board_delete")
	public String deleteCom_Board(Com_Board_Detail vo, HttpSession session) {
		Com_Board_Detail loginUser = (Com_Board_Detail) session.getAttribute("loginUser");
		
//		if (loginUser == null) { 
//			return "member/login"; // 로그인페이지 넣기 (아직 안넣음!)
//		}else if(loginUser.getMember_data().getId() == vo.getMember_data().getId()){
//			return "본인이 작성한 글만 삭제가능합니다.";
//		}else {
			Board_DetailService.deleteBoard(vo);			
			return "redirect:BoardList";
//	}
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
		
//		if (loginUser == null) { 
//			return "member/login"; // 로그인페이지 넣기 (아직 안넣음!)
//		}else {
			Board_DetailService.insertBoard(vo);			
			return "redirect:BoardList";
//			}
	
	}
	
	 //제목, 작성자로 검색
	@GetMapping("/board_search")
	public String getSearchByTitle(String title, String Id, String Keyword, Model model) {
		
		if (title == null) {
			List<Com_Board_Detail> boardList = Board_DetailService.getCom_BoardByWriter(Keyword);
			model.addAttribute("boardList", boardList);
		}else {
			List<Com_Board_Detail> boardList = Board_DetailService.getCom_BoardByTitle(Keyword);
			model.addAttribute("boardList", boardList);
		}

		return "comboard/BoardList";
	}
	
	
}

















