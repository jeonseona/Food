package com.demo.dto;

import lombok.Data;

@Data
public class MemberVO {
	private String id;
    private String name;
    private String nickname;
    private String email;
    private String password;
   
    // 데이터 전송용 
}
