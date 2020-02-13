package com.gupaoedu.service.exception;


import lombok.Data;

@Data
public class BusinessException extends  RuntimeException {

     private String  code;
    public BusinessException() {
        super();

    }
    public BusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String code) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
    }

    public BusinessException(String code,String message) {
        super(message);
        this.code = code;
    }
}
