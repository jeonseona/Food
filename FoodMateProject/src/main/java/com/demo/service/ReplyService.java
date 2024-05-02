package com.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.demo.domain.Reply;

public interface ReplyService {
	
	public void insertReply(Reply vo);
	
	public void updateReply(Reply vo);
	
	public void deleteReply(Reply vo);
	
	public List<Reply> getReplyList();
	
	public Page<Reply> getReplyList_paging(int replynum , int page, int size);
	
	
	
}
