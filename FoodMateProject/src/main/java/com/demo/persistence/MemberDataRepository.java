package com.demo.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.demo.dto.MemberData;

import jakarta.transaction.Transactional;

public interface MemberDataRepository extends JpaRepository<MemberData, Integer> {

	// 개인정보 변경 (마이페이지)
	// 이름, 비밀번호, 이메일, 키, 몸무게, BMI, 나이, 목표 (완)
	@Transactional
	@Modifying
	@Query(value = "UPDATE MemberData SET name=:name, password=:password, email=:email, height=:height, "
			+ " weight=:weight, bmi=:bmi, age=:age, goal=:goal WHERE id=:id", nativeQuery = true)
	public void changeInfo(@Param("id") String id, @Param("name") String name, @Param("password") String password, @Param("email") String email,
					@Param("height") Integer height, @Param("weight") Integer weight, @Param("bmi") Double bmi, @Param("age") Integer age, @Param("goal") String goal);
	
}
