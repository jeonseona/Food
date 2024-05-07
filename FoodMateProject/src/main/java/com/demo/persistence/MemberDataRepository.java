package com.demo.persistence;




import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.dto.MemberData;

public interface MemberDataRepository extends JpaRepository<MemberData, String> {

    // 이름을 통해 회원 데이터 찾기
    List<MemberData> findByName(String name);

    // 나이로 회원 데이터 찾기
    List<MemberData> findByAge(int age);

    // 성별로 회원 데이터 찾기
    List<MemberData> findByGender(String gender);

   


    
    // 추가적인 메소드 정의가 필요하다면 여기에 작성.
}
