package com.peachkoder.service;

import lombok.Data;

@Data
public class OrderPersistException extends RuntimeException {
	
	private static final String MSG = "";
	
	private String stackMessage;

	public OrderPersistException() {
		super(); 
	}

	public OrderPersistException(String message) {
		super(MSG); 
		stackMessage = message;
	}
	
	

}
