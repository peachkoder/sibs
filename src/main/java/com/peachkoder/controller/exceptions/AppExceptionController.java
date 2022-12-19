package com.peachkoder.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.peachkoder.service.OrderPersistException;
import com.peachkoder.service.exceptions.ItemQuantityExceededException;
import com.peachkoder.service.exceptions.StockItemNotFoundException;
import com.peachkoder.service.exceptions.UserNotFoundException;

@ControllerAdvice
public class AppExceptionController {

	@ExceptionHandler(value = UserNotFoundException.class)
	public ResponseEntity<ErrorResponse> userNotFound(UserNotFoundException exception) {

		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND, exception.getLocalizedMessage(),
				exception.getStackTrace().toString(), exception.getUser());

		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = ItemQuantityExceededException.class)
	public ResponseEntity<ErrorResponse> itemQtyExceed(ItemQuantityExceededException exception) {

		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, exception.getLocalizedMessage(),
				exception.getStackTrace().toString(), exception.getItem());

		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = StockItemNotFoundException.class)
	public ResponseEntity<ErrorResponse> itemNotFound(StockItemNotFoundException exception) {

		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND, exception.getLocalizedMessage(),
				exception.getStackTrace().toString(), exception.getUuid());

		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = OrderNotFoundException.class)
	public ResponseEntity<ErrorResponse> orderNotFound(OrderNotFoundException exception) {
		
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND, exception.getLocalizedMessage(),
				exception.getStackTrace().toString(), null);
		
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = NullPointerException.class)
	public ResponseEntity<ErrorResponse> nullPointer(NullPointerException exception) {

		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, "SIBS Null Pointer Error", exception.getMessage(),
				null);

		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = RuntimeException.class)
	public ResponseEntity<ErrorResponse> itemQtdExceeded(RuntimeException exception) {
		
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception.getLocalizedMessage(), "",
				null);
		
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value = OrderPersistException.class)
	public ResponseEntity<ErrorResponse> itemQtdExceeded(OrderPersistException exception) {
		
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception.getLocalizedMessage(), "",
				exception.getStackTrace());
		
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
