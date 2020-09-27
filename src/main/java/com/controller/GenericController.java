package com.controller;

import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/")
@Api(value = "Routes")
public class GenericController {

	private static final int SUCCESS = 200;
	final static Logger log = Logger.getLogger(GenericController.class);

	@GetMapping("/")
	@ApiOperation(value = "Show all the routes")
	@ResponseBody
	ResponseEntity<String> getAllRotes() {
		log.info("GET /");
		String routes = "Bearer routes:\n"
						+ "GET and PUT https://labank.herokuapp.com/api/v1/bearer/{id} valid ids: 39744197056 or 69995668000112"
						+ "\nPOST https://labank.herokuapp.com/api/v1/bearer\nValid Json: \n {\r\n" + 
																								"    \"bearerDocument\": \"69995668000112\",\r\n" + 
																								"    \"bearerName\": \"Leticia\",\r\n" + 
																								"    \"bearerType\": 0\r\n" + 
																								"}\n\n"
					    + "Account routes:"
					    + "\nGET https://labank.herokuapp.com/api/v1/account/{id}  valid accounts: 02451027 or 031818652"
					    + "\nPOST https://labank.herokuapp.com/api/v1/account\nValid Json: \n {\r\n" + 
																							    "        \"accountNumber\": \"031818652\",\r\n" + 
																							    "        \"accountBalance\": 4500.0,\r\n" + 
																							    "        \"bearer\": \"699956680112\",\r\n" + 
																							    "        \"agency\": 2\r\n" + 
																							    "}\n\n"
					    + "Transactions routes: "
					    + "\nGET https://labank.herokuapp.com/api/v1/transaction/{id}, Valid accounts: 02451027 or 031818652"
					    + "\nPOST https://labank.herokuapp.com/api/v1/transaction/deposit"
					    + "\n     https://labank.herokuapp.com/api/v1/transaction/withdraw\n"
					    + "Valid Json: \n{\r\n" + 
					    "            \"transactionAmount\": 5000,\r\n" + 
					    "            \"accountNumber\": \"031818652\"\r\n" + 
					    			"}";
		return ResponseEntity.status(SUCCESS).body(routes);
	}
}
