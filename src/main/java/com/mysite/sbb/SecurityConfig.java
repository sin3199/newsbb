package com.mysite.sbb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration // 환경설정기능을 담당하는 클래스에 사용하는 애너테이션
@EnableWebSecurity // 모든 요청 URL이 스프링시큐리티 제어를 받게하는 애너테이션
@EnableMethodSecurity(prePostEnabled = true) // @PreAuthorize("isAuthenticated()") 동작하기위하여 설정.
public class SecurityConfig {

	
	@Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // 모든 URL주소를 허용설정.
        	.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                .requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
            
            
            // 웹상에서 h2-console을 접근할 때 csrf보안기능으로 접근거부및 페이지가 표시되지 않을 때 설정
            /*
            .csrf((csrf) -> csrf
                .ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**")))
            .headers((headers) -> headers
                .addHeaderWriter(new XFrameOptionsHeaderWriter(
                    XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)))
           */
        	.formLogin((formLogin) -> formLogin
        			.loginPage("/user/login")  // 로그인 주소. 설정을 안하면 시큐리티가 기본으로 /login주소가 사용된다.
        			.defaultSuccessUrl("/"))   // 로그인 성공시 루트주소
        	.logout((logout) -> logout    // 로그아웃 설정작업으로 로그아웃기능은 처리가 된다.
                    .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                    .logoutSuccessUrl("/")  // 로그아웃 이후에 호출되는 루트주소 설정.
                    .invalidateHttpSession(true)) // 세션소멸작업
        ;
        return http.build();
    }

//	@Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//		// 모든 요청에 대하여 허용을 하겠다는 의미.
//		http
//			.csrf(AbstractHttpConfigurer::disable)  // csrf기능을 비활성화.
//			.authorizeHttpRequests(auth -> 
//                auth.requestMatchers("/**").permitAll()
//        );
//
//        return http.build();
//    }
	
	@Bean // passwordEncoder bean생성및 스프링컨테이너에 등록
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	// athenticationManager bean생성및 스프링컨테이너에 등록. 스프링 시큐리티 인증처리담당.
	// AuthenticationManager는 사용자 인증시 앞에서 작성한 UserSecurityService와 PasswordEncoder를 내부적으로 사용하여
	// 인증과 권한 부여 프로세스를 처리한다.
	@Bean 
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
	
}
