package com.mysite.sbb.user;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/user")
@RequiredArgsConstructor
@Controller
public class UserController {

	private final UserService userService;
	
	
	@GetMapping("/signup")
	public String signup(UserCreateForm userCreateForm) {
		return "signup_form";
	}
	
	@PostMapping("signup")
	public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "signup_form";
		}
		
		// 비밀번호, 비밀번호 확인 작업이 일치하지 않으면
		if(!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
			bindingResult.rejectValue("password2", "passwordInCorrect", "2개의 패스워드가 일치하지 않습니다.");
			return "signup_form";
		}
		
		// 아이디가 중복이 되었을 때 회원가입방지하기.
		
		try {
			userService.create(userCreateForm.getUsername(), userCreateForm.getEmail(), userCreateForm.getPassword1());
		} catch (DataIntegrityViolationException e) { // 회원아이디가 중복이 되었을 때 발생하는 예외처리
		    e.printStackTrace();
			bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
			return "signup_form";
		} catch(Exception e) { // 일반적인 데이타베이스 연동시 발생하는 예외처리.
			e.printStackTrace();
			bindingResult.reject("signupFailed", e.getMessage());
			return "signup_form";
		}
		
		
		
		return "redirect:/";
	}
	
	@GetMapping("/login") // SecurityConfig.java파일에서 로그인 주소(/user/login") 커스텀 설정된 주소를 일치시켜 작업해야 한다.
	public String login() {
		return "login_form";
	}
	
	
}
