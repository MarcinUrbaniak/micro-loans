package com.example.microloans.model;

import com.example.microloans.model.type.LoanStatus;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private BigDecimal amount;
    private Long loanPeriod;
    @Enumerated(EnumType.STRING)
    private LoanStatus loanStatus;
    private boolean isDeferral;

    @ManyToOne(cascade = CascadeType.ALL)
    private UserAccount userAccount;




}
