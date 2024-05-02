package com.demo.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.demo.domain.Com_Board;
import com.demo.domain.Com_Board_Detail;

public interface Com_Board_DetailReposiotry extends JpaRepository<Com_Board_Detail, Integer> {

	
	@Query(value="SELECT * FROM Com_Board_Detail c WHERE c.seq = ?1 ", nativeQuery=true)
	public Com_Board_Detail getCom_Board_Detail(int seq); // 상세페이지 정보 출력
	
	@Query(value="SELECT * FROM Com_Board_Detail ", nativeQuery=true)
	public List<Com_Board_Detail> getBoard_List(); //게시글 리스트 출력

}
