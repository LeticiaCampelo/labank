package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.model.Bearer;
import com.service.TransactionalAccountService;

@RestController
public class TransactionalAccountController {
	
	@Autowired
	private TransactionalAccountService service;
	
	@GetMapping("/bearer/{document}")
	  Bearer getOneBearer(@PathVariable String document) {
	    return service.getBearerByDocument(document);
	  }
	
	@GetMapping("/hello")
	 String helloWorld() {
	    return "Hello World!";
	  }

}
