package com.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity	
@Table(schema = "labank", name = "account")
public class Account {
	@Id
	@Column(name = "account_number")
	private String accountNumber;
	
	@Column(name = "account_balance")
	private Double accountBalance;
	
	@MapsId
	@OneToOne
	@Column(name = "fk_bearer_document")
	private Bearer bearer;
	
	@MapsId
	@OneToMany
	@Column(name = "fk_agency_number")
	private Agency agency;
	
	@OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
	private Transaction Transaction;

	public Account(String accountNumber, Double accountBalance, Bearer bearer, Agency agency, Transaction transaction) {
		this.accountNumber = accountNumber;
		this.accountBalance = accountBalance;
		this.bearer = bearer;
		this.agency = agency;
		Transaction = transaction;
	}
	
	public Account() {
		//default constructor
	}
	
	
}
