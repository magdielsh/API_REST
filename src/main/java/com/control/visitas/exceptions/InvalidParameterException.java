package com.control.visitas.exceptions;

public class MethodArgumentTypeMismatchException extends BusinessException{

    private final String parameter;

    public MethodArgumentTypeMismatchException(String parameter){
        super("");
        this.parameter = parameter;
    }

    public MethodArgumentTypeMismatchException (String parameter, String messaje){
        super(messaje);
        this.parameter = parameter;
    }

    public String getParameter() {
        return parameter;
    }
}
