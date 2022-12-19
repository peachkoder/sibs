package com.peachkoder.model.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.peachkoder.model.entity.Item;

public interface ItemRepository extends JpaRepository<Item, UUID> {
	
	Optional<Item> findById(UUID uuid);

	Item getByName(String name);

}
