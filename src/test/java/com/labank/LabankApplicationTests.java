package com.labank;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.json.JSONException;
import org.json.JSONObject;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.controller.AccountController;
import com.controller.BearerController;
import com.controller.TransactionController;
import com.model.Account;
import com.model.Bearer;
import com.model.TransactionDTO;

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
	public void whenAllBearersOK() throws JSONException {
		ResponseEntity<String> response = restTemplate.getForEntity(getRootUrl() + "/bearer/allBearers", String.class);
		assertTrue(response.getStatusCode().is2xxSuccessful());

	}

	@Test
	public void whenBearerExistsOK() throws JSONException {
		ResponseEntity<String> response = restTemplate.getForEntity(getRootUrl() + "/bearer/39744197056", String.class);
		assertTrue(response.getStatusCode().is2xxSuccessful());
		String JsonRequest = response.getBody().toString();
		JSONObject jsonObject = new JSONObject(JsonRequest);
		assertNotNull(jsonObject.get("bearerName"));

	}

	@Test 
	public void whenBearerExistsERROR() throws JSONException {
		ResponseEntity<String> response = restTemplate.getForEntity(getRootUrl() + "/bearer/3974419756", String.class);
		assertTrue(response.getStatusCode().is4xxClientError());
		String JsonRequest = response.getBody().toString();
		JSONObject jsonObject = new JSONObject(JsonRequest);
		assertTrue(jsonObject.get("returnCode").equals("NOT_FOUND"));
	}


	@Test
	public void whenCreateBearersOKCPF() throws JSONException {
		ResponseEntity<String> cpfResponse = restTemplate.postForEntity(getRootUrl() + "/bearer/", createBearerTypeCPFOK(), String.class);
		assertTrue(cpfResponse.getStatusCode().is2xxSuccessful());
		String JsonRequest = cpfResponse.getBody().toString();
		JSONObject jsonObject = new JSONObject(JsonRequest);
		assertNotNull(jsonObject.get("bearerDocument"));
	}

	@Test
	public void whenCreateBearersOKCNPJ() throws JSONException {
		ResponseEntity<String> cnpjResponse = restTemplate.postForEntity(getRootUrl() + "/bearer/", createBearerTypeCNPJOK(), String.class);
		assertTrue(cnpjResponse.getStatusCode().is2xxSuccessful());
		String JsonRequest = cnpjResponse.getBody().toString();
		JSONObject jsonObject = new JSONObject(JsonRequest);
		assertNotNull(jsonObject.get("bearerDocument"));
	}

	@Test
	public void whenCreateBearersERROR() throws JSONException{
		ResponseEntity<String> response = restTemplate.postForEntity(getRootUrl() + "/bearer/", createBearerERROR(), String.class);
		assertTrue(response.getStatusCode().is4xxClientError());
		String JsonRequest = response.getBody().toString();
		JSONObject jsonObject = new JSONObject(JsonRequest);
		assertTrue(jsonObject.get("returnCode").equals("BAD_REQUEST"));

	}

	@Test
	public void whenUpdateBearersOK() throws JSONException {
		HttpEntity<Bearer> request = new HttpEntity<Bearer>(updateBearerOK());
		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/bearer/39744197056", HttpMethod.PUT, request, String.class);
		assertTrue(response.getStatusCode().is2xxSuccessful());
		String JsonRequest = response.getBody().toString();
		JSONObject jsonObject = new JSONObject(JsonRequest);
		assertNotNull(jsonObject.get("bearerDocument"));
	}

	@Test
	public void whenUpdateBearersERROR() throws JSONException{
		HttpEntity<Bearer> request = new HttpEntity<Bearer>(updateBearerERROR());
		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/bearer/12345678910", HttpMethod.PUT, request, String.class);
		assertTrue(response.getStatusCode().is4xxClientError());
		String JsonRequest = response.getBody().toString();
		JSONObject jsonObject = new JSONObject(JsonRequest);
		assertTrue(jsonObject.get("returnCode").equals("BAD_REQUEST"));

	}

	@Test
	public void whenAllAccountsOK() throws JSONException {
		ResponseEntity<String> response = restTemplate.getForEntity(getRootUrl() + "/account/allAccounts", String.class);
		assertTrue(response.getStatusCode().is2xxSuccessful());

	}

	@Test
	public void whenAccountExistsOK() throws JSONException {
		ResponseEntity<String> response = restTemplate.getForEntity(getRootUrl() + "/account/02451027", String.class);
		assertTrue(response.getStatusCode().is2xxSuccessful());
		String JsonRequest = response.getBody().toString();
		JSONObject jsonObject = new JSONObject(JsonRequest);
		assertNotNull(jsonObject.get("accountNumber"));

	}

	@Test 
	public void whenAccountExistsERROR() throws JSONException {
		ResponseEntity<String> response = restTemplate.getForEntity(getRootUrl() + "/account/0245027", String.class);
		assertTrue(response.getStatusCode().is4xxClientError());
		String JsonRequest = response.getBody().toString();
		JSONObject jsonObject = new JSONObject(JsonRequest);
		assertTrue(jsonObject.get("returnCode").equals("NOT_FOUND"));
	}

	@Test
	public void whenCreateAccountOK() throws JSONException {
		ResponseEntity<String> response = restTemplate.postForEntity(getRootUrl() + "/account/", createAccountOK(), String.class);
		assertTrue(response.getStatusCode().is2xxSuccessful());
		String JsonRequest = response.getBody().toString();
		JSONObject jsonObject = new JSONObject(JsonRequest);
		assertNotNull(jsonObject.get("accountNumber"));
	}

	@Test
	public void whenCreateAccountERROR() throws JSONException{
		ResponseEntity<String> response = restTemplate.postForEntity(getRootUrl() + "/account/", createAccountERROR(), String.class);
		assertTrue(response.getStatusCode().is4xxClientError());
		String JsonRequest = response.getBody().toString();
		JSONObject jsonObject = new JSONObject(JsonRequest);
		assertTrue(jsonObject.get("returnCode").equals("BAD_REQUEST"));
	}

	@Test
	public void whenTransactionalAccountExistsOK() throws JSONException {
		ResponseEntity<String> transactions = restTemplate.getForEntity(getRootUrl() + "/transaction/02451027", String.class);
		assertTrue(transactions.getStatusCode().is2xxSuccessful());	
	}

	@Test 
	public void whenTransactionalAccountExistsERROR() throws JSONException {
		ResponseEntity<String> transactions = restTemplate.getForEntity(getRootUrl() + "/transaction/0245027", String.class);
		assertTrue(transactions.getStatusCode().is4xxClientError());
		String JsonRequest = transactions.getBody().toString();
		JSONObject jsonObject = new JSONObject(JsonRequest);
		assertTrue(jsonObject.get("returnCode").equals("NOT_FOUND"));
	}

	@Test 
	public void whenDepositOK() throws JSONException {
		ResponseEntity<String> transactions = restTemplate.postForEntity(getRootUrl() + "/transaction/deposit", transactionsOK(),String.class);
		assertTrue(transactions.getStatusCode().is2xxSuccessful());
		String JsonRequest = transactions.getBody().toString();
		JSONObject jsonObject = new JSONObject(JsonRequest);
		assertNotNull(jsonObject.get("id"));
	}

	@Test 
	public void whenWithDrawOK() throws JSONException {
		ResponseEntity<String> transactions = restTemplate.postForEntity(getRootUrl() + "/transaction/withdraw", transactionsOK(),String.class);
		assertTrue(transactions.getStatusCode().is2xxSuccessful());
		String JsonRequest = transactions.getBody().toString();
		JSONObject jsonObject = new JSONObject(JsonRequest);
		assertNotNull(jsonObject.get("id"));
	}

	@Test 
	public void whenDepositERROR() throws JSONException {
		ResponseEntity<String> transactions = restTemplate.postForEntity(getRootUrl() + "/transaction/deposit", transactionsERROR(),String.class);
		assertTrue(transactions.getStatusCode().is4xxClientError());
		String JsonRequest = transactions.getBody().toString();
		JSONObject jsonObject = new JSONObject(JsonRequest);
		assertTrue(jsonObject.get("returnCode").equals("BAD_REQUEST"));
	}

	@Test 
	public void whenWithDrawERROR() throws JSONException {
		ResponseEntity<String> transactions = restTemplate.postForEntity(getRootUrl() + "/transaction/withdraw", transactionsERROR(),String.class);
		assertTrue(transactions.getStatusCode().is4xxClientError());
		String JsonRequest = transactions.getBody().toString();
		JSONObject jsonObject = new JSONObject(JsonRequest);
		assertTrue(jsonObject.get("returnCode").equals("BAD_REQUEST"));
	}


	private Bearer updateBearerERROR() {
		Bearer testBearer = new Bearer();
		testBearer.setBearerName("TesteUpdateERROR");
		testBearer.setBearerDocument("12345478910");
		testBearer.setBearerType(1);
		return testBearer;
	}

	private Bearer updateBearerOK() {
		Bearer testBearer = new Bearer();
		testBearer.setBearerName("TesteUpdateOK");
		testBearer.setBearerDocument("39744197056");
		testBearer.setBearerType(1);
		return testBearer;
	}

	private Bearer createBearerERROR() {
		Bearer testBearer = new Bearer();
		testBearer.setBearerName("TesteERROR");
		testBearer.setBearerType(1);
		return testBearer;
	}

	private Bearer createBearerTypeCPFOK() {
		Bearer testBearer = new Bearer();
		testBearer.setBearerName("TesteCPF");
		testBearer.setBearerDocument("12345678910");
		testBearer.setBearerType(1);
		return testBearer;
	}

	private Bearer createBearerTypeCNPJOK() {
		Bearer testBearer = new Bearer();
		testBearer.setBearerName("TesteCNPJ");
		testBearer.setBearerDocument("12345678910123");
		testBearer.setBearerType(0);
		return testBearer;
	}

	private Account createAccountERROR() {
		Account testAccount = new Account();
		testAccount.setAccountNumber("12345");
		testAccount.setAgency(1);
		return testAccount;
	}

	private Account createAccountOK() {
		Account testAccount = new Account();
		testAccount.setAccountNumber("12345");
		testAccount.setBearer("12345678910");
		testAccount.setAgency(1);
		return testAccount;
	}

	private TransactionDTO transactionsOK() {
		TransactionDTO transaction = new TransactionDTO();
		transaction.setAccountNumber("02451027");
		transaction.setTransactionAmount(2.0);
		return transaction;
	}

	private TransactionDTO transactionsERROR() {
		TransactionDTO transaction = new TransactionDTO();
		transaction.setAccountNumber("02451027");
		return transaction;

	}


}