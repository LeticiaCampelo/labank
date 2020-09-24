package com.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(schema = "labank", name = "transaction")
public class Transaction {
	 @Id
	 @GeneratedValue(strategy = GenerationType.AUTO)
	 @Column(name = "transaction_id")
	 private Long id;
	 
	 @Column(name = "transaction_value")
	 private Double transactionValue;
	 
	 @Column(name = "transaction_date")
	 private Date transactionDate;
	 
	 @MapsId
	 @OneToMany
	 @Column(name = "fk_account_number")
	 private Account account;

	public Transaction(Long id, Double transactionValue, Date transactionDate, Account account) {
		this.id = id;
		this.transactionValue = transactionValue;
		this.transactionDate = transactionDate;
		this.account = account;
	}
	
	public Transaction() {
		//default constructor
	}
	 
	 

}
