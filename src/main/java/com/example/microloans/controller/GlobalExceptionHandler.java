package com.example.microloans.controller;

import com.example.microloans.api.response.BasicResponse;
import com.example.microloans.exception.CommonRiskException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    BasicResponse handleBadRequestException(CommonRiskException exception) {
        return BasicResponse.ofError(
                exception.getConstErrorMsg().getErrorCode(),
                exception.getConstErrorMsg().getErrorMsg()
        );
    }

}
