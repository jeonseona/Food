package com.demo.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.demo.domain.Member;
import com.demo.dto.MemberData;

import jakarta.transaction.Transactional;

public interface MemberRepository extends JpaRepository<Member, String> {

	// 닉네임 중복체크
	int countByNickname(String nickname);
	
	/**
	 * 마이페이지용 테스트
	 */
	// 개인정보 변경 (id는 기본키라..)
	// 이름, 비밀번호, 이메일, 키, 몸무게, BMI, 나이, 목표 (완)
	@Transactional
	@Modifying
	@Query(value = "UPDATE member SET name=:name, pwd=:pwd, email=:email, height=:height, "
			+ " weight=:weight, bmi=:bmi, age=:age, goal=:goal WHERE id=:id", nativeQuery = true)
	public void changeInfo(@Param("id") String id, @Param("name") String name, @Param("pwd") String pwd, @Param("email") String email,
					@Param("height") Integer height, @Param("weight") Integer weight, @Param("bmi") Double bmi, @Param("age") Integer age, @Param("goal") String goal);

	// 사용자별 작성한 레시피 목록 조회
	/*
	 * @Query("SELECT recipe FROM ComBoardDetail recipe " +
	 * " INNER JOIN Member m ON recipe.member.id = m.id " +
	 * " WHERE recipe.member.id=?1 AND recipe.seq=?2") 
	 * public 게시글테이블 getRecipeByMemberId(String id, int seq);
	 */
	
	// 사용자별 추천받은 음식 목록
	/*
	 * @Query("SELECT history FROM RecommendHistory history " +
	 * " INNER JOIN Member m ON history.member.id = m.id " +
	 * " WHERE history.member.id=?1 ORDER BY history.recommend_date DESC") 
	 * public 추천기록테이블 getRecommendHistoryByMemberId(String id);
	 */
}
