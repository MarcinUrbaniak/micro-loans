package com.example.microloans.controller;

import com.example.microloans.api.request.CreateUserAccountRequest;
import com.example.microloans.api.response.CreateUserAccountResponse;
import com.example.microloans.service.UserAccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("user-account")
public class UserAccountController {

    UserAccountService userAccountService;

    public UserAccountController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @PostMapping(value = "/create", produces = "application/json")
    public ResponseEntity<CreateUserAccountResponse> createUserAccount(@RequestBody CreateUserAccountRequest request) {
        return userAccountService.createUserAccount(request);
    }
}
