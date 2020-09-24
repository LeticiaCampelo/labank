package com.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.model.Account;

@Repository
public interface AccountRepository extends CrudRepository<Account, String>{
	
	Account findByAccountNumber(String accountNumber);
	Account findByFkBearerDocument(String bearerDocument);

}
