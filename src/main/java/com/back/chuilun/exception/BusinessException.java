package com.back.chuilun.exception;

public class BusinessException extends RuntimeException {

    public BusinessException(String message){
        super(message);
    }
}