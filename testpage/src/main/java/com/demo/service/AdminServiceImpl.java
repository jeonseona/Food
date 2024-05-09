package com.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.domain.AdminQnaBoard;
import com.demo.domain.AdminRecipeBoard;
import com.demo.domain.Member;
import com.demo.domain.foodRecipe;
import com.demo.persistence.AdminQnaBoardRepository;
import com.demo.persistence.AdminRecipeBoardRepository;
import com.demo.persistence.AdminRecipeDBRepository;

/*
 * 체크할 사항
 * member테이블 완성되고 로그인 과정 완성되면 adminCheck(), 회원관리페이지
 * 커뮤니티 inquiry 되면 테이블 조회 및 답변기능
 */

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminRecipeBoardRepository adminRecipeBoardRepo;
	
	@Autowired
	private AdminQnaBoardRepository adminQnaBoardRepo;
	
	@Autowired
	private AdminRecipeDBRepository adminRecipeDBRepo;

	@Override
	public int adminCheck(Member vo) {
		int result = -1;
//		
//		// Member 테이블에서 사용자 조회
//		Optional<Member> member = memberRepo.findById(vo.getId());
//		
//		// 결과값 설정 (1:ID, PWD 일치 / 0: PWD 불일치 / -1: ID가 존재하지 않음)
//		// 멤버 전체에 대하여 사용자 객체의 id를 조회하고
//		// id가 존재할경우 해당 id의 유저코드를 가져와서 관리자(1)인 경우 1을 반환
//		if(member.isEmpty()) {
//			result = -1;
//		} else {
//			if(member.get().getUsercode().equals(1)) {
//				return result = 1;
//			} else {
//				return result = -1;
//			}
//		}
		return result;
	}

	@Override
	public List<Member> getAllMemberList() {
		// TODO Auto-generated method stub
		return adminRecipeBoardRepo.getAllMember();
	}

	@Override
	public void insertAdminRecipeBoard(AdminRecipeBoard vo) {
		adminRecipeBoardRepo.save(vo);
		
	}
	
	@Override
	public void insertAdminQnaBoard(AdminQnaBoard vo) {
		adminQnaBoardRepo.save(vo);
		
	}

	@Override
	public void updateAdminRecipeBoard(AdminRecipeBoard vo) {
		AdminRecipeBoard board = adminRecipeBoardRepo.findById(vo.getRecipe_boardnum()).get();
		
		vo.setRegdate(board.getRegdate());
		adminRecipeBoardRepo.save(vo);
	}
	
	@Override
	public void updateAdminQnaBoard(AdminQnaBoard vo) {
		AdminQnaBoard board = adminQnaBoardRepo.findByQnaBoardnum(vo.getQna_boardnum());
		
		vo.setRegdate(board.getRegdate());
		adminQnaBoardRepo.save(vo);
	}


	@Override
	public void deleteAdminRecipeBoard(long boardnum) {
		adminRecipeBoardRepo.deleteById(boardnum);
		
	}
	
	@Override
	public void deleteAdminQnaBoard(long boardnum) {
		adminQnaBoardRepo.deleteById(boardnum);
		
	}

	@Override
	public List<AdminRecipeBoard> getAllFoodBoardList() {
		return adminRecipeBoardRepo.getAllRecipeList();
	}

	@Override
	public List<AdminQnaBoard> getAllQnaBoardList() {
		// TODO Auto-generated method stub
		return adminQnaBoardRepo.getAllQnaList();
	}

	@Override
	public List<AdminQnaBoard> getAllAskBoardList() {
		// TODO Auto-generated method stub
		// 문의 게시판 아직이므로 음식게시판 출력******************
		return adminQnaBoardRepo.getAllAskList();
	}

	@Override
	public AdminRecipeBoard getByRecipeBoardnum(long boardnum) {
		// TODO Auto-generated method stub
		return adminRecipeBoardRepo.findByRecipeBoardnum(boardnum);
	}
	
	@Override
	public AdminQnaBoard getByQnaBoardnum(long boardnum) {
		// TODO Auto-generated method stub
		return adminQnaBoardRepo.findByQnaBoardnum(boardnum);
	}

	@Override
	public void insertAdminRecipeDB(foodRecipe vo) {
		adminRecipeDBRepo.save(vo);
		
	}

	@Override
	public List<foodRecipe> getRecipeDBListByName(String searchWord) {
		// TODO Auto-generated method stub
		return adminRecipeDBRepo.getRecipeDBListByName(searchWord);
	}

	@Override
	public List<foodRecipe> getRecipeDBListByIndex(String searchWord) {
		// TODO Auto-generated method stub
		return adminRecipeDBRepo.getRecipeDBListByIndex(searchWord);
	}

	@Override
	public void deleteDBData(long dbidx) {
		adminRecipeDBRepo.deleteById(dbidx);
		
	}

	@Override
	public foodRecipe getRecipeDBByIndex(int dbidx) {
		// TODO Auto-generated method stub
		return adminRecipeDBRepo.getRecipeDBByIndex(dbidx);
	}

	@Override
	public void updateAdminRecipeDB(foodRecipe vo) {
		
		adminRecipeDBRepo.save(vo);
		
	}

	

	

}
