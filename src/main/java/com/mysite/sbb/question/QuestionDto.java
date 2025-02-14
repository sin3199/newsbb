package com.mysite.sbb.question;

import java.time.LocalDateTime;
import java.util.List;

import com.mysite.sbb.answer.Answer;

import lombok.Data;

@Data
public class QuestionDto {

	private Integer id;
	private String subject;
	private String content;
	private LocalDateTime createDate;
}
