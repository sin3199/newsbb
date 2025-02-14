package com.mysite.sbb.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

// 유효성 검사목적
@Data
public class UserCreateForm {

	@Size(min = 3, max = 25)
	@NotEmpty(message = "사용자ID는 필수항목입니다.")
	private String username;
	
	@NotEmpty(message = "비밀번호는 필수항목입니다.")
	private String password1;
	
	@NotEmpty(message = "비밀번호 확인은 필수항목입니다.")
	private String password2;
	
	@NotEmpty(message = "전자우편은 필수항목입니다.")
	@Email // 전자우편 형식과 일치하는 지 체크
	private String email;
}
