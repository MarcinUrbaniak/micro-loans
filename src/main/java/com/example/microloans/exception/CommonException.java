package com.example.microloans.exception;


import com.example.microloans.common.ConstErrorMsg;

public class CommonException extends RuntimeException{

    private ConstErrorMsg constErrorMsg;

    public CommonException(ConstErrorMsg constErrorMsg) {
        this.constErrorMsg = constErrorMsg;
    }

    public ConstErrorMsg getConstErrorMsg() {
        return constErrorMsg;
    }
}
