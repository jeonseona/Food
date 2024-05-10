package com.demo.service;

import java.util.List;

import com.demo.domain.Address;
import com.demo.domain.Member;

public interface MemberService {
	
	public void insertMember(Member vo);
	
	//회원정보조회
	public Member getMember(String id); 
	
	//회원 로그인
	public int LoginID(Member vo);
	
	//회원 인증
	public int confirmID(String id);
	
	//동 이름으로 주소 찾기
	public List<Address> getAddressByDong(String dong);
	
	//아이디 찾기
	public Member getIdByNameEmail(String name, String Email);
	
	//비밀번호 찾기
	public Member getPwdByIdNameEmail(String id, String name, String email);
	
	//비밀번호 변경
	public void updateByPwd(Member vo);
	
	public List<Member> getMemberList(String name);

}
