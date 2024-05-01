package com.demo.domain;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Entity
@DynamicInsert 
@DynamicUpdate
public class Reply {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int replynum;
	
	private String content;
	
	@OneToOne
    @JoinColumn(name="seq", nullable=false)
	private Com_Board_Detail com_board_detail;
	
	@ManyToOne
    @JoinColumn(name="no_data", nullable=false)
	private Member_Data member_data;

	public Reply(int replynum, String content, Com_Board_Detail com_board_detail, Member_Data member_data) {
		super();
		this.replynum = replynum;
		this.content = content;
		this.com_board_detail = com_board_detail;
		this.member_data = member_data;
	}
	
	public Reply() {}
	
	


}
