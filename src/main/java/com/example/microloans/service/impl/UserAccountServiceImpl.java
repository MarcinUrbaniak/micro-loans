package com.example.microloans.service.impl;

import com.example.microloans.api.request.CreateUserAccountRequest;
import com.example.microloans.api.response.CreateUserAccountResponse;
import com.example.microloans.common.MsgSource;
import com.example.microloans.model.UserAccount;
import com.example.microloans.repository.UserAccountRepository;
import com.example.microloans.service.AbstractCommonService;
import com.example.microloans.service.UserAccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserAccountServiceImpl extends AbstractCommonService implements UserAccountService {

    private UserAccountRepository userAccountRepository;

    public UserAccountServiceImpl(MsgSource msgSource, UserAccountRepository userAccountRepository) {
        super(msgSource);
        this.userAccountRepository = userAccountRepository;

    }

    @Override
    @Transactional
    public ResponseEntity<CreateUserAccountResponse> createUserAccount(CreateUserAccountRequest request) {
        UserAccount addedAccount = addUserAccountToDataSource(request);

        return ResponseEntity.ok(new CreateUserAccountResponse(msgSource.OK001, addedAccount.getId()));
    }

    private UserAccount addUserAccountToDataSource(CreateUserAccountRequest request){
        UserAccount userAccount = new UserAccount(
                null,
                request.getFirstName(),
                request.getLastName(),
                request.getEmail());
        return userAccountRepository.save(userAccount);
    }
}
