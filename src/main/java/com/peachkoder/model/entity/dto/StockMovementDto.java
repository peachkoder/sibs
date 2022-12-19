package com.peachkoder.model.entity.dto;

import com.peachkoder.model.entity.Item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockMovementDto {
	
	private Item item;
	
	private Integer quantity;

}
