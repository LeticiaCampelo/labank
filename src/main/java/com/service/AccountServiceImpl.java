package com.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.Account;
import com.model.RequestReturn;
import com.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService{
	
	private static final int BAD_REQUEST = 400;
	private static final int SUCCESS = 200;
	
	@Autowired
	private AccountRepository accountRepository;

	@Override
	public RequestReturn getAllAccounts() {
		List<Account> accounts = (List<Account>) accountRepository.findAll();
		if(accounts.isEmpty()) {
			return setReturn(BAD_REQUEST," No accounts registered yet!", accounts);
		}
		return setReturn(SUCCESS," Found!", accounts);
	}
	@Override
	public RequestReturn getAccountByAccountNumber(String accountNumber) {
			Account account = accountRepository.findByAccountNumber(accountNumber);
			if(account == null) {
				return setReturn(BAD_REQUEST,"Account not found", account);
			}			
			return setReturn(SUCCESS, "Found!", account);
	}

	@Override
	public RequestReturn createAccount(Account account) {
			if(accountRepository.existsById(account.getAccountNumber())){
				return setReturn(BAD_REQUEST,"This account already exists!", accountRepository.findById(account.getAccountNumber()));
			}
			try {
				accountRepository.save(account);
				return setReturn(SUCCESS, "Created successfully!", account);
			}catch (Exception e) {
				return setReturn(BAD_REQUEST,"Could not create account.", account);
			}	
	}
	
	private RequestReturn setReturn(int code, String message, Object object) {
		return new RequestReturn(code, message, object);

	}

}
