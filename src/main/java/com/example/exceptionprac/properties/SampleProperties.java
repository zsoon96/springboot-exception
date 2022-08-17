package com.example.exceptionprac.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

// Properties 설정값을 가져오는 방법 2 - ConfigurationProperties를 활용하여 POJO 객체(필요에 따라 재활용이 가능하도록 설계된 JAVA 객체)를 두는 방법
    // 해당 방법을 사용했을 때 아래와 같은 장점이 있음
    // 1. @Validated를 통해 유효성 검증을 간편하게 할 수 있다.
    // 2. @Component를 통해 빈으로 등록하기 때문에 재사용성이 높다.
    // 3. 재사용성 뿐만 아니라 user라는 속성값의 캡슐화, 응집력이 높아진다.(user에 대한 데이터들의 항목과 타입을 한 객체로 묶었기 때문)
@Configuration
@ConfigurationProperties(prefix = "user")
@Validated
@Getter
@Setter
public class SampleProperties {

    @Email (message = "@를 포함하여 올바른 이메일 형식을 적어주세요.")
    private String email;

    @NotEmpty (message = "반드시 값이 존재하고 길이 혹은 크기가 0보다 커야합니다.")
    private String nickname;

    private int age;

    private boolean auth;

    private double amount;
}
