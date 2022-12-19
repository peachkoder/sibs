package com.peachkoder.model.entity;

import java.util.Date;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor 
@Data
@Entity
@Table(name = "STOCK_MOVEMENTS")
public class StockMovement {
	
	@Id
    @GeneratedValue(strategy = GenerationType.UUID) 
    private UUID id;
	
	@CreatedDate
	private Date  creationDate = new Date();

	@OneToOne
	private Item item;

	private Integer quantity; 
	
	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private StockMovement mother;
	
	@JsonIgnore
	private Boolean blocked = false;

	public StockMovement(Item item, Integer quantity) {
		super();
		this.item = item;
		this.quantity = quantity;
	}
	
	
}
