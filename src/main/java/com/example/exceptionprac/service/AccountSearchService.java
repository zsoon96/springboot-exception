package com.example.exceptionprac.service;

import com.example.exceptionprac.common.PageRequest;
import com.example.exceptionprac.domain.QUsers;
import com.example.exceptionprac.domain.Users;
import com.example.exceptionprac.dto.AccountSearchType;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// @QuerydslRepositorySupport를 이용하면 동적 쿼리를 쉽게 만들 수 있음
// 객체 기반으로 쿼리를 만들기 때문에 타입 안정성의 강점
@Service
@Transactional(readOnly = true)
public class AccountSearchService extends QuerydslRepositorySupport {

    // 기본 생성자를 통해 조회 대상 엔티티 클래스를 지정
    public AccountSearchService() {
        super(Users.class); // Users 엔티티에서 조회
    }

    // 컨트롤러에서 넘겨받은 type, value, pageable를 기반으로 동적 쿼리를 만드는 메서드
    public Page<Users> search(AccountSearchType type, String value, Pageable pageable) {
        QUsers user = QUsers.users; // 해당 객체 기반으로 동적 쿼리 작업
        JPQLQuery<Users> query;

        switch (type) {
            // 검색 type에 따라 해당 경우에 대한 쿼리가 동작함
            case EMAIL :
                query = from(user)
                        .where(user.email.email.likeIgnoreCase(value + "%")); // likeIgnoreCase를 통해 해당 value 값의 대소문자 무시
                break;

            case NAME :
                query = from(user)
                        .where(user.username.likeIgnoreCase(value + "%"));
                break;

            case ALL :
                query = from(user)
                        .fetchAll();
                break;

            default:
                throw new IllegalArgumentException();
        }

        // JPQLQuery로 데이터 조회
            // getQuerydsl()를 사용하여 pageable 객체의 page, limit, sort 값을 적용 > 적용된 쿼리를 이용하여 데이터 조회
            // getQuerydsl().applyPagination()로 스프링 데이터가 제공하는 페이징을 Querydsl로 편리하게 변환 가능 (단, sort는 오류 발생)
        List<Users> users = getQuerydsl().applyPagination(pageable, query).fetch();
        // PageImpl 객체로 리턴
        return new PageImpl<>(users, pageable, query.fetchCount());
    }
}
