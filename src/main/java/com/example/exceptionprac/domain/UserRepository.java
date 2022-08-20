package com.example.exceptionprac.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

// Repository에서 복잡한 조회 쿼리를 작성하는 것은 유지보수 측면에서 좋지 않음
// 쿼리 메서드로 표현이 어려우며 @Query 어노테이션을 통해 작성된 쿼리는 type safe하지 않은 단점이 있음
// 이를 Querydsl로 해결하고 다형성을 통해 복잡한 쿼리의 세부 구현은 감추고, Repository를 통해 사용하도록 하는 것이 핵심 포인트!
public interface UserRepository extends JpaRepository<Users, Long>, UserCustomRepository {

    Users findByEmail(Email email);

    boolean existsByEmail(Email email);
}
