package com.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.dto.MemberData;
import com.demo.persistence.MemberDataRepository;

@Service
public class MemberDataServiceImpl implements MemberDataService {

	@Autowired
	private MemberDataRepository MemberDataRepo;
	
	@Override
	public MemberData getMember(int no_data) {
		// 기본키가 no_data로 되어있어서...
		return MemberDataRepo.findById(no_data).get();
	}

	@Override
	public void changeInfo(MemberData vo) {

		MemberDataRepo.changeInfo(vo.getId(), vo.getName(), vo.getPassword(), vo.getEmail(), 
				vo.getHeight(), vo.getWeight(), vo.getBmi(), vo.getAge(), vo.getGoal());

	}

}
