package com.demo.domain;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.demo.dto.Com_Recipe;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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
public class Com_Board_Detail {

			@Id
			@GeneratedValue(strategy = GenerationType.IDENTITY)
			private int seq; //게시글번호
			
		    private String d_title; // 제목
			
			@OneToOne
		    @JoinColumn(name="idx", nullable=false)
		    private Com_Recipe com_recipe; //레시피번호
		    
		    @OneToOne
		    @JoinColumn(name="no_data", nullable=false)
		    private Member_Data member_data; //회원번호
		    
		    @Temporal(value=TemporalType.TIMESTAMP)
		    @ColumnDefault("sysdate")
		    private String d_regdate; //작성일자 
		    
		    private int cnt;

			public Com_Board_Detail(int seq, String d_title, Com_Recipe com_recipe, Member_Data member_data,
					String d_regdate, int cnt) {
				super();
				this.seq = seq;
				this.d_title = d_title;
				this.com_recipe = com_recipe;
				this.member_data = member_data;
				this.d_regdate = d_regdate;
				this.cnt = cnt;
			}
			

			
		

}
