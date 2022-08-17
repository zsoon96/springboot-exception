package com.example.exceptionprac.properties;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
@Slf4j
public class SamplePropertiesRunner implements ApplicationRunner {

    private SampleProperties properties;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String email = properties.getEmail();
        String nickname = properties.getNickname();
        int age = properties.getAge();
        boolean auth = properties.isAuth();
        double amount = properties.getAmount();

        log.info("======Anti======");
        log.info(email);
        log.info(nickname);
        log.info(String.valueOf(age));
        log.info(String.valueOf(auth));
        log.info(String.valueOf(amount));
        log.info("======Anti======");
    }
}
