package com.demo.service;

import java.util.List;

import org.hibernate.mapping.Join;

import com.demo.domain.Member;

public interface MemberService {
	
	public void insertMember(Join vo);
	
	// 회원정보 상세 조회
	public Member getMember(String id);
	
	// 회원 로그인
	public int loginID(Join vo);
	
	// 회원 인증
	public int confirmID(String id);
	
	// 이름과 이메일로 아이디 찾기
	public Member getIdByNameEmail(String name, String email);
	
	// 아이디와 이름과 이메일로 비밀번호 찾기
	public Member getPwdByIdNameEmail(String id, String name, String email);
	
	public void changePassword(Join vo);
	
	public List<Member> getMemberList(String name);

	void insertMember(Member member);

	int loginID(Member member);

	void changePassword(Member member);

	public Member getMemberByLoginId(String loginId);
}







