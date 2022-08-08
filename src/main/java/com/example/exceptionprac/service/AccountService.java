package com.example.exceptionprac.service;

import com.example.exceptionprac.domain.UserRepository;
import com.example.exceptionprac.domain.Users;
import com.example.exceptionprac.dto.AccountDto;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final UserRepository userRepository;

    public AccountService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AccountDto.Res create (AccountDto.SignUpReq dto ) {
        String username = dto.getUsername();
        String password = dto.getPassword();
        String email = dto.getEmail();

        Users user = new Users(username, password, email);

        userRepository.save(user);

        return new AccountDto.Res(username, email);
    }

}
