package com.example.microloans.model;

import com.example.microloans.model.type.LoanStatus;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private BigDecimal amount;
    private Long loanPeriod;
    @Enumerated(EnumType.STRING)
    private LoanStatus loanStatus;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isDeferral;

    @ManyToOne(cascade = CascadeType.ALL)
    private UserAccount userAccount;


    public Loan() {
    }

    public Loan(Long id, BigDecimal ammount, Long loanPeriod, LoanStatus status, LocalDate startDate){
        this.id = id;
        this.amount = ammount;
        this.loanPeriod = loanPeriod;
        this.loanStatus = status;
        this.startDate = startDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LoanStatus getLoanStatus() {
        return loanStatus;
    }

    public void setLoanStatus(LoanStatus loanStatus) {
        this.loanStatus = loanStatus;
    }

    public boolean isDeferral() {
        return isDeferral;
    }

    public void setDeferral(boolean deferral) {
        isDeferral = deferral;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    //    public LoanApplication getLoanApplication() {
//        return loanApplication;
//    }
//
//    public void setLoanApplication(LoanApplication loanApplication) {
//        this.loanApplication = loanApplication;
//    }
}
