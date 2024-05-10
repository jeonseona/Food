package com.demo.persistence;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.domain.ProductComment;
import com.demo.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private ProductCommentRepository commentRepo;
	
	@Override
	public void saveComment(ProductComment comment) {
		commentRepo.save(comment);
	}

	@Override
	public List<ProductComment> getCommentList(int pseq) {
		
		return commentRepo.findCommentByPseq(pseq);
	}

	@Override
	public int getCountCommentList(int pseq) {
		
		List<ProductComment> list = commentRepo.findCommentByPseq(pseq);
		return list.size();
		
	}

}
