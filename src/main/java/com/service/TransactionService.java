package com.service;

import com.model.RequestReturn;
import com.model.Transaction;

public interface TransactionService {
	RequestReturn extract(String accountNumber);
	RequestReturn withdraw(Transaction transaction);
	RequestReturn deposit(Transaction transaction);
}
