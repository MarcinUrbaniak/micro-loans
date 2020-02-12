package com.example.microloans.exception;

import com.example.microloans.common.ConstErrorMsg;

public class CommonRiskException extends CommonException {

    public CommonRiskException(ConstErrorMsg constErrorMsg) {
        super(constErrorMsg);
    }
}
