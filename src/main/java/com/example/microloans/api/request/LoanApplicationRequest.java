package com.example.microloans.api.request;

import java.math.BigDecimal;

public class LoanApplicationRequest {



    private Long userId;
    private BigDecimal amount;
    private Long loanPeriod;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getLoanPeriod() {
        return loanPeriod;
    }

    public void setLoanPeriod(Long loanPeriod) {
        this.loanPeriod = loanPeriod;
    }

}
