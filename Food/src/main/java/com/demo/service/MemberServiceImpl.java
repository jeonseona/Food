package com.demo.service;

import java.util.List;
import java.util.Optional;

import org.hibernate.mapping.Join;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.domain.Member;
import com.demo.persistence.MemberRepository;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberRepository memberRepo;    

    @Override
    public void insertMember(Member vo) {
        // 회원 정보 저장
        memberRepo.save(vo);
    }

    @Override
    public Member getMember(String id) {
        Optional<Member> member = memberRepo.findById(id);
        return member.orElse(null);
    }

    @Override
    public int loginID(Member vo) {
        Optional<Member> member = memberRepo.findById(vo.getId());

        // 결과값 설정: 
        // 1: ID,PWD 일치, 0: 비밀번호 불일치, -1: ID가 존재하지 않음.
        if (member.isEmpty()) {
            return -1;
        } else if(member.get().getPwd().equals(vo.getPwd())) {
            return 1;
        } else {
            return 0;   // 비밀번호 불일치
        }
    }

    /*
     * return값:
     *      1: id 존재,  -1: id 존재하지 않음.
     */
    @Override
    public int confirmID(String id) {
        return memberRepo.existsById(id) ? 1 : -1;
    }

    @Override
    public Member getIdByNameEmail(String name, String email) {
        return memberRepo.findByNameAndEmail(name, email);
    }

    @Override
    public Member getPwdByIdNameEmail(String id, String name, String email) {
        return memberRepo.findByIdAndNameAndEmail(id, name, email);
    }

    @Override
    public void changePassword(Member vo) {
        memberRepo.changePassword(vo.getId(), vo.getPwd());
    }

    @Override
    public List<Member> getMemberList(String name) {
        return memberRepo.findMemberByNameContaining(name);
    }

	@Override
	public void insertMember(Join vo) {
		
	}

	@Override
	public int loginID(Join vo) {
		return 0;
	}

	@Override
	public void changePassword(Join vo) {

		
	}

	@Override
	public Member getMemberByLoginId(String loginId) {
		return null;
	}
}