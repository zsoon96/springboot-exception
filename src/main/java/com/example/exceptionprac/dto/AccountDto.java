package com.example.exceptionprac.dto;

import com.example.exceptionprac.domain.Password;
import com.example.exceptionprac.domain.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.example.exceptionprac.domain.Email;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;


public class AccountDto {

    @Getter
    @NoArgsConstructor
    public static class SignUpReq {
        // @Valid 기능을 통한 유효성 검사
        @NotEmpty(message = "username에 Null값 또는 공백 문자열을 허용하지 않습니다.")
        private String username;
        @NotBlank(message = "password에 Null값 또는 문자 1개 이상 포함되어야 합니다.")
        private String password;
        //@Email (message = "@를 포함하여 올바른 이메일 형식을 적어주세요.")
        //private String email;
        @Valid // 해당 어노테이션 반드시 필요
        private Email email;

        // postman 테스트 시, Email은 String이 아닌 객체 타입으로 입력해줘야함 > 안그러면 400 에러가 반김
        // "email" : "email@naver.com: --> "email" : { "email" : "email@naver.com" }
        @Builder
        public SignUpReq(Email email, String username, String password) {
            this.email = email;
            this.username = username;
            this.password = password;
        }

        public Users toEntity() {
            return Users.builder()
                    .email(this.email)
                    .username(this.username)
                    .password(Password.builder().value(this.password).build())
                    .build();
        }
    }

        @Getter
        @NoArgsConstructor
        public static class Res {
            private String username;
            private Email email;

            public Res(String username, Email email) {
                this.username = username;
                this.email = email;
            }
        }
    }
