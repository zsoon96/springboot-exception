package com.example.exceptionprac.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

// Repository에서 복잡한 조회 쿼리를 작성하는 것은 유지보수 측면에서 좋지 않음
// 쿼리 메서드로 표현이 어려우며 @Query 어노테이션을 통해 작성된 쿼리는 type safe하지 않은 단점이 있음
// 이를 Querydsl로 해결하고 다형성을 통해 복잡한 쿼리의 세부 구현은 감추고, Repository를 통해 사용하도록 하는 것이 핵심 포인트!
public interface UserRepository extends JpaRepository<Users, Long>, UserCustomRepository, QuerydslPredicateExecutor<Users> {

    Users findByEmail(Email email);

    boolean existsByEmail(Email email);

    // boolean existsByUsername(String username)
    // boolean existsByPhoneNumboer(String number)

    // boolean existsByXXXX()와 같이 유사한 쿼리가 필요해지면 쿼리 메서드를 지속적으로 추가해야하는 단점이 있는데
    // 이 경우에, QuerydslPredicateExecutor를 사용하면 매우 효과적임!
        // QuerydslPredicateExecutor를 상속만 하면 JpaRepository를 통해 해당 구현부에 있는 findById, findAll과 같은 메서드를 사용할 수 있는 것처럼 QuerydslPredicateExecutor의 메서드들을 편리하게 사용할 수 있음
        // QuerydslPredicateExecutor 코드를 보면 Predicate를 매개변수로 받고 있기 때문에 Predicate를 통해 새로운 쿼리를 만들 수 있음
}
