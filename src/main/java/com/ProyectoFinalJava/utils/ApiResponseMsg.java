package com.ProyectoFinalJava.utils;

public class ApiResponseMsg {

    private String message;
    private Object data;

    public ApiResponseMsg(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

}