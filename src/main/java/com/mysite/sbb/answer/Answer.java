package com.mysite.sbb.answer;

import java.time.LocalDateTime;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.user.SiteUser;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Answer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 일련번호 자동으로 생성.
	private Integer id;
	
	@Column(columnDefinition = "TEXT")
	private String content;
	
	private LocalDateTime createDate;
	private LocalDateTime modifyDate;
	
	// 답변글의 작성자
	@ManyToOne
	private SiteUser author;  // 테이블 author_id 컬럼추가
	
	// mybatis문법.  부모글의 id필드선언.
	// 관계지정.  질문글(1)-One : 답변글(N)-Many
	@ManyToOne
	private Question question;
	
	
}
