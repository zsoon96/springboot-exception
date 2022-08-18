package com.example.exceptionprac.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

import com.example.exceptionprac.domain.Email;
import com.example.exceptionprac.domain.UserRepository;
import com.example.exceptionprac.domain.Users;
import com.example.exceptionprac.dto.AccountDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

// JUnit5에서 Mock 객체를 사용하기 위한 어노테이션
@ExtendWith(MockitoExtension.class)
class AccountServiceJUnit5Test {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private UserRepository userRepository;

    @Test
    // JUnit5의 가장 큰 특징이자 장점의 기능을 하는 어노테이션
    // @DisplayName을 통해 해당 테스트 시, 테스트 코드에 대한 설명을 문자열로 대체할 수 있는 기능
    @DisplayName("findById_존재하는경우_회원리턴")
    public void findBy_not_existed_test() {
        // given
        AccountDto.SignUpReq dto = buildSignUpReq();
        given(userRepository.findById(anyLong())).willReturn(Optional.of(dto.toEntity()));

        // when
        Users user = accountService.findById(anyLong());

        // then
        verify(userRepository, atLeastOnce()).findById(anyLong());
        assertThatEqual(dto, user);
    }

    public void assertThatEqual(AccountDto.SignUpReq dto, Users user) {
        assertThat(dto.getUsername(), is(user.getUsername()));
        assertThat(dto.getEmail().getEmail(), is(user.getEmail().getEmail()));
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
                .email("test1234@naver.com")
                .build();
    }
}