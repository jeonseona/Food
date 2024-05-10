package com.example.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class movie {
	private String title;
	private int vote_count;
	private double vote_average;
	private double score;
	private String post_path;
}


