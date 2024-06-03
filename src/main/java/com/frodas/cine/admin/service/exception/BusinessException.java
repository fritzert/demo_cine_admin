package com.frodas.cine.admin.service.exception;

public class BusinessException extends RuntimeException {

    public BusinessException() {
    }

    public BusinessException(String exception) {
        super(exception);
    }

}
