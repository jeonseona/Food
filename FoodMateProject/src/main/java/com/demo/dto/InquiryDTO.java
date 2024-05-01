<<<<<<< HEAD:FoodMateProject/src/main/java/com/demo/dto/InquiryDTO.java
package com.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Getter
@Setter
@Entity
public class InquiryDTO {
	@Id
    private Long inquiryId;
    private String name;
    private String email;
    private String subject;
    private String message;
    private Timestamp createdAt;
    private String comments;
}
=======
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
>>>>>>> test_food:FoodMateProject/src/main/java/com/dto/InquiryDTO.java
