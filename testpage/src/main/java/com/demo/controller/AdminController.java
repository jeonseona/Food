package com.demo.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.demo.domain.AdminQnaBoard;
import com.demo.domain.AdminRecipeBoard;
import com.demo.domain.Member;
import com.demo.domain.foodRecipe;
import com.demo.service.AdminService;

@Controller
@SessionAttributes("loginUser")
public class AdminController {
	
	@Autowired
	AdminService adminService;
	
	@GetMapping("/main.do")
    public String mainView(@ModelAttribute("loginUser") Member loginUser, Model model) {
        // loginUser가 존재하는지 확인하고 adminCheck 수행
        if (loginUser != null) {
            adminService.adminCheck(loginUser);
        } else {
        	return "admin/admin_main";    // 초기 로그인 화면으로 이동 **********************(수정필요)
        }
        
        List<AdminQnaBoard> askBoardList = adminService.getAllAskBoardList();
        model.addAttribute("askBoardList", askBoardList);

        return "admin/admin_main";
    }
	
	// 회원 관리 페이지를 누르면 회원 정보 전체 조회
	@GetMapping("/memberList.do")
	public String memberInfo(@ModelAttribute("loginUser") Member loginUser, Model model) {
		if (loginUser != null) {
            adminService.adminCheck(loginUser);
        } else {
        	return "admin/admin_main";    // 초기 로그인 화면으로 이동 **********************(수정필요)
        }
		
		List<Member> memberList = adminService.getAllMemberList();
		model.addAttribute("memberList", memberList);
		
		return "admin/boardPage/admin_userlist";  // 회원 정보 페이지로 이동
	}
	
	// 관리자 음식레시피 조회
	@GetMapping("/admin_food.do")
	public String foodBoard(@ModelAttribute("loginUser") Member loginUser, Model model) {
	    if (loginUser == null) {
	        return "redirect:/main.do";  // 초기 로그인 화면으로 리다이렉션
	    }

	    adminService.adminCheck(loginUser);

	    List<AdminRecipeBoard> foodBoardList = adminService.getAllFoodBoardList();
	    model.addAttribute("foodBoardList", foodBoardList);

	    return "admin/boardPage/admin_foodboard";  // 관리자 음식레시피 게시판으로 이동
	}
	
	// 관리자 Q&A 게시판
	@GetMapping("/admin_QnA.do")
	public String QnABoard(@ModelAttribute("loginUser") Member loginUser, Model model) {
		if (loginUser != null) {
            adminService.adminCheck(loginUser);
        } else {
        	return "redirect:/main.do";    // 초기 로그인 화면으로 이동 **********************(수정필요)
        }
		
		List<AdminQnaBoard> qnaBoardList= adminService.getAllQnaBoardList();
		model.addAttribute("qnaBoardList", qnaBoardList);
			
		return "admin/boardPage/admin_qnaboard";  // 관리자 Q&A 게시판으로 이동
	}
		
	// 관리자 1:1문의 게시판
	@GetMapping("/admin_ask.do")
	public String AskBoard(@ModelAttribute("loginUser") Member loginUser, Model model) {
		if (loginUser != null) {
            adminService.adminCheck(loginUser);
        } else {
        	return "main.do";    // 초기 로그인 화면으로 이동 **********************(수정필요)
        }
		
		List<AdminQnaBoard> askBoardList = adminService.getAllAskBoardList();
		model.addAttribute("askBoardList", askBoardList);
			
		return "admin/boardPage/admin_askboard";  // 관리자 1:1문의 게시판으로 이동
	}
	
	@GetMapping("/qnaRegister.do")
	public String qnaRegiView(@ModelAttribute("loginUser") Member loginUser) {
		if (loginUser != null) {
            adminService.adminCheck(loginUser);
        } 
		return "admin/boardForm/qnaForm";
	}
	
	@GetMapping("/foodRegister.do")
	public String foodRegiView(@ModelAttribute("loginUser") Member loginUser) {
		if (loginUser != null) {
            adminService.adminCheck(loginUser);
        } 
		return "admin/boardForm/foodForm";
	}
	
