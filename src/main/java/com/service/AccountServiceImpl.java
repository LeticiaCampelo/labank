package com.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exceptions.AlreadyExistsException;
import com.exceptions.InvalidJsonException;
import com.exceptions.NotFoundException;
import com.model.Account;
import com.repository.AccountRepository;
import com.repository.BearerRepository;

@Service
public class AccountServiceImpl implements AccountService{

	private static final int SUCCESS = 200;

	final static Logger log = Logger.getLogger(AccountServiceImpl.class);

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private BearerRepository bearerRepository;

	@Override
	public List<Account> getAllAccounts() throws NotFoundException {
		log.info("starting /getAllAccounts");
		List<Account> accounts = (List<Account>) accountRepository.findAll();
		if(accounts== null) {
			throw new NotFoundException(String.format(" No accounts registered yet!"));
		}
		log.info(String.format("%d - accounts found: %s", SUCCESS, accounts));
		return accounts;
	}

	@Override
	public Account getAccountByAccountNumber(String accountNumber) throws NotFoundException {
		Account account = accountRepository.findById(accountNumber).orElseThrow(() -> new NotFoundException(String.format("Account %s not found", accountNumber)));	
		log.info(String.format("%d - account found: %s", SUCCESS, account));
		return account;
	}

	@Override
	public Account createAccount(Account account)throws NotFoundException, AlreadyExistsException, InvalidJsonException  {				
		validateAccountParams(account);
		if(account.getAccountBalance() == null)
			account.setAccountBalance(0.00);
		accountRepository.save(account);
		log.info(String.format("%d - account created: %s", SUCCESS, account));
		return account;
	}

	private void validateAccountParams(Account account) throws InvalidJsonException, NotFoundException, AlreadyExistsException{
		if(accountRepository.existsById(account.getAccountNumber())) {
			throw new AlreadyExistsException("This account already exists");
		}
		if(accountRepository.findByBearer(account.getBearer()) != null) {
			throw new AlreadyExistsException("This bearer already have an account");
		}
		bearerRepository.findById(account.getBearer()).orElseThrow(() -> new NotFoundException("Please sign on first to create an account."));
	}

}
