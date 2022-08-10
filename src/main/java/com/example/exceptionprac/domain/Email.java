package com.example.exceptionprac.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

// @Embeddable을 통해 새로운 값 타입을 직접 정의하여 사용할 수 있음
// 속성의 클래스를 생성 후, 기존 테이블의 email 컬럼에 맵핑 (Users > @Embedded)
// 이메일에 대한 유효성 검사는 해당 클래스에서 관리하게 되므로서 유지보수가 용이해짐!
@Embeddable
public class Email {
    @javax.validation.constraints.Email (message = "@를 포함하여 올바른 이메일 형식을 적어주세요.")
    @Column(name = "email", nullable = false, unique = true)
    private String email;
}
