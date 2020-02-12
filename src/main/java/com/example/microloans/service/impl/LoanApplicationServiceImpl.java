package com.example.microloans.service.impl;

import com.example.microloans.api.request.LoanApplicationRequest;
import com.example.microloans.api.response.LoanApplicationResponse;
import com.example.microloans.common.MsgSource;
import com.example.microloans.exception.CommonException;
import com.example.microloans.model.Loan;
import com.example.microloans.model.LoanApplication;
import com.example.microloans.model.UserAccount;
import com.example.microloans.model.type.LoanStatus;
import com.example.microloans.repository.LoanApplicationRepository;
import com.example.microloans.repository.UserAccountRepository;
import com.example.microloans.service.AbstractCommonService;
import com.example.microloans.service.LoanApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Service
public class LoanApplicationServiceImpl extends AbstractCommonService implements LoanApplicationService {

    private LoanApplicationRepository loanApplicationRepository;
    private UserAccountRepository userAccountRepository;


    public LoanApplicationServiceImpl(MsgSource msgSource, LoanApplicationRepository loanApplicationRepository,
                                      UserAccountRepository userAccountRepository) {
        super(msgSource);
        this.loanApplicationRepository = loanApplicationRepository;
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    @Transactional
    public ResponseEntity<LoanApplicationResponse> loanApplication(LoanApplicationRequest request, String ipAddress
            ,LocalDate date, LocalTime time) {
        LoanApplication loanApplication = addLoanApplicationToDataSource(request, ipAddress, date, time);

        return ResponseEntity.ok(new LoanApplicationResponse(msgSource.OK002, loanApplication.getId()));
    }


    private LoanApplication addLoanApplicationToDataSource(LoanApplicationRequest request,
                                                           String ipAddress,
                                                           LocalDate date,
                                                           LocalTime time){

        UserAccount userAccount = checkUserIdInRepository(request);

        Loan loan = new Loan(null, request.getAmmount(), request.getLoanPeriod(), LoanStatus.NEW,LocalDate.now() );
        loan.setDeferral(false);
        loan.setEndDate(loan.getStartDate().plusDays(loan.getLoanPeriod()));
        loan.setUserAccount(userAccount);

        LoanApplication loanApplication = new LoanApplication();
        loanApplication.setApplicationDay(date);
        loanApplication.setApplicationTime(time);
        loanApplication.setIpAddress(ipAddress);
        loanApplication.setLoan(loan);
        loanApplication.setUserAccount(userAccount);

        return loanApplicationRepository.save(loanApplication);
    }

    private UserAccount checkUserIdInRepository(LoanApplicationRequest request) {
        Optional<UserAccount> userAccountOptional = userAccountRepository.findById(request.getUserId());
        if(!userAccountOptional.isPresent()) throw new CommonException(msgSource.ERR001);
        return userAccountOptional.get();
    }
}
