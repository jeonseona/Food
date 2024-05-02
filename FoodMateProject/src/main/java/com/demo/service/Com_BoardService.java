package com.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.demo.domain.Com_Board;
import com.demo.domain.Com_Board_Detail;

public interface Com_BoardService {
	
	public Page<Com_Board> getAllCom_Board(int seq, int page, int size);
	
	public List<Com_Board> getCom_BoardByTitle(String title);
	
	public List<Com_Board> getCom_BoardByWriter(String writer);
	

}
