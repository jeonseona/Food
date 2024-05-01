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
	
	Member findByIdAndNameAndEmail(String id, String name, String email);

	List<Member> findMemberByNameContaining(String id);

	public List<Member> findByNameContaining(String name);

	public Member findByLoginId(String loginId);
	
	public void changePassword(String id, String pwd);

}





