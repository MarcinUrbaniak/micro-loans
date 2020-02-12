package com.example.microloans.common;

public class ConstErrorMsg {

    private final String errorCode;
    private final String errorMsg;

    public ConstErrorMsg(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
