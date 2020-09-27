package com.service;

import java.util.List;

import com.exceptions.AlreadyExistsException;
import com.exceptions.InvalidJsonException;
import com.exceptions.NotFoundException;
import com.model.Account;
import com.model.AccountDTO;

public interface AccountService {
	
	List<Account> getAllAccounts() throws NotFoundException;
	Account getAccountByAccountNumber(String accountNumber) throws NotFoundException;
	Account createAccount(AccountDTO account) throws NotFoundException, AlreadyExistsException, InvalidJsonException;

}
