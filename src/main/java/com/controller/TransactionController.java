package com.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.exceptions.InvalidJsonException;
import com.exceptions.InvalidOperationException;
import com.exceptions.NotFoundException;
import com.model.Bearer;
import com.model.RequestReturn;
import com.model.Transaction;
import com.service.TransactionService;

@RestController
@RequestMapping("/api/v1/transaction")
public class TransactionController {
	
	final static Logger log = Logger.getLogger(TransactionController.class);
	
	@Autowired
	private TransactionService service;
	
	@GetMapping("/{id}")
	@ResponseBody
	List<Transaction> getExtract(@PathVariable (value = "id", required = true) String accountNumber) throws NotFoundException, InvalidJsonException {
		log.info("GET /api/v1/transaction/{id}");
		validateQueryParams(accountNumber);
		List<Transaction> reqReturn = service.extract(accountNumber);
		return reqReturn;
	 }
		
	@ResponseBody
	@PostMapping("/deposit")
	Transaction deposit(@RequestBody Transaction transaction) throws InvalidOperationException, NotFoundException, InvalidJsonException {
		log.info("POST /api/v1/transaction/deposit");
		validateRequestBody(transaction);
		Transaction reqReturn = service.deposit(transaction);
		return reqReturn;
	}	
	
	@ResponseBody
	@PostMapping("/withdraw")
	Transaction withdraw(@RequestBody Transaction transaction) throws InvalidOperationException, NotFoundException, InvalidJsonException {
		log.info("POST /api/v1/transaction/withdraw");
		validateRequestBody(transaction);
		Transaction reqReturn = service.withdraw(transaction);
		return reqReturn;
	}
	
	private void validateQueryParams(String accountNumber) throws InvalidJsonException {
		if(accountNumber == null) {
			throw new InvalidJsonException("Missing arguments");	
		}
	}
	
	private void validateRequestBody(Transaction transaction) throws InvalidJsonException{
		if(transaction.getTransactionAmount() == null || transaction.getAccountNumber() == null) {
			throw new InvalidJsonException("Missing arguments");
		}
		else if(transaction.getTransactionDate() != null) {
			throw new InvalidJsonException("Just instants transactions for now. Take out the Transaction date.");
		}
	}

}
