package com.example.exceptionprac.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;


public class AccountDto {

    @Getter
    @NoArgsConstructor
    public static class SignUpReq {
        // @Valid 기능을 통한 유효성 검사
        @NotEmpty // null 허용 X + 공백 문자열 허용 X
        private String username;
        @NotBlank // null 허용 X + 문자 1개 이상 포함(공백 제외)
        private String password;
        @Email // @이 포함된 올바른 이메일 형식
        private String email;
    }

    @Getter
    @NoArgsConstructor
    public static class Res {
        private String username;
        private String email;

        public Res(String username, String email) {
            this.username = username;
            this.email = email;
        }
    }

}
