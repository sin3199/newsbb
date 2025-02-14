package com.mysite.sbb.question;

import java.security.Principal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.mysite.sbb.answer.AnswerForm;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/question")
@RequiredArgsConstructor
@Controller
public class QuestionController {

	private final QuestionService questionService;
	
	private final UserService userService;
	
	@GetMapping("/list")
	//@ResponseBody
//	public String list(Model model) {
//		
//		List<Question> questionList = this.questionService.getList();
//		model.addAttribute("questionList", questionList);
//		
//		return "question_list";
//	}
	public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
		Page<Question> paging = this.questionService.getList(page);
		model.addAttribute("paging", paging);
		return "question_list";
	}
	
	@GetMapping(value = "/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {
		
		Question question = this.questionService.getQuestion(id);
		
		/*
		QuestionDto dto = new QuestionDto();
		dto.setId(question.getId());
		dto.setSubject(question.getSubject());
		dto.setContent(question.getContent());
		dto.setCreateDate(question.getCreateDate());
		*/
		
		model.addAttribute("question", question);
		
		return "question_detail";
	}
	
	@PreAuthorize("isAuthenticated()") // 로그인한 경우에만 실행
	@GetMapping("/create") // 파라미터 설명. QuestionForm questionForm
	public String questionCreate(QuestionForm questionForm) {
		
		// QuestionForm questionForm 파라미터가 Model 기능을 제공.
		
		return "question_form";
	}
	
	@PreAuthorize("isAuthenticated()")  // 로그인한 경우에만 실행
	@PostMapping("/create")
	public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal) {
		
		if(bindingResult.hasErrors()) {  // 사용자가 입력한 데이터가 유효성검사에서 적합하지 않으면
			return "question_form"; // 질문등록 폼 페이지를 클라이언트에게 다시 보냄.
		}
		
		SiteUser siteUser = this.userService.getUser(principal.getName());
		
		// 질문등록하기
		this.questionService.create(questionForm.getSubject(), questionForm.getContent(), siteUser);
		
		return "redirect:/question/list";
	}
	
	
	// 질문글 수정폼
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{id}")
	public String questionModify(QuestionForm questionForm, @PathVariable("id") Integer id, Principal principal) {
		Question question = this.questionService.getQuestion(id);
		// 질문글의 작성자와 로그인한 사용자가 일치하지 않으면
		if(!question.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
		}
		
		// model 작업과 동일한 의미.
		questionForm.setSubject(question.getSubject());
		questionForm.setContent(question.getContent());
		
		return "question_form";
	}
	
	// 질문글 수정하기	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify/{id}")
	public String questionModify(@Valid QuestionForm questionForm, BindingResult bindingResult, 
										Principal principal, @PathVariable("id") Integer id) {
		if(bindingResult.hasErrors()) {
			return "question_form";
		}
		
		// 수정시 해당 질문글의 엔티티 클래스 객체.
		Question question = this.questionService.getQuestion(id);
		if(!question.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
		}
		
		this.questionService.modify(question, questionForm.getSubject(), questionForm.getContent());
		
		// 질문상세주소로 다시 이동.
		return String.format("redirect:/question/detail/%s", id);
	}
	
	// 질문글 삭제하기
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/delete/{id}")
	public String questionDelete(Principal principal, @PathVariable("id") Integer id) {
		Question question = this.questionService.getQuestion(id);
		if(!question.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
		}
		this.questionService.delete(question);
		return "redirect:/";
	}
	
}
