package com.mysite.sbb.question;

import java.time.LocalDateTime;
import java.util.List;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.user.SiteUser;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

// Entity Class(엔티티 클래스).  참고로 VO, DTO 클래스랑은 구분을 해서 사용해야 한다.
// 용도? 데이타베이스의 테이블을 생성하는 목적

@Entity // 클래스가 최소한의 식별자(PK)에 해당하는 필드가 반드시 존재해야 한다.
//@Getter
//@Setter  // 사용하지 않는 편이다.
@Data // @Getter, @Setter, @ToString, ... 등을 포함.
public class Question {

	// 식별자(PK) 용도로 사용.
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Db의 종류에 따라 선택
	private Integer id;
	
	@Column(length = 200)
	private String subject;
	
	@Column(columnDefinition = "TEXT")
	private String content;
	
	private LocalDateTime createDate;
	private LocalDateTime modifyDate;
	
	@ManyToOne
	private SiteUser author; // 테이블 author_id 컬럼추가
	
	// 질문글의 답변에 해당하는 글들을 참조. 
	@OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
	private List<Answer> answerList;
}
