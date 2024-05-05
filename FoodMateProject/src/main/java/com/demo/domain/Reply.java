package com.demo.domain;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.demo.dto.MemberData;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DynamicInsert 
@DynamicUpdate
public class Reply {
	
	@Id
	private int replynum;
	
	private String content;
	
	@ManyToOne
    @JoinColumn(name="seq", nullable=false)
	private Com_Board_Detail com_board_detail;
	
	@ManyToOne
    @JoinColumn(name="no_data", nullable=false)
	private MemberData member_data;
	


}
