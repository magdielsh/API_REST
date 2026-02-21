package com.control.visitas.exceptions;

public class InvalidParameterException extends BusinessException{

    private final String parameter;

    public InvalidParameterException(String parameter){
        super("");
        this.parameter = parameter;
    }

    public InvalidParameterException(String parameter, String messaje){
        super(messaje);
        this.parameter = parameter;
    }

    public String getParameter() {
        return parameter;
    }
}
