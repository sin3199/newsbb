package com.mysite.sbb.user;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

// 엔티티 클래스의 옵션명령어 학습

@Table(name = "product")
@Entity
public class Item {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "item_id")
	private Long id;  //엔티티 클래스에서 관례적으로 id필드명을 사용하고, 기본키로 설정한다.
	
	@Column(nullable = false, length = 50)
	private String itemNm;  // 문자열은 기본사이트 varchar(255)
	
	@Column(nullable = false)
	private int price;
	
	@Column(nullable = false, name = "number")
	private int stockNumber;
	
	@Lob  // Large Object - CLOL, BLOB
	@Column(nullable = false)
	private String itemDetail;
	
	private LocalDateTime regTime;
	private LocalDateTime updateTime;
}
