package com.kerahnBankingApplication.kerahnBankingApplication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerRequest {
    private String firstName;
    private String lastName;
    private String otherName;
    private String email;
    private BigDecimal accountBalance;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private String nextOfKin;
    private String stateOfOrigin;
    private String city;
}
