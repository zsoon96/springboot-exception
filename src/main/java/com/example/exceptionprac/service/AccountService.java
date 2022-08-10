package com.example.exceptionprac.service;

import com.example.exceptionprac.common.AccountNotFoundException;
import com.example.exceptionprac.domain.Email;
import com.example.exceptionprac.domain.Password;
import com.example.exceptionprac.domain.UserRepository;
import com.example.exceptionprac.domain.Users;
import com.example.exceptionprac.dto.AccountDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {

    private final UserRepository userRepository;

    public AccountService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AccountDto.Res create (AccountDto.SignUpReq dto ) {
//        String username = dto.getUsername();
//        String password = dto.getPassword();
//        Email email = dto.getEmail();
//
//        Password dbPassword = new Password(password);
//
//        Users user = new Users(username, dbPassword, email);
//        userRepository.save(user);

        // 서비스에서는 도메인의 핵심 비즈니스 코드를 담당하는 역할이 아닌,
        // 데이터베이스 영역과 도메인 영역을 연결해주는 매개체임을 명심할 것 !!
        userRepository.save(dto.toEntity());

        return new AccountDto.Res(dto.getUsername(), dto.getEmail());
    }

    @Transactional(readOnly = true)
    public void findById(long id) {
        userRepository.findById(id).orElseThrow(() -> new AccountNotFoundException(id));
    }

}
