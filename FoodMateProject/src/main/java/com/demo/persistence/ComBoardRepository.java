package com.demo.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.domain.Com_Board;

public interface ComBoardRepository extends JpaRepository<Com_Board, Integer> {


}
