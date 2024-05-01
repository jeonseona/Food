package com.demo.persistence;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.demo.domain.Join;

@SpringBootTest
public class MemberRepositoryTest {

	@Autowired
	private MemberRepository memberRepo;
	
	@Disabled
	@Test
	public void memberInsert() {
		Join member1 = Join.builder()
				.id("one")
				.pwd("1111")
				.name("홍길동")
				.email("kimnari@email.com")
				.phone("010-7577-7777")
				.regdate(new Date())
				.useyn("y")
				.build();
		
		memberRepo.save(member1);
		
		Join member2 = Join.builder()
				.id("two")
				.pwd("2222")
				.name("이순신")
				.email("leebakhap@email.com")
				.phone("010-0123-4567")
				.regdate(new Date())
				.useyn("y")
				.build();
		
		memberRepo.save(member2);
	}
	
	@Disabled
	@Test
	public void testFindByNameAndEmail() {
		Join member = memberRepo.findByNameAndEmail("장보고", "bkjang@email.com");
		
		System.out.println("member=" + member);
	}
	
	@Test
	public void testFindMemberByName() {
		List<Join> mList = memberRepo.findMemberByNameContaining("");
		
		for(Join m : mList) {
			System.out.println(m);
		}
	}
}








