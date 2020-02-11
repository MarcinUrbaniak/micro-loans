package com.example.microloans.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    @OneToMany(mappedBy = "userAccount", cascade = CascadeType.ALL)
    private Set<Loan> loans;



}
