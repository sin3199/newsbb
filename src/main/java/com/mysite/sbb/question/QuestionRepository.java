package com.mysite.sbb.question;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

// Question 엔티티를 이용하여 데이타 삽입,삭제,수정,조회 기능을 갖기위해서는 반드시 JpaRepository 상속받아야 한다.(규칙)
// 설명을 하자면, mybatis의 mapper interface 에 해당하는 성격이다.
public interface QuestionRepository extends JpaRepository<Question, Integer> {

	// 메서드명은 임의로 이름을 작성할 수가 없다. 조합규칙에 의거하여 메서드명을 만들어야 한다.
	Question findBySubject(String subject);
	
	Question findBySubjectAndContent(String subject, String content);
	
	List<Question> findBySubjectLike(String subject);
	
	Page<Question> findAll(Pageable pageable);
}
