package com.peachkoder.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;

import com.peachkoder.model.entity.Order;
import com.peachkoder.model.enums.OrderStatus;

import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

public abstract class OrderPersistenceHandler {
	
	private static final Logger log = LogManager.getLogger("com.peachkoder.email" );
	
	@PersistenceContext
	protected Session session;
	
	@Autowired
	private EmailService emailService;
	
	@Transactional
	public Order persist(Order order){
		
		try {
			if (order.isFull()) {
				order.setStatus(OrderStatus.COMPLETE);
				session.persist(order);
				log.warn("order COMPLETE - {}",order);
				emailService.sendMail(order.getUser(), order);
				return order;
			}
			session.persist(order);
			log.warn("order   SAVED - {}",order);
			return order;
		} catch (Exception e) {
			throw new OrderPersistException(e.getLocalizedMessage());
		}
	} 

}
