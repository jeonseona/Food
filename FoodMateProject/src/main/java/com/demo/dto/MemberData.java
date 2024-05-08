package com.demo.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class MemberData {
	private String id;
	private String password;
	private Integer age;
	private Integer height;
	private Integer weight;
	private String name;
	private String gender;
	private String email;
	private Double bmi;
	private String goal;
	private String code;
	@Id
	private int no_data;

}
