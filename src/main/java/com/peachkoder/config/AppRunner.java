package com.peachkoder.config;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.peachkoder.model.entity.Item;
import com.peachkoder.model.entity.User;
import com.peachkoder.model.repository.ItemRepository;
import com.peachkoder.model.repository.OrderRepository;
import com.peachkoder.model.repository.UserRepository;
import com.peachkoder.service.EmailService;
import com.peachkoder.service.OrderService;

import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Configuration()
@EnableAspectJAutoProxy 
//@EnableAspectJAutoProxy(proxyTargetClass=true)  
@ComponentScan("com.peachkoder.*") 
public class AppRunner implements CommandLineRunner {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	private OrderRepository orderRepository;	
	
	@Autowired
	private OrderService orderService;	
	
	
	@Autowired
	private EmailService emailService;
	
	@PersistenceContext
	private Session session;
	
	@Override
	@Transactional
	public void run(String... args) throws Exception { 
		
		userRepository.save(new User("user1", "user1@email1.com"));
		userRepository.save(new User("user2", "user1@email2.com"));
		userRepository.save(new User("user3", "user1@email3.com"));
		
		User user4 = new User("user4", "user4@mail.com"); 
		session.persist(user4);
		
		itemRepository.save(new Item("item1"));
		itemRepository.save(new Item("item2"));
		itemRepository.save(new Item("item3"));
		
		Item item4 = new Item("item4"); 
		session.persist(item4);
		
	}
}
