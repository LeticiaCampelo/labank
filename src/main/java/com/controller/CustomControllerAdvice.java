package com.controller;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.exceptions.AlreadyExistsException;
import com.exceptions.InvalidJsonException;
import com.exceptions.InvalidOperationException;
import com.exceptions.NotFoundException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.model.RequestReturn;

@Configuration
@ControllerAdvice
public class CustomControllerAdvice {
	final static Logger log = Logger.getLogger(CustomControllerAdvice.class);
	
    @ExceptionHandler({HttpMessageNotReadableException.class, HttpMessageNotWritableException.class, JsonMappingException.class, 
    	JsonParseException.class, InvalidOperationException.class, InvalidJsonException.class, AlreadyExistsException.class, HttpMediaTypeNotSupportedException.class })
    public ResponseEntity<RequestReturn> handleMessageNotReadableException(Exception ex) {
		log.error(ex.getClass().getName() + " " + ex.getMessage());
		RequestReturn reqReturn = new RequestReturn(HttpStatus.BAD_REQUEST, ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(reqReturn);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<RequestReturn> handlerHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
		log.error(ex.getClass().getName() + " "+ ex.getMessage());
		RequestReturn reqReturn = new RequestReturn(HttpStatus.METHOD_NOT_ALLOWED, ex.getMessage());
		return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(reqReturn);
    }
    
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<RequestReturn> handlerNotFoundException(NotFoundException ex) {
		log.error(ex.getClass().getName() + " "+ ex.getMessage());
		RequestReturn reqReturn = new RequestReturn(HttpStatus.NOT_FOUND, ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(reqReturn);
    }
		
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<RequestReturn> handleAllExceptions(RuntimeException ex) {
		log.error("RuntimeException" + ex.getMessage());
		RequestReturn reqReturn = new RequestReturn(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(reqReturn);
	}
}
