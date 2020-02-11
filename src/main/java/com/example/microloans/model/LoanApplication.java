package com.example.microloans.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.net.InetAddress;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class LoanApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String ipAddress;
    private LocalDate applicationDay;
    private LocalTime applicationTime;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserAccount userAccount;
    @OneToOne
    @JoinColumn(name = "loan_id", referencedColumnName = "id")
    private Loan loan;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public LocalDate getApplicationDay() {
        return applicationDay;
    }

    public void setApplicationDay(LocalDate applicationDay) {
        this.applicationDay = applicationDay;
    }

    public LocalTime getApplicationTime() {
        return applicationTime;
    }

    public void setApplicationTime(LocalTime applicationTime) {
        this.applicationTime = applicationTime;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }
}
