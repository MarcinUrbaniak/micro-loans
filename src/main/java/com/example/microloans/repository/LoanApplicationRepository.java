package com.example.microloans.repository;

import com.example.microloans.model.LoanApplication;
import org.springframework.data.repository.CrudRepository;

public interface LoanApplicationRepository extends CrudRepository<LoanApplication, Long> {
}
