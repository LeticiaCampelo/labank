package com.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(schema = "labank", name = "bearer")
public class Bearer {
	@Id
	@Column(name = "bearer_document")
	private String bearerDocument;
	
	@Column(name = "bearer_name")
	private String bearerName;
	
	@Column(name = "bearer_type")
	private int bearerType;
	// type = 0 for a company, type 1 for a person.
	
	@OneToOne(mappedBy = "bearer", cascade = CascadeType.ALL)
	private Account account;
	
	
	public Bearer(String bearerDocument, String bearerName, int bearerType, Account account) {
		this.bearerDocument = bearerDocument;
		this.bearerName = bearerName;
		this.bearerType = bearerType;
		this.account = account;
	}
	
	public Bearer() {
		//default constructor
	}
	
	
	
	
}
