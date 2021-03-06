package com.woniu.yujiaweb.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result<T> implements Serializable {
    private boolean flag;
    private int statusCode;
    private String message;
    private T data;

    public Result(boolean flag, int statusCode, String message) {
        this.flag = flag;
        this.statusCode = statusCode;
        this.message = message;
    }

    public Result(boolean flag, int statusCode, String message, T data) {
        this.flag = flag;
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    public Result() {
    }
}
