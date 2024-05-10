package com.demo.persistence;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.demo.domain.Member;

@SpringBootTest
public class MemberRepositoryTest {
	
	@Autowired
	private MemberRepository memberRepo;
	
	@Disabled
	@Test
	public void memberInsert() {
		
		Member member1 = Member.builder() //파라미터주입
				.id("one")
				.pwd("1111")
				.name("홍길동")
				.email("kimnari@email.com")
				.zip_num("133-110")
				.address("서울시 성동구 성수동1가 1번지21호")
				.phone("010-777-7777")
				.regdate(new Date())
				.useyn("y")
				.build();
		
		memberRepo.save(member1);
		
		Member member2 = Member.builder()
				.id("two")
				.pwd("2222")
				.name("이순신")
				.email("leebakhap@email.com")
				.zip_num("130-120")
				.address("서울시 송파구 잠실2동 리센츠 아파트 201동 505호")
				.phone("010-123-4567")
				.regdate(new Date())
				.useyn("y")
				.build();
		
		memberRepo.save(member2);
	}
	
	@Disabled
	@Test
	public void testFindBynameAndEmail() {
		Member member = memberRepo.findByNameAndEmail("test", "test@email.com");
		System.out.println(member);
	}
	
	@Test
	public void testlistMember() {
		List<Member> list = memberRepo.findMemberByNameContaining(" ");
		
		for(Member m : list) {
			System.out.println(m);
		}
	}
	
	
	

}
