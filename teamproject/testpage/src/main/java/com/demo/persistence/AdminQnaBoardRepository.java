package com.demo.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.demo.domain.AdminQnaBoard;

public interface AdminQnaBoardRepository extends JpaRepository<AdminQnaBoard, Long> {

	
	@Query(value="SELECT * FROM admin_qna_board ORDER BY regdate DESC", nativeQuery = true)
	List<AdminQnaBoard> getAllQnaList();
	
	@Query(value="SELECT * FROM admin_qna_board ORDER BY regdate DESC", nativeQuery = true)
	List<AdminQnaBoard> getAllAskList();
	
	@Query(value="SELECT * FROM admin_qna_board WHERE qna_boardnum = :boardnum ORDER BY regdate DESC", nativeQuery = true)
	AdminQnaBoard findByQnaBoardnum(long boardnum);
	
}
