package com.peachkoder.controller.exceptions;

import java.util.Date;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ErrorResponse {
	
	private HttpStatus status; 
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSSZ")
    private Date timestamp = new Date();
	
	private String message;

    private String stackTrace;

    private Object data;

	public ErrorResponse(HttpStatus status, String message, String stackTrace, Object data) {
		super();
		this.status = status;
		this.message = message;
		this.stackTrace = stackTrace;
		this.data = data;
	}
}
