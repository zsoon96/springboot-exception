package com.example.exceptionprac.domain;

import com.example.exceptionprac.dto.AccountDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@RunWith(SpringRunner.class)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private QUsers qUsers = QUsers.users;

    @Test
    public void findByEmail_test() {
        // 실행과 동시에 작성되는 SQL문이 없기 때문에 User 객체 저장하는 부분 별도 추가
        AccountDto.SignUpReq dto = buildSignUpReq();
        userRepository.save(dto.toEntity());

        // String email = "test@test.com";
        Users user = userRepository.findByEmail(Email.of(dto.getEmail().getEmail()));
        assertThat(user.getEmail().getEmail()).isEqualTo(dto.getEmail().getEmail());
    }

    @Test
    public void findById_test() {
        AccountDto.SignUpReq dto = buildSignUpReq();
        userRepository.save(dto.toEntity());

        Optional<Users> optionalUser = userRepository.findById(1L);
        Users user = optionalUser.get();
        assertThat(user.getId()).isEqualTo(1L);
    }

    @Test
    public void isExistedEmail_test() {
        AccountDto.SignUpReq dto = buildSignUpReq();
        userRepository.save(dto.toEntity());

        // String email = "test@test.com";
        boolean existsByEmail = userRepository.existsByEmail(Email.of(dto.getEmail().getEmail()));
        assertThat(existsByEmail).isTrue();
    }

    @Test
    public void findRecentlyRegistered_test() {
        List<Users> users = userRepository.findRecentlyRegistered(10);
        assertThat(users.size()).isLessThan(11);
    }

    public AccountDto.SignUpReq buildSignUpReq() {
        return AccountDto.SignUpReq.builder()
                .email(buildEmail())
                .username("soon")
                .password("soon1234")
                .build();
    }

    public Email buildEmail() {
        return Email.builder()
                .email("test@test.com")
                .build();
    }
}