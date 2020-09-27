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

import com.exceptions.AlreadyExistsException;
import com.exceptions.InvalidJsonException;
import com.exceptions.NotFoundException;
import com.model.Account;
import com.model.AccountDTO;
import com.service.AccountService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "Account")
@RequestMapping("/api/v1/account")
public class AccountController {

	private Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
	final static Logger log = Logger.getLogger(AccountController.class);

	@Autowired
	private AccountService service;

	@GetMapping("/allAccounts")
	@ApiOperation(value = "Get all accounts")
	@ResponseBody
	List<Account> getAllAccounts() throws NotFoundException {
		log.info("GET /api/v1/account/allAccounts");
		List<Account> reqReturn = service.getAllAccounts();		
		return reqReturn;
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "Get an account")
	@ResponseBody
	Account getAccountByNumber(@PathVariable (value = "id", required = true) String accountNumber) throws NotFoundException, InvalidJsonException {
		log.info("GET /api/v1/account/");
		validateQueryParams(accountNumber);
		Account reqReturn = service.getAccountByAccountNumber(accountNumber);
		return reqReturn;
	}

	@PostMapping("/")
	@ApiOperation(value = "Create an account")
	@ResponseBody
	Account createAccount(@RequestBody AccountDTO account) throws NotFoundException, AlreadyExistsException, InvalidJsonException {
		log.info("POST /api/v1/account/");
		validateRequestBody(account);
		Account reqReturn = service.createAccount(account);
		return reqReturn;
	}

	private void validateQueryParams(String accountNumber) throws InvalidJsonException {
		if(accountNumber == null || !pattern.matcher(accountNumber).matches()) {
			throw new InvalidJsonException("Missing or invalid arguments");	
		}
	}

	private void validateRequestBody(AccountDTO account) throws InvalidJsonException{
		if(account.getAccountNumber() == null || account.getBearer() == null || account.getAgency() == null) {
			throw new InvalidJsonException("Missing or invalid arguments");
		}
	}

}
