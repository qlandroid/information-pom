package com.information;

public class HaltException extends RuntimeException{
    private int code = 100;
    public HaltException(String message) {
        super(message);
    }
    public HaltException(int code ,String message) {
        super(message);
        this.code = code;
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
