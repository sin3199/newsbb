package com.mysite.sbb.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

// 기본 CRUD기능을 위한 메서드는 존재.
public interface UserRepository extends JpaRepository<SiteUser, Long> {

	// 아이디 조회
	Optional<SiteUser> findByusername(String username);
}
