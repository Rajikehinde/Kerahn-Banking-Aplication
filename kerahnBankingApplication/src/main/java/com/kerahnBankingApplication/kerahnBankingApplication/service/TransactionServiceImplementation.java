package com.kerahnBankingApplication.kerahnBankingApplication.service;

import com.kerahnBankingApplication.kerahnBankingApplication.dto.Response;
import com.kerahnBankingApplication.kerahnBankingApplication.dto.TransactionDto;
import com.kerahnBankingApplication.kerahnBankingApplication.dto.TransactionHistory;
import com.kerahnBankingApplication.kerahnBankingApplication.entity.Transaction;
import com.kerahnBankingApplication.kerahnBankingApplication.repository.TransactionRepository;
import lombok.Builder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class TransactionServiceImplementation implements TransactionService{
    private final TransactionRepository transactionRepository;

    public TransactionServiceImplementation(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void saveTransaction(TransactionDto transactionDto) {
        Transaction newTransaction = Transaction.builder()
                .transactionType(transactionDto.getTransactionType())
                .accountNumber(transactionDto.getAccountNumber())
                .amount(transactionDto.getAmount())
                .build();
        transactionRepository.save(newTransaction);
    }

    @Override
    public List<TransactionDto> getAllTransaction() {
        List <Transaction> transaction = transactionRepository.findAll();
        List<TransactionDto> response = new ArrayList<>();
        for (Transaction transaction1: transaction){
            response.add(TransactionDto.builder()
                    .transactionType(transaction1.getTransactionType())
                    .accountNumber(transaction1.getAccountNumber())
                    .amount(transaction1.getAmount())
                    .build());
        }
        return response;
    }
    @Override
    public List<TransactionDto> getByAccountNumber(TransactionHistory transactionHistory) {
        List<Transaction> transactionList = transactionRepository.findByAccountNumber(transactionHistory
                .getAccountNumber());
        List<TransactionDto> transactionDtoList = new ArrayList<>();

        for (Transaction transaction: transactionList){

            TransactionDto transactionDto = TransactionDto.builder()
                    .transactionType(transaction.getTransactionType())
                    .accountNumber(transaction.getAccountNumber())
                    .amount(transaction.getAmount())
                    .build();
            transactionDtoList.add(transactionDto);
        }
        return transactionDtoList;
    }
}
