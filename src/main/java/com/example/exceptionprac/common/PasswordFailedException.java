package com.example.exceptionprac.common;

import com.example.exceptionprac.error.ErrorCode;

public class PasswordFailedException extends RuntimeException{

    private ErrorCode errorCode;

    public PasswordFailedException() {
        this.errorCode = ErrorCode.PASSWORD_FAILED;
    }
}
