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

import com.peachkoder.model.entity.User;
import com.peachkoder.service.UserService;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

	@Autowired
	private UserService service;

	@GetMapping()
	private ResponseEntity<?> getAll(@RequestParam(name = "sort", required = false) Boolean sort,
			@RequestParam(name = "field", required = false) String field) {
		List<User> list = service.getAll(field, sort);

		if (list == null || list.size() == 0) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok(list);
	}

	@GetMapping("/{name}")
	private ResponseEntity<?> getbyName(@PathVariable String name) {

		User user = service.getByName(name);

		if (user == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok(user);

	}

	@PostMapping
	private ResponseEntity<?> create(@RequestParam String name, @RequestParam String email) {

		User user = service.createItem(name, email);

		return ResponseEntity.ok(user);
	}

	@PutMapping
	private ResponseEntity<?> update(@RequestBody User user) {
		User updated = service.update(user);
		return ResponseEntity.ok(updated);
	}

	@DeleteMapping
	private ResponseEntity<?> delete(@RequestBody User user) {

		return service.delete(user) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();

	}
}
