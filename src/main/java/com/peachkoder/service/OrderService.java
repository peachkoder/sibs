package com.peachkoder.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.peachkoder.controller.exceptions.OrderNotFoundException;
import com.peachkoder.model.entity.Item;
import com.peachkoder.model.entity.Order;
import com.peachkoder.model.entity.StockMovement;
import com.peachkoder.model.entity.User;
import com.peachkoder.model.repository.ItemRepository;
import com.peachkoder.model.repository.OrderRepository;
import com.peachkoder.model.repository.StockMovementRepository;
import com.peachkoder.model.repository.UserRepository;
import com.peachkoder.service.exceptions.StockItemNotFoundException;
import com.peachkoder.service.exceptions.UserNotFoundException;

import jakarta.transaction.Transactional;

@Service 
public class OrderService extends OrderPersistenceHandler{

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private StockMovementRepository stockMovementRepository;
 

	public Order createOrder(User user, Item item, Integer quantity) {

		if (user == null || item == null || quantity == 0)
			return null;

		return createOrder(quantity, user.getId(), item.getId()); 
	}

	/***
	 * When an order is created, it should try to satisfy it with the current stock.
	 * 
	 * @param quantity
	 * @param userId
	 * @param itemId
	 * @return Order
	 */
	@Transactional
	public Order createOrder(Integer quantity, Long userId, UUID itemId) { 
		 
		
		Optional<User> optUser = userRepository.findById(userId);
		if (optUser.isEmpty())
			throw new UserNotFoundException(null);

		Optional<Item> optItem = itemRepository.findById(itemId);
		if (optItem.isEmpty())
			throw new StockItemNotFoundException(itemId);

		Order order = new Order(optUser.get(), optItem.get(), quantity);
		 	
		fillOrderWithMatchMovement(order); 
		
		return persist(order); 	
	}
	 

	/***
	 * Try associates order with stock movements
	 * 
	 * First, it looks for StockMovement candidates. Candidates must be unblock and
	 * have same item as the order.
	 * 
	 * @param order
	 */
	private void fillOrderWithMatchMovement(Order order) {

		// candidates must have same order's item and be unblocked
		List<StockMovement> list = stockMovementRepository.findByItemAndBlocked(order.getItem(), false);

		if (list.size() == 0) 			
		
		list.stream().forEach(move -> {
 
			int oQ = order.getQuantity();
			int oMQ = order.getMovementsQuantity();
			// room is the diff between order.quantity and the sum of its assocciated
			// stockmovements
			int room = oQ - oMQ;
			if (room > 0) {
				int mQ = move.getQuantity();
				if (mQ <= room) {
					move.setBlocked(true);
					session.persist(move);
					order.getMovements().add(move); 
				} else {
					StockMovement sonMovement = new StockMovement(move.getItem(), room);
					sonMovement.setBlocked(true);
					session.persist(sonMovement);
					order.getMovements().add(sonMovement);
					
					move.setQuantity(mQ - room);
					session.persist(move);
				}
			}

		});

	}
	
	public Order findById(UUID itemId) {
 
		Optional<Order> findById = orderRepository.findById(itemId);
		if (findById.isEmpty())
			throw new OrderNotFoundException();

		return findById.get();
	}

	@Transactional
	public void delete(Order order) {
		if (order == null)
			throw new OrderNotFoundException();
		 
		orderRepository.delete(order);
	}

	@Transactional
	public Boolean update(Order order) { 
		
		Boolean result = false;
		
		Optional<Order> ord = orderRepository.findById(order.getId());
		if (ord.isPresent()) {
			result = orderRepository.save(ord.get()) != null;
		}
		 
		return result;

	}

	public List<Order> findAll(String field, Boolean asc) { 
		
		if (asc == null)
			return orderRepository.findAll();

		if (field == null || field.isBlank())
			field = "id";

		Direction direction = asc ? Direction.ASC : Direction.DESC;
		 

		return orderRepository.findAll(Sort.by(direction, field));
	}

	public Set<StockMovement> getAllMovements(UUID id) {

		Order order = findById(id);
		return order.getMovements();	
	}


}
