package com.example.microloans.controller;

import com.example.microloans.api.request.ExtensionOfLoanRequest;
import com.example.microloans.api.response.ExtensionOfLoanResponse;
import com.example.microloans.service.LoanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("loan")
public class LoanController {

    LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping(value = "/extension", produces = "application/json")
    public ResponseEntity<ExtensionOfLoanResponse> extensionOfLoan(@RequestBody ExtensionOfLoanRequest request) {
        return loanService.extensionOfLoan(request);
    }

}
