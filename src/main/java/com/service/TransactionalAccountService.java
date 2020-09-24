package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.model.Bearer;
import com.repository.AccountRepository;
import com.repository.AgencyRepository;
import com.repository.BearerRepository;
import com.repository.TransactionRepository;

@Service
public class TransactionalAccountService {
	
	@Autowired
	private BearerRepository bearerRepository;
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private AgencyRepository agencyRepository;
	
	public Bearer getBearerByDocument(String document) {
	    return bearerRepository.findByDocument(document);
	  }

}
