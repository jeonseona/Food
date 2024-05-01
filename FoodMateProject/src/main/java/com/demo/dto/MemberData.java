package com.demo.dto;

import jakarta.persistence.Entity;
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
    private int nodata;
}