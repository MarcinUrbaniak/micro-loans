package com.example.microloans.service.impl;

import com.example.microloans.api.request.ExtensionOfLoanRequest;
import com.example.microloans.api.response.ExtensionOfLoanResponse;
import com.example.microloans.common.MsgSource;
import com.example.microloans.exception.CommonRiskException;
import com.example.microloans.model.Loan;
import com.example.microloans.repository.LoanRepository;
import com.example.microloans.service.AbstractCommonService;
import com.example.microloans.service.LoanService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static com.example.microloans.risk.RiskEngine.*;

import java.util.Optional;

@Service
public class LoanServiceImpl extends AbstractCommonService implements LoanService {

    private LoanRepository loanRepository;

    public LoanServiceImpl(MsgSource msgSource, LoanRepository loanRepository) {
        super(msgSource);
        this.loanRepository = loanRepository;
    }

    @Override
    public ResponseEntity<ExtensionOfLoanResponse> extensionOfLoan(ExtensionOfLoanRequest request) {
        Loan loan = findLoanById(request.getLoanId());
        if (loan.isDeferral() || !isCorrectExtension(request.getExtension()))
            throw new CommonRiskException(msgSource.ERR001);

        loan.setDeferral(true);
        loan.setEndDate(loan.getEndDate().plusDays(request.getExtension()));
        loanRepository.save(loan);

        return ResponseEntity.ok(new ExtensionOfLoanResponse(msgSource.OK003, loan.getId()));
    }

    private Loan findLoanById(Long id) {
        Optional<Loan> loanOptional = loanRepository.findById(id);
        if (!loanOptional.isPresent()) throw new CommonRiskException(msgSource.ERR001);
        return loanOptional.get();
    }
}
