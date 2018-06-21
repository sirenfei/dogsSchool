package com.dog.school.dto;

import java.io.Serializable;

public class Results<T> implements Serializable {
    private static final long serialVersionUID = 910720702535427268L;
    public static final String SUCC = "Success";
    public static final String FAIL = "Failed";
    public static final String TOO_LONG = "character too long";
    
    public Results() {
        super();
    }
    
    public Results(String message, T data) {
        super();
        this.message = message;
        this.data = data;
    }
    
    private String message;
    private T data;

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }
    
}
