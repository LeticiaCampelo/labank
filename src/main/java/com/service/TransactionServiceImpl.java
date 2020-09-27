package com.service;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exceptions.InvalidOperationException;
import com.exceptions.NotFoundException;
import com.model.Account;
import com.model.Transaction;
import com.model.TransactionDTO;
import com.repository.AccountRepository;
import com.repository.TransactionRepository;

@Service
public class TransactionServiceImpl implements TransactionService{

	private static final int SUCCESS = 200;

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private AccountRepository accountRepository;

	final static Logger log = Logger.getLogger(TransactionServiceImpl.class);

	@Override
	public List<Transaction> extract(String accountNumber) throws NotFoundException{
		accountRepository.findById(accountNumber).orElseThrow(() -> new NotFoundException("Account doesen't exist.")); 
		List<Transaction> extract = transactionRepository.findByAccountNumber(accountNumber);
		if(extract == null)
			throw new NotFoundException("Error getting extract");
		log.info(String.format("%d - transactions found: %s", SUCCESS, extract));
		return extract;
	}

	@Override
	public Transaction withdraw(TransactionDTO transaction) throws InvalidOperationException, NotFoundException {
		Account account = accountRepository.findById(transaction.getAccountNumber()).orElseThrow(() -> new NotFoundException("Account doesen't exist.")); 
		if(transaction.getTransactionAmount() > 0) {
			if(account.getAccountBalance() <= transaction.getTransactionAmount()) {
				throw new InvalidOperationException("This account doesn't have enough cash");
			}else {
				Transaction transactionEntity = parseTransactionToEntity(transaction);
				return doTransaction(transactionEntity, account, "D");
			}
		}else{
			throw new InvalidOperationException("Invalid transaction amount.");
		}
	}

	@Override
	public Transaction deposit(TransactionDTO transaction) throws InvalidOperationException, NotFoundException  {
		Account account = accountRepository.findById(transaction.getAccountNumber()).orElseThrow(() -> new NotFoundException("Account doesen't exist.")); 
		if(transaction.getTransactionAmount() > 0) {
			Transaction transactionEntity = parseTransactionToEntity(transaction);
			return doTransaction(transactionEntity, account, "C");
		}
		else {
			throw new InvalidOperationException("Invalid transaction amount.");
		}
	}

	private Transaction parseTransactionToEntity(TransactionDTO transaction) {
		Transaction transactionEntity = new Transaction();
		transactionEntity.setAccountNumber(transaction.getAccountNumber());
		transactionEntity.setTransactionAmount(transaction.getTransactionAmount());
		return transactionEntity;
	}


	private Transaction doTransaction(Transaction transaction, Account account, String type) throws InvalidOperationException{
		if(type.equals("C")) {
			account.setAccountBalance(deposit(transaction, account));
		}else{
			account.setAccountBalance(withdraw(transaction, account));
			transaction.setTransactionAmount(-transaction.getTransactionAmount());
		}
		accountRepository.save(account);
		transaction.setTransactionDate(new Date());
		transactionRepository.save(transaction);
		log.info(String.format("%d - %s transaction succeed: %s", SUCCESS, type, transaction));
		return transaction;
	}

	private double withdraw(Transaction transaction, Account account) {
		log.info(String.format("taking out %s from the account %s", transaction.getTransactionAmount(), transaction.getAccountNumber()));
		return account.getAccountBalance() - transaction.getTransactionAmount();
	}

	private double deposit(Transaction transaction, Account account) {
		log.info(String.format("depositing %s in the account %s", transaction.getTransactionAmount(), transaction.getAccountNumber()));
		return account.getAccountBalance() + transaction.getTransactionAmount();
	}

}
