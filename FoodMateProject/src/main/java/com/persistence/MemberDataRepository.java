package com.demo.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.dto.MemberData;

public interface MemberDataRepository extends JpaRepository<MemberData, Integer> {
	//구현때문에 만들어둔것입니다 덮어씌워주세용
}
