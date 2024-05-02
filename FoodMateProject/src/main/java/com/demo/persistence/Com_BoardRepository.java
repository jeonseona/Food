package com.demo.persistence;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.demo.domain.Com_Board;
import com.demo.domain.Com_Board_Detail;

public interface Com_BoardRepository extends JpaRepository<Com_Board_Detail, Integer > {
	
	@Query(value="SELECT * FROM Com_Board c WHERE c.com_recipe.rcp_nm = ?1", nativeQuery=true)
	List<Com_Board> findCom_BoardByTitleContainingOrderByTitle(String title);// 제목으로 검색
	
	@Query(value="SELECT * FROM Com_Board c WHERE c.com_board_recipe.member_data.id = ?1", nativeQuery=true)
	List<Com_Board> findCom_BoardByIdContainingOrderById(String id);// 글쓴이 아이디로 검색
	
	Page<Com_Board> findCom_BoardBySeqContainingOrderBySeq(int seq, Pageable pageable); //페이징처리 
}
