package com.example.microloans.api.response;

public class ExtensionOfLoanResponse extends BasicResponse {

    private Long loanId;

    public ExtensionOfLoanResponse() {
    }

    public ExtensionOfLoanResponse(String responseMsg, Long loanId) {
        super(responseMsg);
        this.loanId = loanId;
    }

    public Long getLoanId() {
        return loanId;
    }

    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }
}
