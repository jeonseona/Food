package com.demo.persistence;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ReplyRepositoryTest {
	
	@Autowired
	ReplyRepository replyRepo;
	
	@Test
	void testFindReplyByreplynumContainingOrderByreplynum() {
		fail("Not yet implemented"); 
	}

}
