package com.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class GenericController {

	private static final int SUCCESS = 200;

	@GetMapping("/")
	@ResponseBody
	ResponseEntity<String> getAllBearers() {
		String routes = "Bearer routes: GET and PUT https://labank.herokuapp.com/api/v1/bearer/{id} and POST https://labank.herokuapp.com/api/v1//bearer\n\n"
					  + "Account routes: GET https://labank.herokuapp.com/api/v1/account/{id}, POST https://labank.herokuapp.com/api/v1/account\n\n"
					  + "Transactions routes: GET https://labank.herokuapp.com/api/v1/transaction/{id}, POST https://labank.herokuapp.com/api/v1/transaction/deposit and "
					  + "https://labank.herokuapp.com/api/v1/transaction/withdraw";
		return ResponseEntity.status(SUCCESS).body(routes);
	}
}
