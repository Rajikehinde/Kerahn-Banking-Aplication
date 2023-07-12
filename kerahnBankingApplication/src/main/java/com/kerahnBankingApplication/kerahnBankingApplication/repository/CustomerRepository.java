package com.kerahnBankingApplication.kerahnBankingApplication.repository;

import com.kerahnBankingApplication.kerahnBankingApplication.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Boolean existsByEmail (String email);
    Customer findByEmail (String email);
    Boolean existsByAccountNumber(String accountNumber);
    Customer findByAccountNumber(String accountNumber);
}
