package com.service;

import java.util.List;

import com.exceptions.InvalidOperationException;
import com.exceptions.NotFoundException;
import com.model.Transaction;

public interface TransactionService {
	List<Transaction> extract(String accountNumber) throws NotFoundException;
	Transaction withdraw(Transaction transaction) throws InvalidOperationException, NotFoundException;
	Transaction deposit(Transaction transaction) throws InvalidOperationException, NotFoundException;
}
