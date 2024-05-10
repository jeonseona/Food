package com.demo.persistence;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.demo.domain.Member;
import com.demo.domain.Qna;

@SpringBootTest
public class QnaRepositoryTest {
	
	@Autowired
	QnaRepository qnaRepo;
	
	
	@Test
	public void testInsertQna() {
		Member member = 
		new Member("test", "1111", "test", "test@email.com","","","","y", new Date() );
		
		Qna qna1 = Qna.builder().subject("Qna 테스트")
				.content("질문내용 1").reply("답변 드립니다.")
				.rep("2").indate(new Date()).member(member).build();
		
		qnaRepo.save(qna1);
		
		Qna qna2 = Qna.builder().subject("Qna 테스트2")
				.content("질문내용 2").reply("두번째 답변 드립니다.")
				.indate(new Date()).member(member).build();
		
		qnaRepo.save(qna2);
		
	}
	
	@Disabled
	@Test
	public void testGetQnaList() {
		
		List<Qna> qnaList = qnaRepo.getQnaList("test2");
		for(Qna qna : qnaList) {
			System.out.println(qna);
		}
	}
}