	@PostMapping("/qnaReg.do")  // 나머지 등록과정도 동일
	public String qnaRegister(@ModelAttribute("loginUser") Member loginUser, AdminQnaBoard vo) {
		// 로그인 정보를 Member타입으로 받아와서 vo객체에 비어있는 정보에 insert해야함
		// 로그인 영역에서 처리하는 방식대로 개선
		// 프로그램 실행해보고 vo객체 나머지가 제대로 들어오는지 확인***************
		adminService.adminCheck(loginUser);
		adminService.insertAdminQnaBoard(vo);
		
		return "redirect:/admin_QnA.do";
	}
	
	@PostMapping("/foodReg.do")  // 나머지 등록과정도 동일
	public String foodRegister(@ModelAttribute("loginUser") Member loginUser, AdminRecipeBoard vo) {
		// 로그인 정보를 Member타입으로 받아와서 vo객체에 비어있는 정보에 insert해야함
		// 로그인 영역에서 처리하는 방식대로 개선
		vo.setUserid(loginUser.getId());
		vo.setNickname(loginUser.getNickname());
		// 프로그램 실행해보고 vo객체 나머지가 제대로 들어오는지 확인***************
		adminService.adminCheck(loginUser);
		adminService.insertAdminRecipeBoard(vo);
		
		return "redirect:/admin_food.do";
	}
	
	@PostMapping("/qnaEdit.do")
	public String editQna(@ModelAttribute("loginUser") Member loginUser, @RequestParam("qnaSelect") int boardnum, Model model) {
		adminService.adminCheck(loginUser);
	    AdminQnaBoard qna = adminService.getByQnaBoardnum(boardnum);

	    model.addAttribute("qna", qna);
	   
	    return "admin/boardForm/qnaEditForm";
	}
	
	@PostMapping("/foodEdit.do")
	public String editFood(@ModelAttribute("loginUser") Member loginUser, @RequestParam("foodSelect") int boardnum, Model model) {
		adminService.adminCheck(loginUser);
	    AdminRecipeBoard food = adminService.getByRecipeBoardnum(boardnum);

	    model.addAttribute("food", food);
	   
	    return "admin/boardForm/foodEditForm";
	}
	
	@PostMapping("/qnaUpdate.do")  // 나머지 등록과정도 동일
	public String qnaUpdate(@ModelAttribute("loginUser") Member loginUser, @RequestParam("qnaNum") long boardnum, 
							AdminQnaBoard vo) {
		// 로그인 정보를 Member타입으로 받아와서 vo객체에 비어있는 정보에 insert해야함
		// 로그인 영역에서 처리하는 방식대로 개선
		vo.setQna_boardnum(boardnum);
		
		adminService.adminCheck(loginUser);
		adminService.updateAdminQnaBoard(vo);
//		adminService.updateFoodReceipe(vo);    보드에 저장된 vo데이터 중 foodRecipe로 저장되어야 하는 정보 선택하여 쿼리로 실행(Regis, Update, Delete)
		
		return "redirect:/admin_QnA.do";
	}
	
	@PostMapping("/foodUpdate.do")  // 나머지 등록과정도 동일
	public String foodUpdate(@ModelAttribute("loginUser") Member loginUser, @RequestParam("foodNum") int boardnum, 
							AdminRecipeBoard vo) {
		// 로그인 정보를 Member타입으로 받아와서 vo객체에 비어있는 정보에 insert해야함
		// 로그인 영역에서 처리하는 방식대로 개선
		vo.setRecipe_boardnum(boardnum);
		vo.setUserid(loginUser.getId());
		vo.setNickname(loginUser.getNickname());
		vo.setEditdate(new Date());
		// 프로그램 실행해보고 vo객체 나머지가 제대로 들어오는지 확인***************
		adminService.adminCheck(loginUser);
		adminService.updateAdminRecipeBoard(vo);
		
		return "redirect:/admin_food.do";
	}
	
	@PostMapping("/qnaDelete.do")
    public String deleteQna(@ModelAttribute("loginUser") Member loginUser, @RequestParam("qnaSelect") int boardnum) {
        // boardnum에 해당하는 Q&A 게시글을 삭제합니다.
		adminService.adminCheck(loginUser);
        adminService.deleteAdminQnaBoard(boardnum);

        // 삭제 후 Q&A 목록 페이지로 리다이렉트합니다.
        return "redirect:/admin_QnA.do";
    }
	
