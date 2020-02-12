package com.example.microloans.service;

import com.example.microloans.api.request.ExtensionOfLoanRequest;
import com.example.microloans.api.response.ExtensionOfLoanResponse;
import org.springframework.http.ResponseEntity;

public interface LoanService {

    ResponseEntity<ExtensionOfLoanResponse> extensionOfLoan(ExtensionOfLoanRequest request);
}
