package com.control.visitas.exceptions;

public class BusinessException extends RuntimeException {

    public BusinessException (String messaje){
        super(messaje);
    }

    public BusinessException (String messaje, Throwable cause){
        super(messaje, cause);
    }
}
