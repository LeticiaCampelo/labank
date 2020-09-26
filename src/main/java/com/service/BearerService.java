package com.service;

import com.model.Bearer;
import com.model.RequestReturn;

public interface BearerService {
	
	RequestReturn getAllBearers();
	RequestReturn getBearerByDocument(String document);	
	RequestReturn createBearer (Bearer bearer);
	RequestReturn updateBearer(Bearer bearer, String id);

}
