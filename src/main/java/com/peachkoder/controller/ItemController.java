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

import com.peachkoder.model.entity.Item;
import com.peachkoder.service.ItemService;

@RestController
@RequestMapping("/api/v1/items")
public class ItemController {

	@Autowired
	private ItemService itemService;

	@GetMapping()
	private ResponseEntity<?> getAll(@RequestParam(name = "sort", required = false) Boolean sort)  {

		List<Item> list = itemService.getAll(sort);

		if (list == null || list.size() == 0) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok(list);
	}

	@GetMapping("/{name}")
	private ResponseEntity<?> getbyName(@PathVariable String name) {

		return ResponseEntity.ok(itemService.getByName(name));

	}

	@PostMapping("/{name}")
	private ResponseEntity<?> create(@PathVariable String name) {

		Item item = itemService.createItem(name);

		return ResponseEntity.ok(item);
	}

	@PutMapping()
	private ResponseEntity<?> update(@RequestBody Item item) {
		Item updated = itemService.update(item);
		
		return ResponseEntity.ok(updated);
	}

	@DeleteMapping()
	private ResponseEntity<?> delete(@RequestBody Item item) {
		itemService.delete(item);
		return ResponseEntity.ok().build();
	}
}
