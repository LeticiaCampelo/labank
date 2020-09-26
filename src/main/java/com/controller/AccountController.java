package com.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.exceptions.AlreadyExistsException;
import com.exceptions.InvalidJsonException;
import com.exceptions.NotFoundException;
import com.model.Account;
import com.service.AccountService;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {
	
	final static Logger log = Logger.getLogger(AccountController.class);
	
	@Autowired
	private AccountService service;
	
	@GetMapping("/allAccounts")
	@ResponseBody
	List<Account> getAllAccounts() throws NotFoundException {
		log.info("GET /api/v1/account/allAccounts");
		List<Account> reqReturn = service.getAllAccounts();		
		return reqReturn;
	}
	
	@GetMapping("/{id}")
	@ResponseBody
	Account getAccountByNumber(@PathVariable (value = "id", required = true) String accountNumber) throws NotFoundException {
		log.info("GET /api/v1/account/{id}");
		Account reqReturn = service.getAccountByAccountNumber(accountNumber);
		return reqReturn;
	 }
	
	@PostMapping("/")
	@ResponseBody
	Account createAccount(@RequestBody Account account) throws NotFoundException, AlreadyExistsException, InvalidJsonException {
		log.info("POST /api/v1/account/");
		validateRequestBody(account);
		Account reqReturn = service.createAccount(account);
		return reqReturn;
	}
	
	private void validateRequestBody(Account account) throws InvalidJsonException{
		if(account.getAgency() == null) {
			throw new InvalidJsonException("Missing arguments");
		}
	}

}