	@PostMapping("/foodDelete.do")
    public String deleteFood(@ModelAttribute("loginUser") Member loginUser, @RequestParam("foodSelect") int boardnum) {
        // boardnum에 해당하는 Q&A 게시글을 삭제합니다.
		adminService.adminCheck(loginUser);
        adminService.deleteAdminRecipeBoard(boardnum);

        // 삭제 후 Q&A 목록 페이지로 리다이렉트합니다.
        return "redirect:/admin_food.do";
    }
	
	@GetMapping("/recipeReg.do")
	public String recipeReg(@ModelAttribute("loginUser") Member loginUser, @RequestParam("foodSelect") int boardnum, Model model) {
		adminService.adminCheck(loginUser);
		
		AdminRecipeBoard vo = adminService.getByRecipeBoardnum(boardnum);
		model.addAttribute("recipe", vo);
		
		return "admin/boardForm/recipeReg";
	}
	
	@PostMapping("/insertDB.do")
    public String insertRecipe(@ModelAttribute("loginUser") Member loginUser, foodRecipe vo) {
        // boardnum에 해당하는 Q&A 게시글을 삭제합니다.
		adminService.adminCheck(loginUser);
		
        adminService.insertAdminRecipeDB(vo);

        // 삭제 후 Q&A 목록 페이지로 리다이렉트합니다.
        return "redirect:/admin_food.do";
    }
	
	@GetMapping("/dataManager.do")
	public String dataManager(@ModelAttribute("loginUser") Member loginUser, Model model) {
		// loginUser가 존재하는지 확인하고 adminCheck 수행
        if (loginUser != null) {
            adminService.adminCheck(loginUser);
        } else {
        	return "admin/admin_main";    // 초기 로그인 화면으로 이동 **********************(수정필요)
        }
        
        model.addAttribute("dbList", new ArrayList<foodRecipe>());
        model.addAttribute("message", "");
        return "admin/boardPage/admin_db";
		
	}
	
	@PostMapping("/getSearch.do")
	public String getSearch(@ModelAttribute("loginUser") Member loginUser, @RequestParam("searchField") String searchField, @RequestParam("searchWord") String searchWord, Model model) {
		// loginUser가 존재하는지 확인하고 adminCheck 수행
        if (loginUser != null) {
            adminService.adminCheck(loginUser);
        } else {
        	return "admin/admin_main";    // 초기 로그인 화면으로 이동 **********************(수정필요)
        }
        
        List<foodRecipe> dbList;
        
        if (searchField.equals("index")) {
            dbList = adminService.getRecipeDBListByIndex(searchWord);
        } else {
        	if(searchWord.equals("")) {
        		searchWord=null;
        	}
            dbList = adminService.getRecipeDBListByName(searchWord);
        }
        
        model.addAttribute("dbList", dbList);
        
        if (dbList.isEmpty()) {
            model.addAttribute("message", "검색 결과가 조회되지 않습니다.");
        }
        
        return "admin/boardPage/admin_db";  // 검색 결과를 포함한 페이지로 이동
	}
	
	@PostMapping("/dbDelete.do")
    public String deleteDB(@ModelAttribute("loginUser") Member loginUser, @RequestParam("dbidx") int dbidx) {
		if (loginUser != null) {
            adminService.adminCheck(loginUser);
        } else {
        	return "admin/admin_main";    // 초기 로그인 화면으로 이동 **********************(수정필요)
        }
        adminService.deleteDBData(dbidx);

        // 삭제 후 Q&A 목록 페이지로 리다이렉트합니다.
        return "redirect:/admin_QnA.do";
    }
	
	@GetMapping("/dbEdit.do")
	public String dbEdit(@ModelAttribute("loginUser") Member loginUser, @RequestParam("dbidx") int dbidx, Model model) {
		if (loginUser != null) {
            adminService.adminCheck(loginUser);
        } else {
        	return "redirect:/admin/admin_main";    // 초기 로그인 화면으로 이동 **********************(수정필요)
        }
		
		foodRecipe recipe = adminService.getRecipeDBByIndex(dbidx);
		model.addAttribute("recipe", recipe);
		
		return "admin/boardForm/dbEdit";
		
	}
	
	@PostMapping("/updateDB.do")
	public void dbUpdate(@ModelAttribute("loginUser") Member loginUser, foodRecipe vo) {
		
		adminService.adminCheck(loginUser);
		adminService.updateAdminRecipeDB(vo);
		
	}
}
