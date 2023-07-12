package com.kerahnBankingApplication.kerahnBankingApplication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@lombok.Data
public class Response {
    private String responseCode;
    private String responseMessage;
    private Data data;
}
