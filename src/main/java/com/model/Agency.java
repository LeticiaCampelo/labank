package com.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(schema = "labank", name = "agency")
public class Agency {
	@Id
	@Column(name = "agency_number")
	private Long id;
	
	@OneToMany(mappedBy = "agency", cascade = CascadeType.ALL)
	private Account account;

	public Agency(Long id) {		
		this.id = id;
	}
	public Agency() {
		//default constructor
	}
	

}
