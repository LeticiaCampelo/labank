package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.model.RequestReturn;
import com.model.Transaction;
import com.service.TransactionService;

@RestController
@RequestMapping("/api/v1")
public class TransactionController {
	
	@Autowired
	private TransactionService service;
	
	@GetMapping("/transaction/{id}")
	@ResponseBody
	ResponseEntity<RequestReturn> getAccountByNumber(@PathVariable (value = "id", required = true) String accountNumber) {
		RequestReturn reqReturn = service.extract(accountNumber);
		return ResponseEntity.status(reqReturn.getReturnCode()).body(reqReturn);
	 }
		
	@ResponseBody
	@PostMapping("/transaction/deposit")
	ResponseEntity<RequestReturn> deposit(@RequestBody Transaction transaction) {
		RequestReturn reqReturn = service.deposit(transaction);
		return ResponseEntity.status(reqReturn.getReturnCode()).body(reqReturn);
	}	
	
	@ResponseBody
	@PostMapping("/transaction/withdraw")
	ResponseEntity<RequestReturn> withdraw(@RequestBody Transaction transaction) {
		RequestReturn reqReturn = service.withdraw(transaction);
		return ResponseEntity.status(reqReturn.getReturnCode()).body(reqReturn);
	}	

}
