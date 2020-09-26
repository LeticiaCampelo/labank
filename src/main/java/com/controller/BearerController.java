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

import com.model.Bearer;
import com.model.RequestReturn;
import com.service.BearerService;

@RestController
@RequestMapping("/api/v1")
public class BearerController {
	
	@Autowired
	private BearerService service;
	
	@GetMapping("/bearers/allBearers")
	@ResponseBody
	ResponseEntity<RequestReturn> getAllBearers() {
		RequestReturn reqReturn = service.getAllBearers();		
		return ResponseEntity.status(reqReturn.getReturnCode()).body(reqReturn);
	}
	
	@GetMapping("/bearers/{id}")
	@ResponseBody
	ResponseEntity<RequestReturn> getBearerByDocument(@PathVariable (value = "id", required = true) String document) {
		RequestReturn reqReturn = service.getBearerByDocument(document);
		return ResponseEntity.status(reqReturn.getReturnCode()).body(reqReturn);
	 }
	
	@PostMapping("bearers/")
	@ResponseBody
	ResponseEntity<RequestReturn> createBearer(@RequestBody Bearer bearer) {
		RequestReturn reqReturn = service.createBearer(bearer);
		return ResponseEntity.status(reqReturn.getReturnCode()).body(reqReturn);
	}
	
	@PutMapping("bearers/{id}")
	@ResponseBody
	ResponseEntity<RequestReturn> updateBearer(@PathVariable (value = "id", required = true) String document, @RequestBody Bearer bearer) {
		RequestReturn reqReturn = service.updateBearer(bearer, document);
		return ResponseEntity.status(reqReturn.getReturnCode()).body(reqReturn);
	}

}
