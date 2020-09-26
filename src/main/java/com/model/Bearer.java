package com.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sun.istack.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(schema = "labank", name = "bearer")
public class Bearer implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@NotNull
	@Column(name = "bearer_document")
	private String bearerDocument;
	
	@NotNull
	@Column(name = "bearer_name")
	private String bearerName;
	
	@NotNull
	@Column(name = "bearer_type")
	private int bearerType;
	// type = 0 for a company, type 1 for a person.

	public String getBearerDocument() {
		return bearerDocument;
	}

	public void setBearerDocument(String bearerDocument) {
		this.bearerDocument = bearerDocument;
	}

	public String getBearerName() {
		return bearerName;
	}

	public void setBearerName(String bearerName) {
		this.bearerName = bearerName;
	}

	public int getBearerType() {
		return bearerType;
	}

	public void setBearerType(int bearerType) {
		this.bearerType = bearerType;
	}
}
