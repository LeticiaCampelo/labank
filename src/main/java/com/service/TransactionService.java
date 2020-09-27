package com.service;

import java.util.List;

import com.exceptions.InvalidOperationException;
import com.exceptions.NotFoundException;
import com.model.Transaction;
import com.model.TransactionDTO;

public interface TransactionService {
	List<Transaction> extract(String accountNumber) throws NotFoundException;
	Transaction withdraw(TransactionDTO transaction) throws InvalidOperationException, NotFoundException;
	Transaction deposit(TransactionDTO transaction) throws InvalidOperationException, NotFoundException;
}
