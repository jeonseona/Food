package com.demo.service;

import com.demo.domain.Member;
import com.demo.dto.MemberData;

public interface MemberDataService {

	// 마이페이지
	// 회원 조회용
	MemberData findByMember(Member member);
	// 개인정보 수정
	public void changeInfo(MemberData vo);
}
