package com.example.microloans.service;

import com.example.microloans.api.request.CreateUserAccountRequest;
import com.example.microloans.api.response.CreateUserAccountResponse;
import org.springframework.http.ResponseEntity;

public interface UserAccountService {

    ResponseEntity<CreateUserAccountResponse> createUserAccount(CreateUserAccountRequest request);

}
