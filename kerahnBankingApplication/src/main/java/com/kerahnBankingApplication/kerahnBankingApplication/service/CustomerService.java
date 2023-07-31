package com.kerahnBankingApplication.kerahnBankingApplication.service;

import com.kerahnBankingApplication.kerahnBankingApplication.dto.CreditDebitRequest;
import com.kerahnBankingApplication.kerahnBankingApplication.dto.CustomerRequest;
import com.kerahnBankingApplication.kerahnBankingApplication.dto.Response;
import com.kerahnBankingApplication.kerahnBankingApplication.dto.TransferRequest;

import java.util.List;

public interface CustomerService {
    Response registerCustomer (CustomerRequest customerRequest);
    Response registerAdmin (CustomerRequest customerRequest);
    Response customerLogin (CustomerRequest customerRequest);
    Response adminLogin (CustomerRequest customerRequest);

    List<Response> listAllCustomers ();
    Response fetchCustomer (Long customerId);
    Response balanceEnquiry(String accountNumber);
    Response nameEnquiry(String accountNumber);
    Response updateCustomerProfile (CustomerRequest customerRequest);
    Response deleteCustomer (Long customerId);
    Response credit (CreditDebitRequest creditRequest);
    Response debit (CreditDebitRequest debitRequest);
    Response transfer (TransferRequest request);
}
