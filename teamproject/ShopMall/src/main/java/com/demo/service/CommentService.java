package com.demo.service;

import java.util.List;

import com.demo.domain.ProductComment;

public interface CommentService {

		public void saveComment(ProductComment comment);
		
		//상품평 조회
		public List<ProductComment> getCommentList(int pseq);
		
		public int getCountCommentList(int pseq);
}
