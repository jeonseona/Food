package com.demo.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class MemberData {
    private String id;
    private String password;
    private int age;
    private int height;
    private int weight;
    private String name;
    private String gender;
    private String email;
    //private String code;
    //private String goal;
    @Id
    private int no_data;
  
   //데이터베이스에 저장된 회원 정보
   
   
}
