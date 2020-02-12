package com.example.microloans.service;

import com.example.microloans.api.request.CreateUserAccountRequest;
import com.example.microloans.api.request.LoanApplicationRequest;
import com.example.microloans.api.response.CreateUserAccountResponse;
import com.example.microloans.api.response.LoanApplicationResponse;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalTime;

public interface LoanApplicationService {
    ResponseEntity<LoanApplicationResponse> loanApplication(LoanApplicationRequest request, String ipAddress, LocalDate date, LocalTime time);
}
