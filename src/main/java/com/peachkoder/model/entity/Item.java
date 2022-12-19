package com.peachkoder.model.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor

@Data
@Entity
@Table(name = "ITEMS")
public class Item {
	
	@Id
    @GeneratedValue(strategy = GenerationType.UUID) 
    private UUID id;
	
	@Column(unique = true)
	private String name;

	public Item(String name) { 
		this.name = name;
	}
	 
	

}




