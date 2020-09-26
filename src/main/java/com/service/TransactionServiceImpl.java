package com.service;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exceptions.InvalidDataException;
import com.model.Account;
import com.model.RequestReturn;
import com.model.Transaction;
import com.repository.AccountRepository;
import com.repository.TransactionRepository;

@Service
public class TransactionServiceImpl implements TransactionService{
	
	private static final int BAD_REQUEST = 400;
	private static final int SUCCESS = 200;
	
	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Override
	public RequestReturn extract(String accountNumber) {
		if(accountRepository.existsById(accountNumber)) {
			List<Transaction> extract = transactionRepository.findByAccountNumber(accountNumber);
			return setReturn(SUCCESS, "Here is your extract: ", extract);
		}else{
			return setReturn(BAD_REQUEST, "This account doesn't exist", null);
		}
	}

	@Override
	public RequestReturn withdraw(Transaction transaction) {
		try{
			System.out.println(transaction.getAccountNumber());
			if(accountRepository.existsById(transaction.getAccountNumber())) {
				Account account = accountRepository.findByAccountNumber(transaction.getAccountNumber());
				if(transaction.getTransactionAmount() > 0) {
					if(account.getAccountBalance() <= transaction.getTransactionAmount()) {
						return setReturn(BAD_REQUEST, "This account doesn't have enough cash", null);
					}else {
						doTransaction(transaction, account, "D");
						return setReturn(SUCCESS, "Withdraw succeed! ", transaction);
					}


				}else{
					return setReturn(BAD_REQUEST, "Invalid operation", null);
				}
			}else {
				return setReturn(BAD_REQUEST, "This account doesn't exist", null);
			}
		}catch (InvalidDataException e) {
			return setReturn(BAD_REQUEST, e.getErrorMsg(), null);
		}
	}

	@Override
	public RequestReturn deposit(Transaction transaction) {
		try {
			if(accountRepository.existsById(transaction.getAccountNumber())) {
				Account account = accountRepository.findByAccountNumber(transaction.getAccountNumber());
				if(transaction.getTransactionAmount() > 0) {

					doTransaction(transaction, account, "C");

					return setReturn(SUCCESS, "Deposit succeed! ", transaction);
				}
				else {
					return setReturn(BAD_REQUEST, "Invalid operation", null);
				}
			}else {
				return setReturn(BAD_REQUEST, "This account doesn't exist", null);
			}
		} catch (InvalidDataException e) {
			return setReturn(BAD_REQUEST, e.getErrorMsg(), null);
		}
	}


	private RequestReturn setReturn(int code, String message, Object object) {
		return new RequestReturn(code, message, object);

	}
	

	private void doTransaction(Transaction transaction, Account account, String type) throws InvalidDataException{
		if(type.equals("C")) {
			account.setAccountBalance(account.getAccountBalance() + transaction.getTransactionAmount());
		}else if(type.equals("D")){
			account.setAccountBalance(account.getAccountBalance() - transaction.getTransactionAmount());
			transaction.setTransactionAmount(-transaction.getTransactionAmount());
		} else {
			throw new InvalidDataException("Invalid transaction type");
		}
		accountRepository.save(account);
		transaction.setTransactionDate(Calendar.getInstance().getTime());
		transactionRepository.save(transaction);
	}

}
