package com.example.exceptionprac.controller;

import com.example.exceptionprac.dto.AccountDto;
import com.example.exceptionprac.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api")
public class UserController {

    private final AccountService accountService;

    public UserController(AccountService accountService) {
        this.accountService = accountService;
    }

    // @Valid를 활용하여 유효성 검증이 실패했을 경우 > MethodArgumentNotValidException 발생
    @PostMapping("/signup/v1")
    public ResponseEntity signup (@RequestBody @Valid AccountDto.SignUpReq dto, BindingResult result) {

        // BindingResult를 통해 에러 발생시, 에러 내용을 body에 담아 응답
        if (result.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            result.getAllErrors().forEach(objectError -> {
                FieldError field = (FieldError) objectError;
                String message = objectError.getDefaultMessage();
                // 클라이언트 응답용 데이터
                sb.append("field: " + field.getField() + " ");
                sb.append("message: " + message).append("\n");
                // 서버 확인용 로그
                log.info("field: " + field.getField() + " ");
                log.info("message: " + message + "\n");
            });
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(sb.toString());
        }
        return ResponseEntity.ok(accountService.create(dto));
    }

    // @ControllerAdvice를 통한 예외처리
    // 성공 시, 아래 리턴 값 반환
    // 실패 시, ErrorExceptionController에서 예외 값 반환
    @PostMapping("signup/v2")
    public ResponseEntity signup (@RequestBody @Valid AccountDto.SignUpReq dto) {
        return ResponseEntity.ok(accountService.create(dto));
    }
}
