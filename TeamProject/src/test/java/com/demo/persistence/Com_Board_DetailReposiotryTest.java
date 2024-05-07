package com.demo.persistence;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.demo.domain.Com_Board_Detail;
import com.demo.dto.Com_Recipe;
import com.demo.dto.MemberData;

@SpringBootTest
class Com_Board_DetailReposiotryTest {
	
	@Autowired
	Com_Board_DetailReposiotry comboardDetailRepo;
	@Autowired
	MemberDataRepository memberRepo;

	@Test
	void testGetCom_Board_Detail() {
		// 레시피
		Com_Recipe recipe = new Com_Recipe();
		recipe.setIdx(1);
		
		// 멤버
		MemberData member = new MemberData();
		Optional<MemberData> member_data = memberRepo.findById(1);
		if (member_data.isPresent()) {
			member = member_data.get();
			System.out.println("member=" + member);
		} else {
			System.out.println("member 데이터가 없습니다.");
		}
		
		Com_Board_Detail board_detail = new Com_Board_Detail();
		board_detail.setCom_recipe(recipe);
		board_detail.setMember_data(member);
		board_detail.setCnt(0);
		
		comboardDetailRepo.save(board_detail);
		
	}

}
