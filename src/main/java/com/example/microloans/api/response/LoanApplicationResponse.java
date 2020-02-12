package com.example.microloans.api.response;

public class LoanApplicationResponse extends BasicResponse {

    private Long loanApplicationId;


    public LoanApplicationResponse(String responseMsg, Long loanApplicationId) {
        super(responseMsg);
        this.loanApplicationId = loanApplicationId;
    }

    public Long getLoanApplicationId() {
        return loanApplicationId;
    }

    public void setLoanApplicationId(Long loanApplicationId) {
        this.loanApplicationId = loanApplicationId;
    }
}
