package com.gupaoedu.service.exception;


import lombok.Data;

@Data
public class AopException extends  RuntimeException {

     private String  code;
    public AopException() {
        super();

    }
    public AopException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String code) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
    }

    public AopException(String code, String message) {
        super(message);
        this.code = code;
    }
}
