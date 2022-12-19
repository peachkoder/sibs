package com.peachkoder.controller;

import java.util.List;

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

import com.peachkoder.model.entity.StockMovement;
import com.peachkoder.model.entity.dto.StockMovementDto;
import com.peachkoder.service.OrderService;
import com.peachkoder.service.StockMovementService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/stockmovement")
@RequiredArgsConstructor

public class StockMovementController {

	@Autowired
	private StockMovementService stockMovementService;

	@GetMapping()
	private ResponseEntity<?> getAll(@RequestParam(name = "field", required = false) String field,
			@RequestParam(name = "sort", required = false) Boolean sort) {
		List<StockMovement> list = stockMovementService.findAll(field, sort);

		if (list == null || list.size() == 0) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok(list);
	}

	@PostMapping()
	private ResponseEntity<?> createOrder(@RequestBody StockMovementDto movementDto) {

		StockMovement movement = stockMovementService.createMovement(movementDto.getItem(), movementDto.getQuantity());

		return ResponseEntity.ok(movement);
	}

	@PutMapping()
	private ResponseEntity<?> updateOrder(@RequestBody StockMovement movement) {

		return stockMovementService.update(movement) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
	}

	@DeleteMapping()
	private ResponseEntity<?> deleteOrder(@RequestBody StockMovement movement) {
		stockMovementService.delete(movement);
		return ResponseEntity.ok().build();
	}
}
