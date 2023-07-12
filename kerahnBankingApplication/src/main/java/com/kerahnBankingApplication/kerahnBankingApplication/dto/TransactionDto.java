package com.kerahnBankingApplication.kerahnBankingApplication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@lombok.Data
public class TransactionDto {
    private String transactionType;
    private String accountNumber;
    private BigDecimal amount;
}
