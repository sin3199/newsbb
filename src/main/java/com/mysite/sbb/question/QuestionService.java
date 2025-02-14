package com.mysite.sbb.question;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mysite.sbb.DataNotFoundException;
import com.mysite.sbb.user.SiteUser;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class QuestionService {

	private final QuestionRepository questionRepository;
	
//	public List<Question> getList() {
//		return this.questionRepository.findAll();
//	}
	
	public Page<Question> getList(int page) {
		
		// 정렬작업
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		
		// page : 조회페이지(사용자가 선택한 페이지번호), 10 : 한 페이지에 보여줄 게시물 개수
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
		
		return this.questionRepository.findAll(pageable);
	}
	
	public Question getQuestion(Integer id) {
		Optional<Question> question = this.questionRepository.findById(id);
		if(question.isPresent()) {
			return question.get();
		}else {
			throw new DataNotFoundException("question not found");
		}
	}
	
	public void create(String subject, String content, SiteUser siteUser) {
			
		Question q = new Question();
		q.setSubject(subject);
		q.setContent(content);
		q.setCreateDate(LocalDateTime.now());
		// 작성자.
		q.setAuthor(siteUser);
		
		this.questionRepository.save(q);
	}
	
	public void modify(Question question, String subject, String content) {
		question.setSubject(subject);
		question.setContent(content);
		question.setModifyDate(LocalDateTime.now());
		this.questionRepository.save(question);
	}
	
	public void delete(Question question) {
		this.questionRepository.delete(question);
	}
}
