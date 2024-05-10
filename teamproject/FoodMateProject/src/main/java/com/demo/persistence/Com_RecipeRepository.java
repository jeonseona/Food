package com.demo.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.dto.Com_Recipe;

public interface Com_RecipeRepository extends JpaRepository<Com_Recipe, Integer> {

}
