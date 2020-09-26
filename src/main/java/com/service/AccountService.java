package com.service;

import com.model.Account;
import com.model.RequestReturn;

public interface AccountService {
	
	RequestReturn getAllAccounts();
	RequestReturn getAccountByAccountNumber(String accountNumber);
	RequestReturn createAccount(Account account);

}
