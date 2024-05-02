package com.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.demo.domain.Com_Board;
import com.demo.persistence.Com_BoardRepository;

@Service
public class Com_BoardServiceImpl implements Com_BoardService {
	
	@Autowired
	Com_BoardRepository boardRepo;
	
	// 페이징처리
	@Override
	public Page<Com_Board> getAllCom_Board(int seq, int page, int size) {
		Pageable pageable = PageRequest.of(page-1, size, Direction.DESC, "seq");
		return boardRepo.findCom_BoardBySeqContainingOrderBySeq(seq, pageable);
	}

	//제목으로 검색
	@Override
	public List<Com_Board> getCom_BoardByTitle(String title) {
		return boardRepo.findCom_BoardByTitleContainingOrderByTitle(title);
	}
	
	//아이디로 검색
	@Override
	public List<Com_Board> getCom_BoardByWriter(String id) {
		return boardRepo.findCom_BoardByIdContainingOrderById(id);
	}
	

}
