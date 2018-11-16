package com.information;

public class ReloginException extends HaltException{
    public ReloginException(String message) {
        super(402,message);
    }
}
