package com.peachkoder.service.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class AppCustomException extends RuntimeException {
	
	private HttpStatus status = null;

	private Object data = null;

	public AppCustomException() {
		super();
	}

	public AppCustomException(String message) {
		super(message);
	}

	public AppCustomException(HttpStatus status, String message) {
		this(message);
		this.status = status;
	}

	public AppCustomException(HttpStatus status, String message, Object data) {
		this(status, message);
		this.data = data;
	}
}