package com.peachkoder.model.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderDto {
	
	private String itemId;
	
	private Long userId;
	
	private Integer quantity;

	@Override
	public String toString() {
		return "OrderDto [itemId=" + itemId + ", userId=" + userId + ", quantity=" + quantity + "]";
	}
	
	
}
