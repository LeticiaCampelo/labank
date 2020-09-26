package com.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class RequestReturn implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int returnCode;
	private String returnMessage;
	private Object object;
	
	
	public RequestReturn(int returnCode, String returnMessage, Object object) {
		this.returnCode = returnCode;
		this.returnMessage = returnMessage;
		this.object = object;
	}
	
	public int getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(int returnCode) {
		this.returnCode = returnCode;
	}
	public String getReturnMessage() {
		return returnMessage;
	}
	public void setReturnMessage(String returnMessage) {
		this.returnMessage = returnMessage;
	}
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
	
	private RequestReturn setReturn(int code, String message, Object object) {
		return new RequestReturn(code, message, object);

	}

}
