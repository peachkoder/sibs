package com.peachkoder.service.exceptions;

import java.util.UUID;

import com.peachkoder.model.entity.User;

import lombok.Getter;

@Getter
public class StockItemNotFoundException extends RuntimeException {
 

	private UUID uuid;

	public StockItemNotFoundException(UUID uuid) {
		super( "Stock Item Not Found. Id = " + uuid.toString());
		this.uuid = uuid;
	} 
}
