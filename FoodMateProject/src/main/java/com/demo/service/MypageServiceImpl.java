package com.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.domain.Com_Board;
import com.demo.domain.Member;
import com.demo.persistence.MemberRepository;
import com.demo.persistence.MypageRepository;

@Service
public class MypageServiceImpl implements MypageService {

	@Autowired
	public MypageRepository mypageRepo;
	@Autowired
	public MemberRepository memberRepo;
	
	@Override
	public Member getMember(String id) {
		// 개인정보 조회
		return memberRepo.findById(id).get();
	}

	@Override
	public void changeInfo(Member vo) {
		// 개인정보(닉네임, 비밀번호, 이메일)변겅
		mypageRepo.changeInfo(vo.getNickname(), vo.getPassword(), vo.getEmail(), vo.getId());
	}

	@Override
	public void changeBody(Member vo) {
		//  바디데이터(키, 몸무게)변경
		mypageRepo.changeBody(vo.getHeight(), vo.getWeight(), vo.getId());
	}
	
	@Override
	public List<Com_Board> getRecipeList(String id) {
		// 개인이 작성한 레시피 목록 보기
		return mypageRepo.getRecipeList(id);
	}

}
