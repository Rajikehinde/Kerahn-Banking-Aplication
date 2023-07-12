package com.kerahnBankingApplication.kerahnBankingApplication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@lombok.Data
public class Data {
    private String accountNumber;
    private String accountName;
    private BigDecimal accountBalance;
}
