package com.controller;

import java.util.List;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.model.Transaction;
import com.service.TransactionService;

@RestController
@RequestMapping("/api/v1/transaction")
public class TransactionController {

	final static Logger log = Logger.getLogger(TransactionController.class);
	private Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

	@Autowired
	private TransactionService service;

	@GetMapping("/{id}")
	@ResponseBody
	List<Transaction> getExtract(@PathVariable (value = "id", required = true) String accountNumber) throws NotFoundException, InvalidJsonException {
		log.info("GET /api/v1/transaction/");
		validateQueryParams(accountNumber);
		List<Transaction> reqReturn = service.extract(accountNumber);
		return reqReturn;
	}

	@ResponseBody
	@PostMapping("/deposit")
	Transaction deposit(@RequestBody Transaction transaction) throws InvalidOperationException, NotFoundException, InvalidJsonException {
		log.info("POST /api/v1/transaction/deposit " + transaction);
		validateRequestBody(transaction);
		Transaction reqReturn = service.deposit(transaction);
		return reqReturn;
	}	

	@ResponseBody
	@PostMapping("/withdraw")
	Transaction withdraw(@RequestBody Transaction transaction) throws InvalidOperationException, NotFoundException, InvalidJsonException {
		log.info("POST /api/v1/transaction/withdraw " + transaction);
		validateRequestBody(transaction);
		Transaction reqReturn = service.withdraw(transaction);
		return reqReturn;
	}

	private void validateQueryParams(String accountNumber) throws InvalidJsonException {
		if(accountNumber == null || !pattern.matcher(accountNumber).matches()) {
			throw new InvalidJsonException("Missing or invalid arguments");
		}
	}

	private void validateRequestBody(Transaction transaction) throws InvalidJsonException{
		if((transaction.getTransactionAmount() == null || transaction.getAccountNumber() == null) || transaction.getAccountNumber().equals("")) {
			throw new InvalidJsonException("Missing or invalid arguments");
		}
		else if(transaction.getTransactionDate() != null) {
			throw new InvalidJsonException("Just instants transactions for now. Take out the Transaction date.");
		}
	}

}
