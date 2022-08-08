package com.example.exceptionprac.controller;

import com.example.exceptionprac.dto.AccountDto;
import com.example.exceptionprac.service.AccountService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserController {

    private final AccountService accountService;

    public UserController(AccountService accountService) {
        this.accountService = accountService;
    }

    // @Valid를 활용하여 유효성 검증이 실패했을 경우 > MethodArgumentNotValidException 발생
    @PostMapping("/signup")
    public AccountDto.Res signup (@RequestBody @Valid AccountDto.SignUpReq dto) {
        return accountService.create(dto);
    }
}
