package com.controller;

import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class GenericController {

	private static final int SUCCESS = 200;
	final static Logger log = Logger.getLogger(GenericController.class);

	@GetMapping("/")
	@ResponseBody
	ResponseEntity<String> getAllBearers() {
		log.info("GET /");
		String routes = "Bearer routes:\n"
						+ "GET and PUT https://labank.herokuapp.com/api/v1/bearer/{id} "
						+ "\nPOST https://labank.herokuapp.com/api/v1/bearer\n\n"
					    + "Account routes:"
					    + "\nGET https://labank.herokuapp.com/api/v1/account/{id},"
					    + "\nPOST https://labank.herokuapp.com/api/v1/account\n\n"
					    + "Transactions routes: "
					    + "\nGET https://labank.herokuapp.com/api/v1/transaction/{id}, "
					    + "\nPOST https://labank.herokuapp.com/api/v1/transaction/deposit"
					    + "\n     https://labank.herokuapp.com/api/v1/transaction/withdraw";
		return ResponseEntity.status(SUCCESS).body(routes);
	}
}
