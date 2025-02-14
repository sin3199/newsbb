package com.mysite.sbb.answer;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mysite.sbb.DataNotFoundException;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.user.SiteUser;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AnswerService {

	private final AnswerRepository answerRepository;
	
	// 답변등록..   DTO 클래스 -> Entity 클래스변환.  반대로 db에서 읽어오는 의미.Entity 클래스 -> DTO클래스 변환
	public void create(Question question, String content, SiteUser siteUser) {
		Answer answer = new Answer();
		answer.setContent(content);
		answer.setCreateDate(LocalDateTime.now());
		
		// 부모글.  mybastis문법에서는 부모글의 id값이 대입.
		// jpa에서는 부모글의 id값을 이용하여 question객체를 사용한다.
		answer.setQuestion(question); 
		
		// 작성자 아이디를 저장.
		answer.setAuthor(siteUser);
		
		this.answerRepository.save(answer);
	}
	
	// 수정또는 삭제를 하기위한 Answer엔티티를 참조.
	public Answer getAnswer(Integer id) {
		Optional<Answer> answer = this.answerRepository.findById(id);
		if (answer.isPresent()) {
			return answer.get();
		} else {
			throw new DataNotFoundException("answer not found");
		}
	}

	// 답변글 수정
	public void modify(Answer answer, String content) {
		answer.setContent(content);
		answer.setModifyDate(LocalDateTime.now());
		this.answerRepository.save(answer);
	}

	// 답변글 삭제
	public void delete(Answer answer) {
		this.answerRepository.delete(answer);
	}
}
