package com.example.exceptionprac.properties;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;


// Properties 설정값을 가져오는 방법 1 - Environment 활용하여 properties에 정의된 것을 key 값으로 가져오기 (일반적이고 가장 쉬운 방법)
    // 해당 방법이 간편하지만 아래와 같은 단점이 있음
    // 1. 정확한 자료형을 확인하기가 어렵다. (key 값으로 단순히 유추할 수 있는 정도일 뿐)
    // 2. 키값 변경 시 관리가 어렵다. (예를 들어, email > user-email로 변경하게 된다면 getProperty()를 통해 바인딩시킨 부분들 모두 변경해줘야하는 번거로움 발생)
@Component
@AllArgsConstructor
@Slf4j
public class AntiSamplePropertiesRunner implements ApplicationRunner {

    private final Environment env;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String email = env.getProperty("user.email");
        String nickname = env.getProperty("user.nickname");
        int age = Integer.parseInt(env.getProperty("user.age"));
        boolean auth = Boolean.valueOf(env.getProperty("user.auth"));
        int amount = Integer.parseInt(env.getProperty("user.amount"));

        log.info("======Anti======");
        log.info(email);
        log.info(nickname);
        log.info(String.valueOf(age));
        log.info(String.valueOf(auth));
        log.info(String.valueOf(amount));
        log.info("======Anti======");

    }
}
