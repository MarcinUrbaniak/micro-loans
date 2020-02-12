package com.example.microloans.controller;

import com.example.microloans.api.request.LoanApplicationRequest;
import com.example.microloans.api.response.LoanApplicationResponse;
import com.example.microloans.common.WebUtils;
import com.example.microloans.service.LoanApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;

@RestController
@RequestMapping("loan-application")
public class LoanApplicationController {

    private LoanApplicationService loanApplicationService;

    public LoanApplicationController(LoanApplicationService loanApplicationService) {
        this.loanApplicationService = loanApplicationService;
    }

    @PostMapping(value = "/apply", produces = "application/json")
    public ResponseEntity<LoanApplicationResponse> loanApplication(@RequestBody LoanApplicationRequest request) {
        String ipAddress = WebUtils.getClientIp();
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        return loanApplicationService.loanApplication(request, ipAddress, currentDate, currentTime);
    }

}
