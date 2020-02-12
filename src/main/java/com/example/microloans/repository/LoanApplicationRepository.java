package com.example.microloans.repository;

import com.example.microloans.model.LoanApplication;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface LoanApplicationRepository extends CrudRepository<LoanApplication, Long> {
    List<LoanApplication> findAllByIpAddressEquals(String ipAddress);
}
