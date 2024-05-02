package com.demo.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.demo.domain.Com_Board;
import com.demo.domain.Mypage;

import jakarta.transaction.Transactional;

public interface MypageRepository extends JpaRepository<Mypage, Integer> {
	
	// 개인정보 변경
	@Transactional // 모든 작업이 성공적으로 완료되면 데이터베이스에 커밋되고, 하나라도 실패하면 롤백.
	@Modifying // 해당 쿼리가 데이터를 수정하는 작업(업데이트, 삭제)
	@Query(value="update member set nickname=:nickname, password=:password, email=:email "
				+ " where id=:id", nativeQuery=true) // nativeQuery:DB의 SQL 문법을 직접 사용.
	public void changeInfo(@Param("nickname") String nickname, @Param("password") String password,
				@Param("email") String email, @Param("id") String id);
	
	// 바디데이터 변경
	@Transactional
	@Modifying
	@Query(value="UPDATE member SET height=:height, weight=:weight "
			+ " WHERE id=:id", nativeQuery=true)
	public void changeBody(@Param("height") String height, @Param("weight") String weight,
				@Param("id") String id);
	
	// 음식 선호도 및 알레르기 설정
	// ???????????????????
	
	// 개인이 작성한 레시피 목록 조회
//	@Query("select cb from Com_Board cb where cb.member.id=?1")
//	public List<Com_Board> getRecipeList(String id);
}
