package com.kerahnBankingApplication.kerahnBankingApplication.util;

import java.time.Year;
import java.util.Random;

public class ResponseUtil {
    public static final String USER_EXISTS_CODE = "001";
    public static final String USER_EXISTS_MESSAGE = "User with provided email already exists";
    public static final String SUCCESS = "002";
    public static final String USER_REGISTERED_SUCCESS = "Success";
    public static final String SUCCESS_MESSAGE = "Successfully done!";
    public static final String USER_NOT_FOUND_MESSAGE = "This user does not exist";
    public static final String USER_NOT_FOUND_CODE = "003";

    public static final String SUCCESSFUL_TRANSACTION = "004";
    public static final String ACCOUNT_CREDITED = "Account has been credited";
    public static final String ACCOUNT_DEBITED = "Account has been debited";
    public static final String SUCCESSFUL_TRANSFER_MESSAGE = "Transfer Successful";
    public static final String USER_BALANCE_INSUFFICIENT = "005";
    public static final String USER_BALANCE_INSUFFICIENT_MESSAGE = "Balance insufficient";
    public static final String DELETED_SUCCESSFULLY_CODE = "006";
    public static final String DELETED_SUCCESSFULLY_MESSAGE = "Account deleted successfully";




    public static final int LENGTH_OF_ACCOUNT_NUMBER = 10;

    public  static String generateAccountNumber(int len){
    Year currentYear = Year.now();

    int max =  999999;
    int min = 100000;

    int random = (int) Math.floor(Math.random() * (max - min - 1) - min);

    String year = String.valueOf(currentYear);
    String randomNumber = String.valueOf(random);

    StringBuilder accountNumber = new StringBuilder();
    return accountNumber.append(year).append(randomNumber).toString();

//        String accountNumber = "";
//        int x;
//        char[] stringChars = new char[len];
//
//        for (int i = 0; i < len; i++) {
//            Random random = new Random();
//            x = random.nextInt(9);
//            stringChars[i] = Integer.toString(x).toCharArray()[0];
//        }
//        accountNumber = new String(stringChars);
//        return accountNumber.trim();
    }

}
