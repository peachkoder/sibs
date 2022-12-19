package com.peachkoder.controller;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.peachkoder.model.entity.Order;
import com.peachkoder.model.entity.StockMovement;
import com.peachkoder.service.OrderService;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
	
	@Autowired
	private OrderService orderService;

	@GetMapping()
	private ResponseEntity<?> getAll(@RequestParam(name = "field", required = false) String field,
			@RequestParam(name = "sort", required = false) Boolean sort) {
		 List<Order> list = orderService.findAll(field, sort);
		 
		 if (list == null || list.size() == 0) {
			 return ResponseEntity.noContent().build();
		 }
			 
		 return ResponseEntity.ok(list); 
	}
	
	@GetMapping("/{id}/stockmovements")
	private ResponseEntity<?> getStockMovementByOrder(@PathVariable String id ) {
		
		Set<StockMovement> list = orderService.getAllMovements(UUID.fromString(id)); 

		if (list == null || list.size() == 0) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok(list);
	}


	@PostMapping
	private ResponseEntity<?> createOrder(@RequestParam Integer quantity, @RequestParam Long userId,
			@RequestParam String itemId) { 		
		
		Order order = orderService.createOrder(quantity, userId, UUID.fromString(itemId));  
		
		return ResponseEntity.ok(order);
	}
	
	@PutMapping()
	private ResponseEntity<?> updateOrder(@RequestBody Order order) { 
		orderService.update(order);
		return ResponseEntity.ok(order);
	} 
	
	@DeleteMapping()
	private ResponseEntity<?> deleteOrder(@RequestBody Order order) { 
		orderService.delete(order);
		return ResponseEntity.ok().build();
	} 
}
