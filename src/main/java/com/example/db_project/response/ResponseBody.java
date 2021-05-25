package com.example.db_project.response;

public class ResponseBody<T> {
    private String message;
    private int errorCode;
    private T data;

    public ResponseBody(String message, int errorCode, T data) {
        this.message = message;
        this.errorCode = errorCode;
        this.data = data;
    }

    public ResponseBody(String message, int errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
