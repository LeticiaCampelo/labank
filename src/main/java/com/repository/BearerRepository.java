package com.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.model.Bearer;

public interface BearerRepository extends CrudRepository<Bearer, String> {
	
	Bearer findByDocument(String document);
	List<Bearer> findByName(String name);
	

}
