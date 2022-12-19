package com.peachkoder.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.peachkoder.model.entity.Item;
import com.peachkoder.model.entity.Order;
import com.peachkoder.model.entity.StockMovement;
import com.peachkoder.model.enums.OrderStatus;
import com.peachkoder.model.repository.ItemRepository;
import com.peachkoder.model.repository.OrderRepository;
import com.peachkoder.model.repository.StockMovementRepository;
import com.peachkoder.service.exceptions.StockItemNotFoundException;

import jakarta.transaction.Transactional;
 
@Service
public class StockMovementService extends OrderPersistenceHandler{
	
	@Autowired
	private ItemRepository itemRepository;
 
	
	@Autowired
	private OrderRepository orderRepository;	
	
	@Autowired
	private StockMovementRepository movementRepository;
	
	@Autowired
	EmailService emailService;

	/***
	 * 	CREATE A STOCKMOVEMENT
	 * 
	 *   When a stock movement is created, the system should try to attribute it 
	 *   to an order that isn't complete;
	 * 
	 * @param item
	 * @param quantity
	 * @return  StockMovement
	 */
	@Transactional
	public StockMovement createMovement(Item item, Integer quantity) { 
		
		if (item == null || quantity == 0)
			throw new RuntimeException("Stock Movements Error: bad parameters."); 
		
		Optional<Item> optItem = itemRepository.findById(item.getId());
		if (optItem.isEmpty())
			throw new StockItemNotFoundException(item.getId());   

		StockMovement movement = new StockMovement(optItem.get(), quantity);
		session.persist(movement);  
		
		// ORDER match mechanism.
		// Looking for open orders with item = movement's item 
		List<Order> list = orderRepository.findByItemAndStatus(optItem.get(), OrderStatus.OPEN);
		list.stream().forEach(order -> {
			 
			int room = order.getRoom();
			
			if (room > 0) {  // no space - get out!
				if (room >= movement.getQuantity()) {
					setOrder(movement, order);
				} else {
					splitAndSetOrder(movement, order);
				}				
			} 
			 
		});
		
		return movement;	  
	}
	
	@Transactional
	private void setOrder(StockMovement movement, Order order) { 
		 
		movement.setBlocked(true);
		session.persist(movement);
		
		order.getMovements().add(movement);
		persist(order);
	}
	
	@Transactional
	private void splitAndSetOrder(StockMovement movement, Order order) { 
		
		int room = order.getRoom();
		int moveQ = movement.getQuantity();		
		
		movement.setQuantity(moveQ - room);
		session.persist(movement);
		
		StockMovement sonMovement = new StockMovement(movement.getItem(), room);
		sonMovement.setMother(movement);		
		
		setOrder(sonMovement, order); 
	}


	@Transactional
	public void delete(StockMovement movement) {
		if (movement == null) return; 
		session.remove(movement); 
	}
	
	@Transactional
	public Boolean update(StockMovement movement) {		 
		
		Boolean result = false;
		
		Optional<StockMovement> moveFound = movementRepository.findById(movement.getId());
		if (moveFound.isPresent()) {
			result = movementRepository.save(moveFound.get()) != null;
		}
		 
		return result;		
	}

	public List<StockMovement> findAll(String field, Boolean asc) {	 
		if (asc == null)
			return movementRepository.findAll();
		
		if (field == null || field.isBlank())
			field = "id";
		
		Direction direction = asc ? Sort.Direction.ASC : Sort.Direction.DESC;
		 
		return movementRepository.findAll(Sort.by(direction, field));   
	}

}
