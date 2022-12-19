package com.peachkoder.service.exceptions;

import com.peachkoder.model.entity.StockMovement;

import lombok.Getter;

@Getter
public class ItemQuantityExceededException extends RuntimeException {

	private StockMovement item;
	
	private Integer quantity;

	public ItemQuantityExceededException(StockMovement stockItem, Integer quantity) {
		super(String.format("Item Quantity Insuficient Error. Required value = %d", quantity));		
		this.item = stockItem;
		this.quantity = quantity;
	} 
}
