package com.demo.persistence;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.demo.domain.Member;
import com.demo.domain.Product;
import com.demo.domain.ProductComment;

@SpringBootTest
public class ProductCommentRepositoryTest {
	
	@Autowired
	private ProductCommentRepository commentRepo;
	
	@Test
	public void testInsertComment(){
		Member member1 = 
		new Member("one", "1111", "홍길동", "one@email.com", "1", "2", "3","4", new Date());
		
		Product product = new Product(1,
		"크로커다일부츠", "2", 40000, 50000, 10000, "오리지날 크로커다일부츠 입니다.", "w2.jpg", "n", "y", new Date());
		
		ProductComment comment = ProductComment.builder().
				product(product)
				.content("따뜻해요!")
				.member(member1)
				.build();
		
		commentRepo.save(comment);
		
	}

}
