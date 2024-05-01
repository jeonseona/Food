package com.demo.dto;


import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class QnADTO {
	private int id;
    private String title;
    private String content;
    private String author;

}
