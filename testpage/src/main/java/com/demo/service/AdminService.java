package com.demo.service;

import java.util.List;

import com.demo.domain.AdminQnaBoard;
import com.demo.domain.AdminRecipeBoard;
import com.demo.domain.Member;
import com.demo.domain.foodRecipe;

public interface AdminService {
	
	// 관리자인지 체크(id 및 유저코드)
	int adminCheck(Member vo);
	
	// 관리자인 경우 모든 멤버의 정보조회
	public List<Member> getAllMemberList();
	
	// 관리자인 경우 모든 음식게시판 정보조회
	public List<AdminRecipeBoard> getAllFoodBoardList();
	
	// 관리자인 경우 모든 Q&A게시판 정보조회 
	public List<AdminQnaBoard> getAllQnaBoardList();
		
	// 관리자인 경우 모든 1:1문의게시판 정보조회
	public List<AdminQnaBoard> getAllAskBoardList();
	
	// 관리자인 경우 모든 레시피db 정보조회
	public List<foodRecipe> getRecipeDBListByName(String searchWord);
	public List<foodRecipe> getRecipeDBListByIndex(String searchWord);
	public foodRecipe getRecipeDBByIndex(int dbidx);
	
	// 게시글 저장
	void insertAdminRecipeBoard(AdminRecipeBoard vo);
	
	void insertAdminQnaBoard(AdminQnaBoard vo);
	
	// 게시글 수정
	public void updateAdminRecipeBoard(AdminRecipeBoard vo);
	
	public void updateAdminQnaBoard(AdminQnaBoard vo);
	
	public void updateAdminRecipeDB(foodRecipe vo);
	
	// 게시글 삭제
	public void deleteAdminRecipeBoard(long boardnum);
	
	public void deleteAdminQnaBoard(long boardnum);
	
	// 게시글 Boardnum을 통해서 게시글 조회
	public AdminRecipeBoard getByRecipeBoardnum(long boardnum);
	
	public AdminQnaBoard getByQnaBoardnum(long boardnum);
	
	// db에 recipe 등록
	void insertAdminRecipeDB(foodRecipe vo);
	
	// db의 데이터 삭제
	void deleteDBData(long dbidx);

	



	

	

	
}
