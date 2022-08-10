package com.example.exceptionprac.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    @Embedded
    private com.example.exceptionprac.domain.Email email;

    @Builder
    public Users (String username, String password, com.example.exceptionprac.domain.Email email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
