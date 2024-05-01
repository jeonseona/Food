// 자주하는 질문과 답변을 저장할 데이터베이스 모델을 설계
package com.demo.domain;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.demo.dto.QnADTO;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
@DynamicUpdate
public class QnA {

	
	@Id
	@OneToOne
    @JoinColumn(name="seq", nullable=false)
	private QnA_Detail qna_detail; // QnA글 번호

	@OneToOne
	@JoinColumn(name="idx", nullable=false)
	private QnADTO title; // QnA글 제목
	
    
     
    
}
