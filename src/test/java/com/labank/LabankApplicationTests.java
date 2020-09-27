package com.labank;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.RequestEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.controller.AccountController;
import com.controller.BearerController;
import com.controller.TransactionController;
import com.model.Bearer;

@SpringBootTest(classes = {AccountController.class, BearerController.class, TransactionController.class} , webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@ComponentScan("com")
@EnableJpaRepositories("com")
@EntityScan("com")
@EnableAutoConfiguration
@TestPropertySource(locations = "classpath:application.properties")
@AutoConfigureTestDatabase
@ContextConfiguration(classes = {AppConfig.class} )
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
		return "http://localhost:" + port + "/api/v1";
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
		System.out.println(response);
		assertNotNull(response);
		
	}
	
	@Test 
	public void whenBearerExistsERROR() {
		Bearer bearer = restTemplate.getForObject(getRootUrl() + "/bearer/3974419756", Bearer.class);
		assertTrue(bearer.getBearerDocument() == null);
	}
		
	
	@Test
	public void whenCreateBearersOK() {
		Bearer responseCPF = restTemplate.postForObject(getRootUrl() + "/bearer/", createBearerTypeCPF(), Bearer.class);
		System.out.println(responseCPF);
		assertNotNull(responseCPF.getBearerDocument());
		Bearer responseCNPJ = restTemplate.postForObject(getRootUrl() + "/bearer/", createBearerTypeCNPJ(), Bearer.class);
		System.out.println(responseCNPJ);
		assertNotNull(responseCNPJ.getBearerDocument());
	}
	
	@Test
	public void whenCreateBearersERROR(){
		Bearer bearer = restTemplate.postForObject(getRootUrl() + "/bearer/", createBearerTypeERROR(), Bearer.class);
		assertTrue(bearer.getBearerDocument() == null);
		
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
