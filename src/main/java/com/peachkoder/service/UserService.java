package com.peachkoder.service;

import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.peachkoder.model.entity.User;
import com.peachkoder.model.repository.UserRepository;

import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;
	
	@PersistenceContext
	private Session session;

	public List<User> getAll(String field, Boolean asc) {
		if (asc == null)
			return repository.findAll();
		
		if (field == null || field.isBlank())
			field = "id";
		
		Direction dir = asc ? Direction.ASC : Direction.DESC;
		return repository.findAll(Sort.by(dir, field));

	}
 
	public User getByName(String name) {
		return repository.findByName(name);		 
	}
 
	@Transactional
	public User createItem(String name, String email) {	
		
		if (name == null || name.isBlank() || email == null || email.isBlank())
			throw new NullPointerException("Item Creation paramater(s) empty!");
		
		try {
			return repository.save(new User(name, email));
		} catch (Exception e) {
			throw new RuntimeException("SIBS - Error creating User:\n" + e.getLocalizedMessage());
		} 
	}


	@Transactional
	public User update(User user) {
		
		Optional<User> optUser = repository.findById(user.getId());
		if (optUser.isEmpty())  
			throw new RuntimeException("Sibs - User Update Error. No item found to be updated.");
		
		session.persist(user);		
		return user;
	}

	@Transactional
	public Boolean delete(User user) {
		
		Optional<User> optItem = repository.findById(user.getId());
		if (optItem.isPresent()) {
			session.remove(optItem.get());
			return true;
		}
		return false;
	}
}

















