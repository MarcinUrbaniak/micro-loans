package com.example.microloans.service.impl;

import com.example.microloans.api.request.LoanApplicationRequest;
import com.example.microloans.api.response.LoanApplicationResponse;
import com.example.microloans.common.MsgSource;
import com.example.microloans.exception.CommonRiskException;
import com.example.microloans.model.Loan;
import com.example.microloans.model.LoanApplication;
import com.example.microloans.model.UserAccount;
import com.example.microloans.model.type.LoanValueParameter;
import com.example.microloans.repository.LoanApplicationRepository;
import com.example.microloans.repository.UserAccountRepository;
import com.example.microloans.service.AbstractCommonService;
import com.example.microloans.service.LoanApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static com.example.microloans.risk.RiskEngine.*;

@Service
public class LoanApplicationServiceImpl extends AbstractCommonService implements LoanApplicationService {

    private LoanApplicationRepository loanApplicationRepository;
    private UserAccountRepository userAccountRepository;

    public LoanApplicationServiceImpl(MsgSource msgSource, LoanApplicationRepository loanApplicationRepository, UserAccountRepository userAccountRepository) {
        super(msgSource);
        this.loanApplicationRepository = loanApplicationRepository;
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    @Transactional
    public ResponseEntity<LoanApplicationResponse> loanApplication(LoanApplicationRequest request, String ipAddress, LocalDate date, LocalTime time) {

        LoanApplication loanApplication = addLoanApplicationToDataSource(request, ipAddress, date, time);

        if ((isIncorrectAmount(request.getAmount())
                && isIncorrectApplicationTime(loanApplication.getApplicationTime()))
                || (request.getAmount().compareTo(LoanValueParameter.MAX_VALUE.getValue()) > 0)
                || isThirdApplicationFromIp(ipReturningAddress(ipAddress))) {
            throw new CommonRiskException(msgSource.ERR001);
        }

        return ResponseEntity.ok(new LoanApplicationResponse(msgSource.OK002, loanApplication.getId()));
    }

    private LoanApplication addLoanApplicationToDataSource(LoanApplicationRequest request, String ipAddress, LocalDate date, LocalTime time) {

        UserAccount userAccount = checkUserIdInRepository(request);

        Loan loan = new Loan(null, request.getAmount(), request.getLoanPeriod(), LocalDate.now());
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
        if (!userAccountOptional.isPresent()) throw new CommonRiskException(msgSource.ERR001);
        return userAccountOptional.get();
    }

    private int ipReturningAddress(String ipAddress) {
        List<LoanApplication> loanApplications = loanApplicationRepository.findAllByIpAddressEquals(ipAddress);
        return loanApplications.size();
    }
}