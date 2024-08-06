package com.fininco.finincoserver.global.exception;

public abstract class BusinessException extends RuntimeException {
    private final ErrorCode errorCode;

    protected BusinessException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

}
