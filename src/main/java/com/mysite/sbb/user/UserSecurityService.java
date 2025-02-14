package com.mysite.sbb.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// 시큐리티에서 사용하는 인증기능 목적
@Slf4j
@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService {

	
	private final UserRepository userRepository;
	
	// loadUserByUsername 인증메서드 : 스프링시큐리티에서 사용자(아이디)를 조회하는 목적 
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<SiteUser> _siteUser = this.userRepository.findByusername(username);
		
		if(_siteUser.isEmpty()) {
			throw new UsernameNotFoundException("사용자를 찾을수 없습니다.");
		}
		
		SiteUser siteUser = _siteUser.get();
		
		log.info("로그인 정보: " + siteUser);
		
		// 권한작업(로그인시 사용자가 admin or user) 구분
		List<GrantedAuthority> authroities = new ArrayList<>();
		// 사용자가 입력한 아이디가 admin 이면
		if("admin".equals(username)) {
			authroities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
		}else {
			authroities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
		}
		
		// User객체의 비밀번호 사용자로부터 입력받은 비밀번호와 일치하는 지를 검사하는 기능을 내부에 가지고 있다.
		return new User(siteUser.getUsername(), siteUser.getPassword(), authroities);
	}

}
