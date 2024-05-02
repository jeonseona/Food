package com.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.domain.Com_Board_Detail;
import com.demo.persistence.Com_Board_DetailReposiotry;

@Service
public class Com_Board_DetailServiceImpl implements Com_Board_DetailService {
	
	@Autowired
	Com_Board_DetailReposiotry BoardDetailRepo;
	
	
	@Override
	public Com_Board_Detail getCom_Board_Datail(int seq) {
		return BoardDetailRepo.getCom_Board_Detail(seq);
	}

	@Override
	public void insertBoard(Com_Board_Detail vo) {
		BoardDetailRepo.save(vo);
		
	}

	@Override
	public void updateBoard(Com_Board_Detail vo) {
		Com_Board_Detail p = BoardDetailRepo.findById(vo.getSeq()).get();
		vo.setD_regdate(p.getD_regdate());
		vo.setCnt(p.getCnt());
		BoardDetailRepo.save(vo);
		
	}

	@Override
	public void deleteBoard(Com_Board_Detail vo) {
		BoardDetailRepo.delete(vo);
		
	}

	@Override
	public List<Com_Board_Detail> getCom_Board() {
		return BoardDetailRepo.getBoard_List();
	}


}
