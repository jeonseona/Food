package com.demo.service;

import java.util.List;

import com.demo.domain.Com_Board_Detail;

public interface Com_Board_DetailService {
	
	public void insertBoard(Com_Board_Detail vo);
	
	public void updateBoard(Com_Board_Detail vo);
	
	public void deleteBoard(Com_Board_Detail vo);

	Com_Board_Detail getCom_Board_Datail(int seq);
	
	public List<Com_Board_Detail> getCom_Board();

}
