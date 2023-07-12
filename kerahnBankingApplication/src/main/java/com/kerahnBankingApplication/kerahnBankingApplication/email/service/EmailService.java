package com.kerahnBankingApplication.kerahnBankingApplication.email.service;

import com.kerahnBankingApplication.kerahnBankingApplication.email.dto.EmailDetails;

public interface EmailService {
    String sendSimpleEmail(EmailDetails emailDetails);
    String sendEmailWithAttachment(EmailDetails emailDetails);

}
