package com.kerahnBankingApplication.kerahnBankingApplication.repository;

import com.kerahnBankingApplication.kerahnBankingApplication.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
    List<Transaction> findByAccountNumber(String accountNumber);

}
