package com.demo.dto;

import java.sql.Timestamp;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class InquiryDTO {

    private Long inquiryId;
    private String name;
    private String email;
    private String subject;
    private String message;
    private Timestamp createdAt;
    private String comments;
}
