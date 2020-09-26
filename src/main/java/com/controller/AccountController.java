package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.model.Account;
import com.model.RequestReturn;
import com.service.AccountService;

@RestController
@RequestMapping("/api/v1")
public class AccountController {
	
	@Autowired
	private AccountService service;
	
	@GetMapping("/account/allAccounts")
	@ResponseBody
	ResponseEntity<RequestReturn> getAllAccounts() {
		RequestReturn reqReturn = service.getAllAccounts();		
		return ResponseEntity.status(reqReturn.getReturnCode()).body(reqReturn);
	}
	
	@GetMapping("/account/{id}")
	@ResponseBody
	ResponseEntity<RequestReturn> getAccountByNumber(@PathVariable (value = "id", required = true) String accountNumber) {
		RequestReturn reqReturn = service.getAccountByAccountNumber(accountNumber);
		return ResponseEntity.status(reqReturn.getReturnCode()).body(reqReturn);
	 }
	
	@PostMapping("account/")
	@ResponseBody
	ResponseEntity<RequestReturn> createAccount(@RequestBody Account account) {
		RequestReturn reqReturn = service.createAccount(account);
		return ResponseEntity.status(reqReturn.getReturnCode()).body(reqReturn);
	}	
	

}
