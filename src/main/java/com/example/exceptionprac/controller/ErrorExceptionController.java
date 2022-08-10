package com.example.exceptionprac.controller;

import com.example.exceptionprac.common.AccountNotFoundException;
import com.example.exceptionprac.error.ErrorCode;
import com.example.exceptionprac.error.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

// @RestControllerAdvice (ResponseBody + ControllerAdvice) 사용 시,
// 특정 Exception을 핸들링하여 적절한 값을 Response 값으로 리턴해줌
    // 모든 @RestController에 대해 전역적으로 발생할 수 있는 예외를 잡아서 처리하는 역할
@RestControllerAdvice
@Slf4j
public class ErrorExceptionController {

    // @ExceptionHandler 사용 시, 해당 예외가 발생했을 때 메서드에 정의한 로직으로 예외 처리
    // @ControllerAdvice 또는 @RestControllerAdvice에 정의된 메서드가 아닌 일반 Controller단에 존재하는 메서드에 선언할 경우, 해당 Controller에만 적용
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    // 유효성 검증 실패 시, 예외처리
    protected ErrorResponse MethodArgumentNotValidException(MethodArgumentNotValidException e) {
        final BindingResult bindingResult = e.getBindingResult();
        final List<FieldError> errors = bindingResult.getFieldErrors();

        return buildFieldErrors(
                ErrorCode.INPUT_VALUE_INVALID,

                // ErrorResponse 내부 클래스인 FieldError에서 필요한 속성만 담아주기
                errors.parallelStream()
                        .map(error -> ErrorResponse.FieldError.builder()
                                .reason(error.getDefaultMessage())
                                .field(error.getField())
                                .value((String) error.getRejectedValue())
                                .build())
                        .collect(Collectors.toList())
        );
    }

    // 에러 메세지 내용 중, 필요한 속성만 담긴 ErrorResponse 클래스를 통해 공통적인 예외 값만 갖도록 담아주는 메서드
    private ErrorResponse buildFieldErrors(ErrorCode errorCode, List<ErrorResponse.FieldError> errors) {
        return ErrorResponse.builder()
                .code(errorCode.getCode())
                .status(errorCode.getStatus())
                .message(errorCode.getMessage())
                .errors(errors)
                .build();
    }

    // 회원정보 조회 실패 시, 에러처리
    @ExceptionHandler(value = {AccountNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ErrorResponse AccountNotFoundException(AccountNotFoundException e) {
        ErrorCode accountNotFound = ErrorCode.ACCOUNT_NOT_FOUND;
        log.error(accountNotFound.getMessage(), e.getId());
        return buildError(accountNotFound);
    }

    private ErrorResponse buildError(ErrorCode errorCode) {
        return ErrorResponse.builder()
                .code(errorCode.getCode())
                .status(errorCode.getStatus())
                .message(errorCode.getMessage())
                .build();
    }

}
