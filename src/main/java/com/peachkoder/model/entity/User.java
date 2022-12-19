package com.peachkoder.model.entity;

import java.util.HashSet;
import java.util.Set;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor 
@Data
@Entity
@Table(name = "USERS")
public class User {
	 
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	@NonNull 
	private String name;
	
	@NonNull
	//@Column(unique = true)
	private String email;
	
//	@OneToMany(mappedBy="user")
//	private Set<Order> orders = new HashSet<>();
	
	public User(String name, String email) {
		this.name = name;
		this.email = email;
	}

}
