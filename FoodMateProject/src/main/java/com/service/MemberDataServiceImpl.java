package com.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.domain.Member;
import com.demo.dto.MemberData;
import com.demo.persistence.MemberDataRepository;

@Service
public class MemberDataServiceImpl implements MemberDataService {

	@Autowired
	private MemberDataRepository MemberDataRepo;

	@Override
	public MemberData findByMember(Member member) {
		
		return MemberDataRepo.findByMember(member);
	}

	@Override
	public void changeInfo(MemberData vo) {
		// 
		MemberDataRepo.updateMemberData(vo.getMember().getId(), vo.getHeight(), vo.getWeight(), 
				vo.getBmi(), vo.getAge(), vo.getGender(), vo.getGoal());

	}

	

	

	

}
