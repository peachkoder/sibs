package com.peachkoder.model.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.peachkoder.config.aop.LoggingRequired;
import com.peachkoder.model.enums.OrderStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name = "ORDERS")
public class Order implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@CreatedDate
	private Date creationDate = new Date();

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Item item;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private User user;

	@OneToMany()
	private Set<StockMovement> movements = new HashSet<>();

	private Integer quantity;

	@Enumerated(EnumType.STRING)
	private OrderStatus status = OrderStatus.OPEN;

	public Order(User user, Item item, Integer quantity) {
		this.user = user;
		this.item = item;
		this.quantity = quantity;
	}

	public Boolean addMovement(StockMovement movement) {
		return movements.add(movement);
	}

	@JsonIgnore
	public Integer getMovementsQuantity() {  
		return movements.stream().map(m-> m.getQuantity()).reduce(0, (l, r) -> l + r);		
	}
	
	@JsonIgnore 
	public Boolean isFull() {
		return quantity <= getMovementsQuantity();
	}
	
	// Diference between order.quantity and total of its movements
	@JsonIgnore 
	public Integer getRoom() {
		 Integer room =  quantity - getMovementsQuantity();
		 return room >= 0 ? room : 0;
	}
}
