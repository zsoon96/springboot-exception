package com.example.exceptionprac.domain;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// 실제 Querydsl을 이용하여 UserSupportRepository의 세부 구현을 진행
// 해당 구현체를 통해 복잡한 쿼리를 구현시키고 UserRepository를 통해 마치 JpaRepository를 사용하는 것처럼 편리하게 사용
@Transactional(readOnly = true)
public class UserCustomRepositoryImpl extends QuerydslRepositorySupport implements UserCustomRepository {

    public UserCustomRepositoryImpl() {
        super(Users.class);
    }

    // 최근 가입한 limit 갯수 만큼 유저리스트 불러오기
    @Override
    public List<Users> findRecentlyRegistered(int limit) {
        // Querydsl에서는 엔티티로 설정된 클래스에 Qxxx라는 쿼리타입 클래스를 미리 생성해 놓고 메타데이터로 사용하여 쿼리를 메소드 기반으로 작성
        // 즉, Querydsl 프레임워크를 사용하면서 쿼리문을 작성하려면 Q타입의 클래스가 필요로함 ! (Q타입 클래스를 사용하는 방법 중에 아래는 기본 인스턴스를 사용한 방법)
        QUsers user = QUsers.users;
        return from(user)
                .limit(limit)
                .orderBy(user.username.desc())
                .fetch();
    }
}
