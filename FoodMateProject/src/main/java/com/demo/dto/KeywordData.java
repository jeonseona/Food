package com.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KeywordData {
	private String selectedKeywords;
    private String excludeIngredients;
    private String dietCalory;
    private String nutrientChoice;
}
