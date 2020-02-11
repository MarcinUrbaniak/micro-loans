package com.example.microloans.model;

import javax.persistence.*;
import java.net.InetAddress;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class LoanApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private InetAddress ipAddress;
    private LocalDate applicationDay;
    private LocalTime applicationTime;
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserAccount userAccount;
    @OneToOne
    @JoinColumn(name = "loan_id", referencedColumnName = "id")
    private Loan loan;





}
