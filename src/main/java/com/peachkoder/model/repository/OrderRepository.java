package com.peachkoder.model.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.peachkoder.model.entity.Item;
import com.peachkoder.model.entity.Order;
import com.peachkoder.model.enums.OrderStatus;

public interface OrderRepository extends JpaRepository<Order, UUID> {
	
	Optional<Order> findById(UUID uuid);

	List<Order> findByQuantityEqualsAndStatusEquals(Integer quantityInStock, OrderStatus status);

	List<Order> findByItem( Item item);
	
	List<Order> findByItemAndStatus(Item item, OrderStatus status); 

}
