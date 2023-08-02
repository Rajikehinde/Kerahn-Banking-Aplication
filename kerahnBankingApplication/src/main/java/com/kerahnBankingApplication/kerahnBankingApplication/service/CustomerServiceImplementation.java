package com.kerahnBankingApplication.kerahnBankingApplication.service;

import com.kerahnBankingApplication.kerahnBankingApplication.dto.*;
import com.kerahnBankingApplication.kerahnBankingApplication.email.dto.EmailDetails;
import com.kerahnBankingApplication.kerahnBankingApplication.email.service.EmailService;
import com.kerahnBankingApplication.kerahnBankingApplication.entity.Customer;
import com.kerahnBankingApplication.kerahnBankingApplication.entity.Roles;
import com.kerahnBankingApplication.kerahnBankingApplication.repository.CustomerRepository;
import com.kerahnBankingApplication.kerahnBankingApplication.repository.RoleRepository;
import com.kerahnBankingApplication.kerahnBankingApplication.security.Login;
import com.kerahnBankingApplication.kerahnBankingApplication.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImplementation implements CustomerService {
    @Autowired
    EmailService emailService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Response registerCustomer(CustomerRequest customerRequest) {
        //checking if customer already exists - if no create an account for customer
        Boolean isExists = customerRepository.existsByEmail(customerRequest.getEmail());
        if (isExists) {
            return Response.builder()
                    .responseCode(ResponseUtil.USER_EXISTS_CODE)
                    .responseMessage(ResponseUtil.USER_EXISTS_MESSAGE)
                    .data(null)
                    .build();
        }
        //Creating new customer account
        Customer customer = Customer.builder()
                .firstName(customerRequest.getFirstName())
                .lastName(customerRequest.getLastName())
                .otherName(customerRequest.getOtherName())
                .email(customerRequest.getEmail())
                .accountNumber(ResponseUtil.generateAccountNumber())
                .accountBalance(BigDecimal.ZERO)
                .username(customerRequest.getUsername())
                .city(customerRequest.getCity())
                .nextOfKin(customerRequest.getNextOfKin())
                .phoneNumber(customerRequest.getPhoneNumber())
                .stateOfOrigin(customerRequest.getStateOfOrigin())
                .dateOfBirth(customerRequest.getDateOfBirth())
                //added the password and roles because of security
                .password(passwordEncoder.encode(customerRequest.getPassword()))
                .status("Active")
                .build();
        //saving created account of customer to repository
        Roles role = roleRepository.findByRoleName("CUSTOMER").get();
        customer.setRoles(Collections.singleton(role));
        Customer saveCustomerInfo = customerRepository.save(customer);

        //appending email response to the account
        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(saveCustomerInfo.getEmail())
                .subject("Account Creation")
                .messageBody("CONGRATULATION! Your Account Has Been Successfully Created.\nYour Account Details: \n" +
                        "Account Name: " + saveCustomerInfo.getFirstName() + " " + saveCustomerInfo.getLastName() + " " + saveCustomerInfo.getOtherName() + "\n Account Number: " + saveCustomerInfo.getAccountNumber() + "\n Account Balance" + saveCustomerInfo.getAccountBalance())
                .build();
        emailService.sendSimpleEmail(emailDetails);

        //giving a response on created account
        return Response.builder()
                .responseCode(ResponseUtil.SUCCESS)
                .responseMessage(ResponseUtil.USER_REGISTERED_SUCCESS)
                .data(Data.builder()
                        .accountNumber(saveCustomerInfo.getAccountNumber())
                        .accountBalance(saveCustomerInfo.getAccountBalance())
                        .accountName(saveCustomerInfo.getFirstName() + " " + saveCustomerInfo.getLastName() + " " + saveCustomerInfo.getOtherName())
                        .build())
                .build();
    }

    @Override
    public Response registerAdmin(CustomerRequest customerRequest) {
        Boolean isExists = customerRepository.existsByEmail(customerRequest.getEmail());
        if (isExists) {
            return Response.builder()
                    .responseCode(ResponseUtil.USER_EXISTS_CODE)
                    .responseMessage(ResponseUtil.USER_EXISTS_MESSAGE)
                    .data(null)
                    .build();
        }
        //Creating new customer account
        Customer admin = Customer.builder()
                .firstName(customerRequest.getFirstName())
                .lastName(customerRequest.getLastName())
                .otherName(customerRequest.getOtherName())
                .email(customerRequest.getEmail())
                .username(customerRequest.getUsername())
                .accountNumber(ResponseUtil.generateAccountNumber())
                .accountBalance(BigDecimal.ZERO)
                .city(customerRequest.getCity())
                .nextOfKin(customerRequest.getNextOfKin())
                .phoneNumber(customerRequest.getPhoneNumber())
                .stateOfOrigin(customerRequest.getStateOfOrigin())
                .dateOfBirth(customerRequest.getDateOfBirth())
                //added the password and roles because of security
                .password(passwordEncoder.encode(customerRequest.getPassword()))
                .status("Active")
                .build();
        Roles role = roleRepository.findByRoleName("ADMIN").get();
        admin.setRoles(Collections.singleton(role));

        //saving created account of customer to repository
        Customer saveAdminInfo = customerRepository.save(admin);

        //appending email response to the account
        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(saveAdminInfo.getEmail())
                .subject("Account Creation")
                .messageBody("CONGRATULATION! Your Account Has Been Successfully Created.\nYour Account Details: \n" +
                        "Account Name: " + saveAdminInfo.getFirstName() + " " + saveAdminInfo.getLastName() + " " + saveAdminInfo.getOtherName() + "\n Account Number: " + saveAdminInfo.getAccountNumber() + "\n Account Balance" + saveAdminInfo.getAccountBalance())
                .build();
        emailService.sendSimpleEmail(emailDetails);

        //giving a response on created account
        return Response.builder()
                .responseCode(ResponseUtil.SUCCESS)
                .responseMessage(ResponseUtil.USER_REGISTERED_SUCCESS)
                .data(Data.builder()
                        .accountNumber(saveAdminInfo.getAccountNumber())
                        .accountBalance(saveAdminInfo.getAccountBalance())
                        .accountName(saveAdminInfo.getFirstName() + " " + saveAdminInfo.getLastName() + " " + saveAdminInfo.getOtherName())
                        .build())
                .build();
    }

    @Override
    // listing all customers existed in the bank
    public List<Response> listAllCustomers() {
        List<Customer> customerList = customerRepository.findAll();

        //response on all the customers and there details
        List<Response> responseList = new ArrayList<>();
        for (Customer customer : customerList) {
            responseList.add(Response.builder()
                    .responseCode(ResponseUtil.SUCCESS)
                    .responseMessage(ResponseUtil.SUCCESS_MESSAGE)
                    .data(Data.builder()
                            .accountName(customer.getFirstName() + " " + customer.getLastName() + " " + customer.getOtherName())
                            .accountBalance(customer.getAccountBalance())
                            .accountNumber(customer.getAccountNumber())
                            .build())
                    .build());
        }
        return responseList;
    }

    @Override
    //getting customers individually
    public Response fetchCustomer(Long customerId) {
        if (!customerRepository.existsById(customerId)) {
            return Response.builder()
                    .responseCode(ResponseUtil.USER_NOT_FOUND_CODE)
                    .responseMessage(ResponseUtil.USER_NOT_FOUND_MESSAGE)
                    .data(null)
                    .build();
        }
        Customer customer = customerRepository.findById(customerId).get();
        return Response.builder()
                .responseCode(ResponseUtil.USER_EXISTS_CODE)
                .responseMessage(ResponseUtil.USER_EXISTS_MESSAGE)
                .data(Data.builder()
                        .accountNumber(customer.getAccountNumber())
                        .accountBalance(customer.getAccountBalance())
                        .accountName(customer.getFirstName() + " " + customer.getLastName() + " " + customer.getOtherName())
                        .build())
                .build();
    }

    @Override
    //getting customer balance info in the bank
    public Response balanceEnquiry(String accountNumber) {
        boolean exists = customerRepository.existsByAccountNumber(accountNumber);
        if (!exists) {
            Response.builder()
                    .responseCode(ResponseUtil.USER_NOT_FOUND_CODE)
                    .responseMessage(ResponseUtil.USER_NOT_FOUND_MESSAGE)
                    .data(null)
                    .build();
        }
        Customer customer = customerRepository.findByAccountNumber(accountNumber);
        return Response.builder()
                .responseCode(ResponseUtil.SUCCESS)
                .responseMessage(ResponseUtil.SUCCESS_MESSAGE)
                .data(Data.builder()
                        .accountNumber(customer.getAccountNumber())
                        .accountBalance(customer.getAccountBalance())
                        .accountName(customer.getFirstName() + " " + customer.getLastName() + " " + customer.getOtherName())
                        .build())
                .build();
    }

    @Override
    //getting customer name info in the bank
    public Response nameEnquiry(String accountNumber) {
        boolean exists = customerRepository.existsByAccountNumber(accountNumber);
        if (!exists) {
            return Response.builder()
                    .responseCode(ResponseUtil.USER_NOT_FOUND_CODE)
                    .responseMessage(ResponseUtil.USER_NOT_FOUND_MESSAGE)
                    .data(null)
                    .build();
        }
        Customer customer = customerRepository.findByAccountNumber(accountNumber);
        return Response.builder()
                .responseCode(ResponseUtil.SUCCESS)
                .responseMessage(ResponseUtil.SUCCESS_MESSAGE)
                .data(Data.builder()
                        .accountNumber(customer.getAccountNumber())
                        .accountBalance(customer.getAccountBalance())
                        .accountName(customer.getFirstName() + " " + customer.getLastName() + " " + customer.getOtherName())
                        .build())
                .build();
    }

    @Override
    //updating customer info in the bank
    public Response updateCustomerProfile(CustomerRequest customerRequest) {
        boolean userExists = customerRepository.existsByEmail(customerRequest.getEmail());
        if (!userExists) {
            return Response.builder()
                    .responseCode(ResponseUtil.USER_NOT_FOUND_CODE)
                    .responseMessage(ResponseUtil.USER_NOT_FOUND_MESSAGE)
                    .data(null)
                    .build();
        }
        Customer customer = Customer.builder()
                .firstName(customerRequest.getFirstName())
                .lastName(customerRequest.getLastName())
                .otherName(customerRequest.getOtherName())
                .email(customerRequest.getEmail())
                .username(customerRequest.getUsername())
                //.accountNumber(ResponseUtil.generateAccountNumber(28973667))
                //.accountBalance(BigDecimal.ZERO)
                .city(customerRequest.getCity())
                .nextOfKin(customerRequest.getNextOfKin())
                .phoneNumber(customerRequest.getPhoneNumber())
                .stateOfOrigin(customerRequest.getStateOfOrigin())
                .dateOfBirth(customerRequest.getDateOfBirth())
                .status("Active")
                .build();
        Customer saveCustomerInfo = customerRepository.save(customer);
        //appending email response to the account
        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(saveCustomerInfo.getEmail())
                .subject("Account Creation")
                .messageBody("CONGRATULATION! Your Profile Has Been Successfully Updated.\nYour Account Details: \n" +
                        "Account Name: " + saveCustomerInfo.getFirstName() + " " + saveCustomerInfo.getLastName() + " " + saveCustomerInfo.getOtherName() + "\n Account Number: " + saveCustomerInfo.getAccountNumber() + "\n Account Balance" + saveCustomerInfo.getAccountBalance())
                .build();
        emailService.sendSimpleEmail(emailDetails);


        return Response.builder()
                .responseCode(ResponseUtil.SUCCESS)
                .responseMessage(ResponseUtil.SUCCESS_MESSAGE)
                .data(Data.builder()
                        .accountNumber(saveCustomerInfo.getAccountNumber())
                        .accountBalance(saveCustomerInfo.getAccountBalance())
                        .accountName(saveCustomerInfo.getFirstName() + " " + saveCustomerInfo.getLastName() + " " + saveCustomerInfo.getOtherName())
                        .build())
                .build();
    }

    @Override
    public Response deleteCustomer(Long customerId) {
        boolean userExists = customerRepository.existsById(customerId);
        if (!userExists) {
            return Response.builder()
                    .responseCode(ResponseUtil.USER_NOT_FOUND_CODE)
                    .responseMessage(ResponseUtil.USER_NOT_FOUND_MESSAGE)
                    .data(null)
                    .build();
        }
        Optional<Customer> customer = customerRepository.findById(customerId);
        customer.get().setDeleteStatus(true);
        Customer saveCustomerInfo = customerRepository.save(customer.get());

        return Response.builder()
                .responseCode(ResponseUtil.DELETED_SUCCESSFULLY_CODE)
                .responseMessage(ResponseUtil.DELETED_SUCCESSFULLY_MESSAGE)
                .build();
    }

    @Override
    public Response credit(CreditDebitRequest creditRequest) {
        boolean exists = customerRepository.existsByAccountNumber(creditRequest.getAccountNumber());
        if (!exists) {
            return Response.builder()
                    .responseCode(ResponseUtil.USER_NOT_FOUND_CODE)
                    .responseMessage(ResponseUtil.USER_NOT_FOUND_MESSAGE)
                    .data(null)
                    .build();
        }
        Customer customer = customerRepository.findByAccountNumber(creditRequest.getAccountNumber());
        customer.setAccountBalance(customer.getAccountBalance().add(creditRequest.getAmount()));
        Customer saveCustomerInfo = customerRepository.save(customer);
        //appending email response to the account
        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(saveCustomerInfo.getEmail())
                .subject("Account Creation")
                .messageBody("CONGRATULATION! Your Account Has Been Successfully Credited. \nYour Account Details: \n" +
                        "Account Name: " + saveCustomerInfo.getFirstName() + " " + saveCustomerInfo.getLastName() + " " + saveCustomerInfo.getOtherName() + "\n Account Number: " + saveCustomerInfo.getAccountNumber() + "\n Account Balance" + saveCustomerInfo.getAccountBalance())
                .build();
        emailService.sendSimpleEmail(emailDetails);

        transactionService.saveTransaction(TransactionDto.builder()
                .transactionType("credit")
                .accountNumber(saveCustomerInfo.getAccountNumber())
                .amount(creditRequest.getAmount())
                .build());

        return Response.builder()
                .responseCode(ResponseUtil.SUCCESSFUL_TRANSACTION)
                .responseMessage(ResponseUtil.ACCOUNT_CREDITED)
                .data(Data.builder()
                        .accountName(customer.getFirstName() + " " + customer.getLastName() + " " + customer.getOtherName())
                        .accountNumber(creditRequest.getAccountNumber())
                        .accountBalance(customer.getAccountBalance())
                        .build())
                .build();
    }

    @Override
    public Response debit(CreditDebitRequest debitRequest) {
        boolean exists = customerRepository.existsByAccountNumber(debitRequest.getAccountNumber());
        if (!exists) {
            return Response.builder()
                    .responseCode(ResponseUtil.USER_NOT_FOUND_CODE)
                    .responseMessage(ResponseUtil.USER_NOT_FOUND_MESSAGE)
                    .data(null)
                    .build();
        }
        Customer customer = customerRepository.findByAccountNumber(debitRequest.getAccountNumber());
        if (customer.getAccountBalance().compareTo(debitRequest.getAmount()) < 1) {
            return Response.builder()
                    .responseCode(ResponseUtil.USER_BALANCE_INSUFFICIENT)
                    .responseMessage(ResponseUtil.USER_BALANCE_INSUFFICIENT_MESSAGE)
                    .data(null)
                    .build();
        } else {
            customer.setAccountBalance(customer.getAccountBalance().subtract(debitRequest.getAmount()));
            Customer saveCustomerInfo = customerRepository.save(customer);
            //appending email response to the account
            EmailDetails emailDetails = EmailDetails.builder()
                    .recipient(saveCustomerInfo.getEmail())
                    .subject("Account Creation")
                    .messageBody("CONGRATULATION! Your Account Has Been Successfully Debited.\nYour Account Details: \n" +
                            "Account Name: " + saveCustomerInfo.getFirstName() + " " + saveCustomerInfo.getLastName() + " " + saveCustomerInfo.getOtherName() + "\n Account Number: " + saveCustomerInfo.getAccountNumber() + "\n Account Balance" + saveCustomerInfo.getAccountBalance())
                    .build();
            transactionService.saveTransaction(TransactionDto.builder()
                    .transactionType("debit")
                    .accountNumber(saveCustomerInfo.getAccountNumber())
                    .amount(debitRequest.getAmount())
                    .build());
            emailService.sendSimpleEmail(emailDetails);
            return Response.builder()
                    .responseCode(ResponseUtil.SUCCESSFUL_TRANSACTION)
                    .responseMessage(ResponseUtil.ACCOUNT_DEBITED)
                    .data(Data.builder()
                            .accountNumber(debitRequest.getAccountNumber())
                            .accountName(customer.getFirstName() + " " + customer.getLastName() + " " + customer.getOtherName())
                            .accountBalance(customer.getAccountBalance())
                            .build())
                    .build();
        }
    }

    @Override
    public Response transfer(TransferRequest request) {
        boolean isDestinationAccountExists = customerRepository.existsByAccountNumber(request.getDestinationAccountNumber());
        Customer sourceAccount = customerRepository.findByAccountNumber(request.getSourceAccountNumber());
        Customer destinationAccountNumber = customerRepository.findByAccountNumber(request.getDestinationAccountNumber());

        if (!isDestinationAccountExists) {
            return Response.builder()
                    .responseCode(ResponseUtil.USER_NOT_FOUND_CODE)
                    .responseMessage(ResponseUtil.USER_NOT_FOUND_MESSAGE)
                    .data(null)
                    .build();
        }

        if (sourceAccount.getAccountBalance().compareTo(request.getAmount()) < 0) {
            return Response.builder()
                    .responseCode(ResponseUtil.USER_BALANCE_INSUFFICIENT)
                    .responseMessage(ResponseUtil.USER_BALANCE_INSUFFICIENT_MESSAGE)
                    .data(Data.builder()
                            .accountName(sourceAccount.getFirstName() + " " + sourceAccount.getLastName() + " " + sourceAccount.getOtherName())
                            .accountNumber(sourceAccount.getAccountNumber())
                            .accountBalance(sourceAccount.getAccountBalance())
                            .build())
                    .build();
        } else {

            //debit transfer
            sourceAccount.setAccountBalance(sourceAccount.getAccountBalance().subtract(request.getAmount()));
            Customer saveCustomerInfo = customerRepository.save(sourceAccount);
            //appending email response to the account
            EmailDetails emailDetails = EmailDetails.builder()
                    .recipient(saveCustomerInfo.getEmail())
                    .subject("Account Creation")
                    .messageBody("CONGRATULATION! Your Account Has Been Successfully Debited.\nYour Account Details: \n" +
                            "Account Name: " + saveCustomerInfo.getFirstName() + " " + saveCustomerInfo.getLastName() + " " + saveCustomerInfo.getOtherName() + "\n Account Number: " + saveCustomerInfo.getAccountNumber() + "\n Account Balance: " + saveCustomerInfo.getAccountBalance())
                    .build();
            emailService.sendSimpleEmail(emailDetails);
            transactionService.saveTransaction(TransactionDto.builder()
                    .transactionType("debit")
                    .accountNumber(saveCustomerInfo.getAccountNumber())
                    .amount(request.getAmount())
                    .build());
            //credit transfer
            destinationAccountNumber.setAccountBalance(destinationAccountNumber.getAccountBalance().add(request.getAmount()));
            Customer saveCustomerInfo2 = customerRepository.save(destinationAccountNumber);
            //appending email response to the account
            EmailDetails emailDetails2 = EmailDetails.builder()
                    .recipient(saveCustomerInfo2.getEmail())
                    .subject("Account Creation")
                    .messageBody("CONGRATULATION! Your Account Has Been Successfully Credited.\nYour Account Details: \n" +
                            "Account Name: " + saveCustomerInfo2.getFirstName() + " " + saveCustomerInfo2.getLastName() + " " + saveCustomerInfo2.getOtherName() + "\n Account Number: " + saveCustomerInfo2.getAccountNumber() + "\n Account Balance: " + saveCustomerInfo2.getAccountBalance())
                    .build();
            emailService.sendSimpleEmail(emailDetails2);
            transactionService.saveTransaction(TransactionDto.builder()
                    .transactionType("credit")
                    .accountNumber(saveCustomerInfo.getAccountNumber())
                    .amount(request.getAmount())
                    .build());
            return Response.builder()
                    .responseCode(ResponseUtil.SUCCESS)
                    .responseMessage(ResponseUtil.SUCCESSFUL_TRANSFER_MESSAGE)
                    .data(null)
                    .build();
        }
    }
}
