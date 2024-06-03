package com.frodas.cine.admin.service.exception;

public class EmptyException extends RuntimeException {

    public EmptyException() {
    }

    public EmptyException(String message) {
        super(message);
    }

}
