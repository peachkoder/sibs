package com.peachkoder.service.exceptions;

import com.peachkoder.model.entity.User;

import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException {

	private User user;

	public UserNotFoundException(User user) {
		super( "User Not Found");
		this.user = user; 
	} 
}
