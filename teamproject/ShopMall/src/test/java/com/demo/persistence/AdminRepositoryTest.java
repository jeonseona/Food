package com.demo.persistence;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.demo.domain.Admin;

@SpringBootTest
public class AdminRepositoryTest {

	@Autowired
	public AdminRepository adRepo;
	
	@Disabled
	@Test
	public void testInsertAdmin() {
		Admin account = Admin.builder()
				.id("admin")
				.pwd("admin")
				.name("홍관리")
				.phone("010-5923-1932")
				.build();
		
		adRepo.save(account);
		
		Admin account2 = Admin.builder()
				.id("admin2")
				.pwd("admin2")
				.name("김사원")
				.phone("010-5923-1932")
				.build();
		
		adRepo.save(account2);
	}
}
