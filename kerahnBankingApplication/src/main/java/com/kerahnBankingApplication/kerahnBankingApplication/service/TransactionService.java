package com.kerahnBankingApplication.kerahnBankingApplication.service;

import com.kerahnBankingApplication.kerahnBankingApplication.dto.TransactionDto;
import com.kerahnBankingApplication.kerahnBankingApplication.dto.TransactionHistory;
import com.kerahnBankingApplication.kerahnBankingApplication.entity.Transaction;

import java.util.List;

public interface TransactionService {
    void saveTransaction(TransactionDto transactionDto);
    List<TransactionDto> getAllTransaction();
    List <TransactionDto> getByAccountNumber(TransactionHistory transactionHistory);
}
