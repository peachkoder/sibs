package com.peachkoder.model.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.peachkoder.model.entity.Item;
import com.peachkoder.model.entity.StockMovement;

public interface StockMovementRepository extends JpaRepository<StockMovement, UUID> { 
	
	List<StockMovement> findByItem(Item item);
	
	List<StockMovement> findByItemAndBlocked(Item item, Boolean blocked);

}
