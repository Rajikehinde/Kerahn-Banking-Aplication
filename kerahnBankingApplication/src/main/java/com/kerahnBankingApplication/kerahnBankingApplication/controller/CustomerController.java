package com.kerahnBankingApplication.kerahnBankingApplication.controller;

import com.kerahnBankingApplication.kerahnBankingApplication.dto.*;
import com.kerahnBankingApplication.kerahnBankingApplication.service.AuthResponse;
import com.kerahnBankingApplication.kerahnBankingApplication.service.AuthServiceImpl;
import com.kerahnBankingApplication.kerahnBankingApplication.service.CustomerService;
import com.kerahnBankingApplication.kerahnBankingApplication.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomerController {
    private final TransactionService transactionService;

    private final CustomerService customerService;

    public CustomerController(TransactionService transactionService, CustomerService customerService) {
        this.transactionService = transactionService;
        this.customerService = customerService;
    }

    @PostMapping("/customer/registration")
    public Response registerCustomer(@RequestBody CustomerRequest customerRequest){
        return customerService.registerCustomer(customerRequest);
    }
    @PostMapping("/admin/registration")
    public Response registerAdmin(@RequestBody CustomerRequest customerRequest){
        return customerService.registerAdmin(customerRequest);
    }
    //@PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/all/customers")
//    @PreAuthorize("ADMIN")
    List<Response> listAllCustomers (){
        return customerService.listAllCustomers();
    }
    @GetMapping("/userFetching/{userFetching}")
    Response fetchCustomer (@PathVariable("userFetching") Long customerId){
        return customerService.fetchCustomer(customerId);
    }
//    @GetMapping("/{BalanceEnquiry}")
//    Response balanceEnquiry(@PathVariable("BalanceEnquiry") String accountNumber){
//        return customerService.balanceEnquiry(accountNumber);
//    }
//    @GetMapping("/{NameEnquiryCheck}")
//    Response nameEnquiry(@PathVariable("NameEnquiryCheck") String accountNumber){
//        return customerService.nameEnquiry(accountNumber);
//    }
    @PutMapping("/update/user/profile")
    Response updateCustomerProfile (@RequestBody CustomerRequest customerRequest){
        return customerService.updateCustomerProfile(customerRequest);
    }
    @DeleteMapping("/delete/{deleteUser}")
    //@PreAuthorize("ADMIN")
    Response deleteCustomer (@PathVariable("deleteUser") Long customerId){
        return customerService.deleteCustomer(customerId);
    }
    @PostMapping("/credit/user")
    Response credit (@RequestBody CreditDebitRequest creditRequest){
        return customerService.credit(creditRequest);
    }
    @PostMapping("/debit/user/account")
    Response debit (@RequestBody CreditDebitRequest debitRequest){
        return customerService.debit(debitRequest);
    }
    @PostMapping("transfer")
    public Response transfer(@RequestBody TransferRequest request){
        return customerService.transfer(request);
    }
    @PostMapping("/save/transaction/details")
    public void saveTransaction(@RequestBody TransactionDto transactionDto){
        transactionService.saveTransaction(transactionDto);
    }
    @GetMapping("/list/all/transaction/history")
    public List<TransactionDto> getAllTransaction(){
        return transactionService.getAllTransaction();
    }
    @GetMapping("/single/transaction/history")
    public List<TransactionDto> fetchTransactionsByAccountNumber(@RequestBody TransactionHistory transactionHistory){
        return transactionService.getByAccountNumber(transactionHistory);
    }
}
