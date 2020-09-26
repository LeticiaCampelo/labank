package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exceptions.InvalidDataException;
import com.exceptions.NotFoundException;
import com.model.Bearer;
import com.model.RequestReturn;
import com.repository.BearerRepository;

@Service
public class BearerServiceImpl implements BearerService {
	
	private static final int BAD_REQUEST = 400;
	private static final int SUCCESS = 200;
	
	@Autowired
	private BearerRepository bearerRepository;
	
	@Override
	public RequestReturn getAllBearers() {
		List<Bearer> bearers = (List<Bearer>) bearerRepository.findAll();
		if(bearers.isEmpty()) {
			return setReturn(BAD_REQUEST, "No bearers registered yet!", bearers);
		}		
		return setReturn(SUCCESS, "Found!", bearers);
	}
	
	public RequestReturn getBearerByDocument(String document) {
	    try{
	    	Bearer bearer = bearerRepository.findById(document).orElseThrow(() -> new NotFoundException("Bearer not found"));
	    	return setReturn(SUCCESS, "Found!", bearer);
	    }catch (NotFoundException e) {
	    	return setReturn(BAD_REQUEST, e.getErrorMsg(), null);
		}		 
	  }
	
	@Override
	public RequestReturn createBearer(Bearer newBearer) {
		newBearer.getBearerDocument().replaceAll("[./-]", "");
		if(bearerRepository.existsById(newBearer.getBearerDocument())) {
			return setReturn(BAD_REQUEST, "Bearers already registered yet!", bearerRepository.findById(newBearer.getBearerDocument()));
		}
		try {
			validateBearer(newBearer);
			Bearer bearer = bearerRepository.save(newBearer);
			return setReturn(SUCCESS, "Created successfully!", bearer);	
		}catch (InvalidDataException e) {
			return setReturn(BAD_REQUEST, e.getErrorMsg(), null);
		}

			
	}
	
	@Override
	public RequestReturn updateBearer(Bearer bearerDetails, String id){
		try {
		Bearer bearer = bearerRepository.findById(id).orElseThrow(() -> new NotFoundException("Bearer not found"));
		if(!bearer.getBearerDocument().equals(bearerDetails.getBearerDocument())) {
			throw new NotFoundException("Bearer document can't be updated");
		}
		bearer.setBearerName(bearerDetails.getBearerName());
		bearer = bearerRepository.save(bearer);
		return setReturn(SUCCESS, "Updated successful!", bearer);
		}
		catch (NotFoundException e) {
			return setReturn(BAD_REQUEST, e.getErrorMsg(), null);
		}
	}
	
	private RequestReturn setReturn(int code, String message, Object object) {
		return new RequestReturn(code, message, object);

	}
	

	private void validateBearer(Bearer newBearer) throws InvalidDataException {
		if((newBearer.getBearerType() == 1 && newBearer.getBearerDocument().length() != 11) || 
				(newBearer.getBearerType() == 0 && newBearer.getBearerDocument().length() != 14)) {
			throw new InvalidDataException("Invalid document number");
		}
	}

}
