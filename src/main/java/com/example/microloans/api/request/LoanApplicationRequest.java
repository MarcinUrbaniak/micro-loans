package com.example.microloans.api.request;

import com.example.microloans.common.WebUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public class LoanApplicationRequest {



    private Long userId;
    private BigDecimal ammount;
    private Long loanPeriod;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getAmmount() {
        return ammount;
    }

    public void setAmmount(BigDecimal ammount) {
        this.ammount = ammount;
    }

    public Long getLoanPeriod() {
        return loanPeriod;
    }

    public void setLoanPeriod(Long loanPeriod) {
        this.loanPeriod = loanPeriod;
    }

}
