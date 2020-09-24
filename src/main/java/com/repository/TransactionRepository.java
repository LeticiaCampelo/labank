package com.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.model.Transaction;

@Repository
public interface  TransactionRepository extends CrudRepository<Transaction, Long> {
	
	List<Transaction> findByFkAccountNumber(String accountNumber);	
	List<Transaction> findByTransactionDate(Date transactionDate);

}
