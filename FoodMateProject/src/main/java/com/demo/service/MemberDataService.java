package com.demo.service;

import com.demo.dto.MemberData;

public interface MemberDataService {

	// 마이페이지
	// 회원 조회용 : 기본키가 no_data여서 id로 조회가 안됨.
	public MemberData getMember(int no_data);
	// 개인정보 수정
	public void changeInfo(MemberData vo);
}
