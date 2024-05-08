package com.demo.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.demo.domain.Com_Board_Detail;

public interface Com_Board_DetailRepository extends JpaRepository<Com_Board_Detail, Integer> {

	// 마이페이지
	// 회원별 작성한 게시글(레시피)조회
	@Query("SELECT b FROM Com_Board_Detail b JOIN b.member_data m WHERE m.id=?1")
	public Page<Com_Board_Detail> getMyRecipeListById(String id, Pageable pageable);
	
}
