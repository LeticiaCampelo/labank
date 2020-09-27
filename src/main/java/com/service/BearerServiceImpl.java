package com.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exceptions.AlreadyExistsException;
import com.exceptions.InvalidOperationException;
import com.exceptions.NotFoundException;
import com.model.Bearer;
import com.repository.BearerRepository;

@Service
public class BearerServiceImpl implements BearerService {

	private static final int SUCCESS = 200;

	@Autowired
	private BearerRepository bearerRepository;
	final static Logger log = Logger.getLogger(BearerServiceImpl.class);

	@Override
	public List<Bearer> getAllBearers() throws NotFoundException {
		List<Bearer> bearers = (List<Bearer>) bearerRepository.findAll();
		if(bearers == null) {
			throw new NotFoundException(String.format("No bearers registered yet!"));
		}
		log.info(String.format("%d - bearers found: %s", SUCCESS, bearers));
		return bearers;
	}

	public Bearer getBearerByDocument(String document) throws NotFoundException{
		Bearer bearer = bearerRepository.findById(document).orElseThrow(() -> new NotFoundException(String.format("Bearer %s not found", document)));
		log.info(String.format("%d - bearer found: %s", SUCCESS, bearer));
		return bearer;		 
	}

	@Override
	public Bearer createBearer(Bearer newBearer) throws InvalidOperationException, AlreadyExistsException {
		validateBearer(newBearer);							
		Bearer bearer = bearerRepository.save(newBearer);
		log.info(String.format("%d - bearer created: %s", SUCCESS, bearer));
		return bearer;	

	}

	@Override
	public Bearer updateBearer(Bearer bearerDetails, String id) throws InvalidOperationException, NotFoundException{
		Bearer bearer = bearerRepository.findById(id).orElseThrow(() -> new NotFoundException("Bearer not found"));
		if((!bearer.getBearerDocument().equals(bearerDetails.getBearerDocument())) || 
				(!(bearer.getBearerType() == bearerDetails.getBearerType()))) {
			throw new InvalidOperationException("Bearer documents can't be updated");
		}
		bearer.setBearerName(bearerDetails.getBearerName());
		bearer = bearerRepository.save(bearer);
		log.info(String.format("%d - bearer updated: %s", SUCCESS, bearer));
		return bearer;
	}

	private void validateBearer(Bearer newBearer) throws InvalidOperationException, AlreadyExistsException {
		newBearer.getBearerDocument().replaceAll("[./-]", "");
		if((newBearer.getBearerType() == 1 && newBearer.getBearerDocument().length() != 11) || 
				(newBearer.getBearerType() == 0 && newBearer.getBearerDocument().length() != 14)) {
			throw new InvalidOperationException("Invalid document number");
		}
		if(bearerRepository.existsById(newBearer.getBearerDocument())) {
			throw new AlreadyExistsException("Bearer already registered");	
		}
	}

}
