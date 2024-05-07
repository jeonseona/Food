package com.demo.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.demo.domain.Com_Board_Detail;

public interface Com_Board_DetailRepository extends JpaRepository<Com_Board_Detail, Integer> {

	// 회원별 작성한 게시글(레시피)조회
	@Query("SELECT cbd FROM Com_Board_Detail cbd WHERE cbd.member_data.id=?1")
	public List<Com_Board_Detail> getMyRecipeListById(String id);
}
