package com.demo.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.demo.domain.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {
	
	//댓글 페이징
	@Query(value="SELECT * FROM Reply r WHERE r.com_board_detail.seq = ?1 ", nativeQuery=true)
	Page<Reply> findReplyByreplynumContainingOrderByReplynum(int replynum, Pageable pageable);
	
	// 댓글 출력
	@Query(value="SELECT * FROM Reply r WHERE r.com_board_detail.seq = ?1 ", nativeQuery=true)
	Reply getReplyList(int seq); // 상세페이지 정보 출력

}
