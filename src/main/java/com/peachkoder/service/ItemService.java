package com.peachkoder.service;

import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.peachkoder.model.entity.Item;
import com.peachkoder.model.repository.ItemRepository;

import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

//@Log4j2
@Service
public class ItemService {

	@Autowired
	private ItemRepository repository;
	
	@PersistenceContext
	Session session;
	
	public List<Item> getAll(Boolean asc) {
		
		if (asc == null)
			return repository.findAll();
		
		return asc ? repository.findAll(Sort.by(Direction.ASC, "name")) :
			repository.findAll(Sort.by(Direction.DESC, "name"));
	}
	
	
	@Transactional
	public Item createItem(String uniqueString) {
		
		try {
			return repository.save(new Item(uniqueString));
		} catch (Exception e) {
			throw new RuntimeException("Item Creation Error. Be attention Name field must be unique.");
			
		}
	}
	
	@Transactional
	public Boolean delete(Item item) {
		
		Optional<Item> optItem = repository.findById(item.getId());
		if (optItem.isPresent()) {
			session.remove(optItem.get());
			return true;
		}
		return false;
	}
	
	@Transactional
	public Item update(Item item) {
		
		Optional<Item> optItem = repository.findById(item.getId());
		if (optItem.isEmpty())  
			throw new RuntimeException("Item Update Error. No item found to be updated.");
		
		session.persist(item);		
		return item;
	}


		
	public Item getByName(String name) { 
		Item item = repository.getByName(name);
		if (item == null)
			throw new RuntimeException("Item Update Error. No item found.");
		return item;
		
	}
	
}
