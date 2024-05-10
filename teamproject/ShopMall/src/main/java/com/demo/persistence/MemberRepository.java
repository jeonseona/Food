package com.demo.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.demo.domain.Member;

import jakarta.transaction.Transactional;

public interface MemberRepository extends JpaRepository<Member, String> {

	public Member findByNameAndEmail(String name, String email);
	
	public Member findByIdAndNameAndEmail(String id, String name, String email);
	
	@Transactional
	@Modifying //변경감지
	@Query(value="UPDATE member SET pwd= :pwd WHERE id=:id", nativeQuery=true)
	public void updateByPwd(@Param("id") String id, @Param("pwd") String pwd);
	
	
	
	@Query("SELECT m FROM Member m WHERE name LIKE %?1% "
			+ "ORDER BY regdate DESC")
	public List<Member> findMemberByNameContaining(String name);
	
}
