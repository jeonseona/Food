package com.demo.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.demo.domain.Member;
import com.demo.dto.MemberData;

public interface MemberDataRepository extends JpaRepository<MemberData, String> {
	//구현때문에 만들어둔것입니다 덮어씌워주세용

	// 마이페이지
//	MemberData findByMember(Member member);
    
	/**
	* MemberData 수정
	* 키, 몸무게, bmi(자동 계산), age, gender, goal
	* Member의 id와 같은 MemberData의 정보 수정
	*/
	//@Transactional
	//@Modifying
	//@Query("UPDATE MemberData md SET md.height = :height, md.weight = :weight, md.bmi=:bmi, md.age = :age, md.gender = :gender, md.goal = :goal WHERE md.member.id = :id")
	//void updateMemberData(@Param("id") String id, @Param("height") Integer height, @Param("weight") Integer weight, @Param("bmi") Double bmi,
			//@Param("age") Integer age, @Param("gender") String gender, @Param("goal") String goal);

}
