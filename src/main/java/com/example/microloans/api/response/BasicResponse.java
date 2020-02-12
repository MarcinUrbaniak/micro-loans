package com.example.microloans.api.response;

import com.example.microloans.api.response.type.ResponseStatus;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BasicResponse {

    private String responseMsg;
    private String rejectCode;
    private String riskMsg;
    private ResponseStatus status;

    public BasicResponse() {
    }
    public BasicResponse(String responseMsg) {
        this.responseMsg = responseMsg;
        this.status = ResponseStatus.ACCEPTED;
    }

    public BasicResponse(String rejectCode, String riskMsg) {
        this.rejectCode = rejectCode;
        this.riskMsg = riskMsg;
        this.status = ResponseStatus.REJECTED;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }

    public String getRejectCode() {
        return rejectCode;
    }

    public void setRejectCode(String rejectCode) {
        this.rejectCode = rejectCode;
    }

    public String getRiskMsg() {
        return riskMsg;
    }

    public void setRiskMsg(String riskMsg) {
        this.riskMsg = riskMsg;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

    public static BasicResponse of(String responseMsg) {
        return new BasicResponse(responseMsg);
    }

    public static BasicResponse ofError(String errorCode, String errorMsg){
        return new BasicResponse(errorCode, errorMsg);
    }
}
