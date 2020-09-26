package com.labank;

import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringRunner;

import com.controller.AccountController;
import com.controller.BearerController;
import com.controller.TransactionController;
import com.exceptions.InvalidJsonException;
import com.exceptions.NotFoundException;
import com.model.Bearer;

@SpringBootTest(classes = {AccountController.class, BearerController.class, TransactionController.class} , webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@ComponentScan("com")
@EnableJpaRepositories("com")
@EntityScan("com")
@EnableAutoConfiguration
class LabankApplicationTests {

	@Autowired
	private AccountController accountController;
	@Autowired
	private BearerController bearerController;
	@Autowired
	private TransactionController transactionController;
	
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;
	
	
	private String getRootUrl() {
		return "http://localhost:" + port + "/api/vi";
	}

	@Test
	public void contextLoads() throws Exception {
		assertNotNull(accountController);
		assertNotNull(bearerController);
		assertNotNull(transactionController);
		
	}

	
	@Test
	public void whenBearerExistsOK() {
		Bearer response = restTemplate.getForObject(getRootUrl() + "/bearer/39744197056", Bearer.class);
		assertNotNull(response);
		
	}
	
	@org.junit.Test(expected = NotFoundException.class) 
	public void whenBearerExistsERROR() {
		restTemplate.getForObject(getRootUrl() + "/bearer/3974419756", Bearer.class);
	}
	
	@Test
	public void whenCreateBearersOK() {
		Bearer responseCPF = restTemplate.postForObject(getRootUrl() + "/bearer/", createBearerTypeCPF(), Bearer.class);
		assertNotNull(responseCPF);
		Bearer responseCNPJ = restTemplate.postForObject(getRootUrl() + "/bearer/", createBearerTypeCNPJ(), Bearer.class);
		assertNotNull(responseCNPJ);
		Bearer response = restTemplate.getForObject(getRootUrl() + "/bearer/12345678910", Bearer.class);
		assertNotNull(response);
	}
	
	@org.junit.Test(expected = InvalidJsonException.class) 
	public void whenCreateBearersERROR(){
		createBearerTypeERROR();
		restTemplate.postForObject(getRootUrl() + "/bearer/", createBearerTypeCPF(), Bearer.class);
		
	}
	
	
	private Bearer createBearerTypeERROR() {
		Bearer testBearer = new Bearer();
		testBearer.setBearerName("TesteCPF");
		testBearer.setBearerType(1);
		return testBearer;
	}
	
	private Bearer createBearerTypeCPF() {
		Bearer testBearer = new Bearer();
		testBearer.setBearerName("TesteCPF");
		testBearer.setBearerDocument("12345678910");
		testBearer.setBearerType(1);
		return testBearer;
	}
	
	private Bearer createBearerTypeCNPJ() {
		Bearer testBearer = new Bearer();
		testBearer.setBearerName("TesteCNPJ");
		testBearer.setBearerDocument("12345678910123");
		testBearer.setBearerType(0);
		return testBearer;
	}


}
