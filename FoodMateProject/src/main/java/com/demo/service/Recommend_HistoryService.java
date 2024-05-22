package com.demo.service;

import java.util.List;

import com.demo.domain.MemberData;
import com.demo.domain.Recommend_History;

public interface Recommend_HistoryService {

	// 회원별 추천받은 음식(레시피)기록 조회
	public List<Recommend_History> getMyRecommendHistory(MemberData member);
	
	void saveRecommendHistory(Recommend_History history); 

    void deleteRecommendHistory(int idx);
}
