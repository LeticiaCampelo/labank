package com.service;

import java.util.List;

import com.exceptions.AlreadyExistsException;
import com.exceptions.InvalidOperationException;
import com.exceptions.NotFoundException;
import com.model.Bearer;
import com.model.RequestReturn;

public interface BearerService {
	
	List<Bearer> getAllBearers() throws NotFoundException;
	Bearer getBearerByDocument(String document) throws NotFoundException;	
	Bearer createBearer (Bearer bearer) throws InvalidOperationException, AlreadyExistsException;
	Bearer updateBearer(Bearer bearer, String id) throws InvalidOperationException, NotFoundException;

}
